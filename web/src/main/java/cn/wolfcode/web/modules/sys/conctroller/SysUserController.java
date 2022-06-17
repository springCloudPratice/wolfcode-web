package cn.wolfcode.web.modules.sys.conctroller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONUtil;
import cn.wolfcode.web.modules.sys.entity.SysUserVerifyEntity;
import cn.wolfcode.web.modules.sys.service.DeptService;
import cn.wolfcode.web.modules.sys.service.UserRoleMenuService;
import cn.wolfcode.web.modules.sys.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.wolfcode.web.modules.sys.form.LoginForm;
import cn.wolfcode.web.modules.sys.form.PasswordForm;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysRoleInfo;
import cn.wolfcode.web.modules.sys.entity.SysUser;
import cn.wolfcode.web.commons.entity.CodeMsg;
import cn.wolfcode.web.commons.entity.ExcelExportEntityWrapper;
import cn.wolfcode.web.commons.entity.LayuiPage;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.PoiExportHelper;
import cn.wolfcode.web.commons.utils.PoiImportHelper;
import cn.wolfcode.web.commons.utils.SystemCheckUtils;
import cn.wolfcode.web.modules.sys.form.SysUserForm;
import cn.wolfcode.web.modules.sys.vo.SysUserVo;
import link.ahsj.core.annotations.*;
import link.ahsj.core.components.layui.treeselect.MultipleTreeSelect;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.exception.ErrorCode;
import link.ahsj.core.utils.base.AppAssertUtil;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/2 16:55
 * @email chenshiyun2011@163.com
 */
@Controller
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleMenuService roleInfoService;
    @Autowired
    private DeptService deptService;

    @GetMapping("/add.html")
    @PreAuthorize("hasAuthority('user:add')")
    public ModelAndView toAdd(ModelAndView mv) {
        List<SysRoleInfo> roles = roleInfoService.queryEnableRole();

        mv.setViewName("sys/user/userAdd");
        mv.addObject("roles", roles);
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('user:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        List<SysRoleInfo> roles = roleInfoService.queryEnableRole();
        List<MultipleTreeSelect> treeSelects = deptService.queryAllDeptTree();
        SysUser sysUser = userService.queryUserInfo(id);

        mv.setViewName("sys/user/userUpdate");
        mv.addObject("roles", roles);
        mv.addObject("sysUser", sysUser);
        mv.addObject("depts", CollectionUtil.isNotEmpty(treeSelects) ? JSONUtil.toJsonStr(treeSelects) : Collections.EMPTY_LIST);
        return mv;
    }

    @GetMapping("password.html")
    public ModelAndView toPassword(ModelAndView mv, HttpSession session) {
        RSA rsa = new RSA();
        session.setAttribute(LoginForm.KEY_PAIR_STR, rsa.getPrivateKeyBase64());

        mv.setViewName("sys/user/password");
        mv.addObject("publicKey", rsa.getPublicKeyBase64());
        return mv;
    }


    @GetMapping("/list.html")
    @PreAuthorize("hasAuthority('user:userList')")
    public ModelAndView toList(ModelAndView mv) {
        List<SysRoleInfo> roles = roleInfoService.queryEnableRole();

        mv.setViewName("sys/user/userList");
        mv.addObject("roles", roles);
        return mv;
    }

    @GetMapping("import.html")
    @PreAuthorize("hasAuthority('user:import')")
    public ModelAndView toImport(ModelAndView mv) {

        mv.setViewName("sys/user/importUser");
        return mv;
    }

    @GetMapping("info.html")
    public ModelAndView toInfo(ModelAndView mv, HttpServletRequest request) {
        SysUser loginUser = (SysUser) request.getSession().getAttribute(LoginForm.LOGIN_USER_KEY);
        AppAssertUtil.isNull(loginUser, CodeMsg.SYS_ERR_MSG);

        mv.setViewName("sys/user/info");
        mv.addObject("loginUser", userService.queryUserInfo(loginUser.getUserId()));
        return mv;
    }

    /*************************action method************************************/

    @SameUrlData
    @SysLog(value = LogModules.SAVE, module = LogModules.USER)
    @PostMapping("save")
    @PreAuthorize("hasAuthority('user:add')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody SysUser sysUser, HttpSession session) {
        SysUser currentUser = (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);

        userService.saveUserInfo(currentUser, sysUser);

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModules.USER)
    @DeleteMapping("delete")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<ApiModel> delete(HttpSession session, @RequestBody List<String> userIds) {
        SysUser currentUser = (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);

        userService.deleteUser(currentUser, userIds);

        return ResponseEntity.ok(ApiModel.ok());
    }


    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModules.USER)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody SysUser sysUser) {
        userService.updateUserInfo(sysUser);

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.RECOVER, module = LogModules.USER)
    @PutMapping("reset/{userId}")
    @PreAuthorize("hasAuthority('user:reset')")
    public ResponseEntity<ApiModel> reset(HttpSession session, @PathVariable String userId) {
        SysUser currentUser = (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);

        userService.resetUser(currentUser, userId);

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = "重置密码", module = LogModules.USER)
    @PutMapping("resetPassword/{userId}")
    @PreAuthorize("hasAuthority('user:resetPassword')")
    public ResponseEntity<ApiModel> resetPassword(HttpSession session, @PathVariable String userId) {
        SysUser currentUser = (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);

        userService.resetPassword(currentUser, userId);

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = "修改密码", module = LogModules.USER)
    @PutMapping("updatePassword")
    public ResponseEntity<ApiModel> updatePassword(HttpSession session, @Validated @RequestBody PasswordForm passwordForm) throws Exception {
        userService.updatePassword(passwordForm, session);

        session.invalidate();

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.LIST, module = LogModules.USER)
    @PostMapping("userList")
    @PreAuthorize("hasAuthority('user:userList')")
    public ResponseEntity page(LayuiPage layuiPage, SysUserForm sysUserForm) {
        SystemCheckUtils.getInstance().checkMaxPage(layuiPage);
        IPage page = new Page<>(layuiPage.getPage(), layuiPage.getLimit());
        List<SysUserVo> sysUsers = userService.queryList(sysUserForm, page);
        page.setRecords(sysUsers);
        return ResponseEntity.ok(LayuiTools.toLayuiTableModel(page));
    }

    @SysLog(value = LogModules.IMPORT, module = LogModules.USER)
    @PostMapping("import")
    @PreAuthorize("hasAuthority('user:import')")
    public ResponseEntity importUser(MultipartFile file, HttpSession session) throws Exception {
        SysUser currentUser = (SysUser) session.getAttribute(LoginForm.LOGIN_USER_KEY);
        ImportParams params = PoiImportHelper.buildImportParams(new String[]{SysUser.USERNAME_COL, SysUser.NICKNAME_COL, SysUser.ROLE_COL, SysUser.ORGANIZE_COL, SysUser.EMAIL_COL, SysUser.PHONE_COL}, new Class[]{ImportGroup.class});
        ExcelImportResult result = ExcelImportUtil.importExcelMore(file.getInputStream(), SysUserVerifyEntity.class, params);
        List<String> errors = PoiImportHelper.getErrors(result);

        List<String> ers = userService.importUser(currentUser, result.getList());
        errors.addAll(ers);

        return CollectionUtils.isEmpty(errors) ? ResponseEntity.ok(ApiModel.ok()) : ResponseEntity.ok(ApiModel.error(ErrorCode.UNVALID, CodeMsg.VALID_ERR_MSG, errors));
    }

    @SysLog(value = "用户模板", module = "用户管理")
    @GetMapping("template")
    @PreAuthorize("hasAuthority('user:import')")
    public void template(HttpServletResponse response) throws UnsupportedEncodingException {
        ExcelExportEntityWrapper wrapper = new ExcelExportEntityWrapper();
        wrapper.entity(SysUser.USERNAME_COL, "username", 20)
                .entity(SysUser.NICKNAME_COL, "nickName", 20)
                .entity(SysUser.ROLE_COL, "roleId", 20)
                .entity(SysUser.ORGANIZE_COL, "deptId", 20)
                .entity(SysUser.EMAIL_COL, "email", 20)
                .entity(SysUser.PHONE_COL, "phone", 20);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), wrapper.getResult(), new ArrayList<>());
        Sheet sheet = workbook.getSheetAt(0);
        DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();

        // 创建角色下拉列表数据(注意:如果字符长度>255,会出现超出字符长度异常)
        String[] roleNames = roleInfoService.queryEnableRole().stream().map(SysRoleInfo::getRoleName).toArray(String[]::new);
        // 创建机构列表数据
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(roleNames);
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 999, 2, 2);
        DataValidation validation = dataValidationHelper.createValidation(constraint, cellRangeAddressList);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);
        PoiExportHelper.exportExcel(response, "用户模板", workbook);
    }

    @SysLog(value = LogModules.EXPORT, module = LogModules.USER)
    @PostMapping("export")
    @PreAuthorize("hasAuthority('user:export')")
    public void export(SysUserForm sysUserForm, HttpServletResponse response) throws IOException {

        List<SysUserVo> sysUsers = userService.queryList(sysUserForm, null);

        Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams(), SysUserVo.class, sysUsers);
        PoiExportHelper.exportExcel(response, "用户列表", workbook);
    }
}
