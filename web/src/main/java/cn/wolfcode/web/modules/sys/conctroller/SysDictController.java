package cn.wolfcode.web.modules.sys.conctroller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.wolfcode.web.modules.sys.service.DictService;
import cn.wolfcode.web.modules.sys.service.SysDictService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.wolfcode.web.commons.enums.DeleteType;
import cn.wolfcode.web.modules.log.LogModules;
import cn.wolfcode.web.modules.sys.entity.SysDict;
import cn.wolfcode.web.commons.entity.CodeMsg;
import cn.wolfcode.web.commons.entity.ExcelExportEntityWrapper;
import cn.wolfcode.web.commons.utils.LayuiTools;
import cn.wolfcode.web.commons.utils.PoiExportHelper;
import cn.wolfcode.web.commons.utils.PoiImportHelper;
import cn.wolfcode.web.modules.sys.entity.SysDictVerifyEntity;
import link.ahsj.core.annotations.*;
import link.ahsj.core.entitys.ApiModel;
import link.ahsj.core.exception.ErrorCode;
import link.ahsj.core.utils.base.AppAssertUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 17:39
 * @email chenshiyun2011@163.com
 */
@Controller
@RequestMapping("/dataDict")
public class SysDictController {

    @Autowired
    private DictService dictService;
    @Autowired
    private SysDictService sysDictService;

    @GetMapping("/dictList.html")
    @PreAuthorize("hasAuthority('dataDict:dictList')")
    public ModelAndView toList(ModelAndView mv) {
        List<SysDict> dicts = sysDictService.list(Wrappers.<SysDict>lambdaQuery().orderByDesc(SysDict::getSort).select(SysDict::getDiceCode, SysDict::getDiceName));
        mv.setViewName("sys/dataDict/dictList");
        mv.addObject("dictList", dicts);
        return mv;
    }

    @GetMapping("/add.html")
    @PreAuthorize("hasAuthority('dataDict:save')")
    public ModelAndView toAdd(ModelAndView mv) {
        List<SysDict> dicts = sysDictService.list(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getDisable, DeleteType.NORMAL)
                .isNull(SysDict::getParentDiceCode)
                .groupBy(SysDict::getDiceCode, SysDict::getDiceName, SysDict::getSort)
                .orderByDesc(SysDict::getSort)
                .select(SysDict::getDiceCode, SysDict::getDiceName));
        mv.setViewName("sys/dataDict/dictAdd");
        mv.addObject("dictList", dicts);
        return mv;
    }

    @GetMapping("/{id}.html")
    @PreAuthorize("hasAuthority('dataDict:update')")
    public ModelAndView toUpdate(@PathVariable("id") String id, ModelAndView mv) {
        List<SysDict> dicts = sysDictService.list(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getDisable, DeleteType.NORMAL)
                .isNull(SysDict::getParentDiceCode)
                .groupBy(SysDict::getDiceCode, SysDict::getDiceName, SysDict::getSort)
                .orderByDesc(SysDict::getSort)
                .select(SysDict::getDiceCode, SysDict::getDiceName));
        SysDict sysDict = sysDictService.getById(id);

        mv.setViewName("sys/dataDict/dictUpdate");
        mv.addObject("dictList", dicts);
        mv.addObject("sysDict", sysDict);
        return mv;
    }



    @GetMapping("import.html")
    @PreAuthorize("hasAuthority('dataDict:import')")
    public ModelAndView toImport(ModelAndView mv) {

        mv.setViewName("sys/dataDict/importDict");
        return mv;
    }

    /*************************action method************************************/

    @SameUrlData
    @SysLog(value = LogModules.SAVE, module = LogModules.DICT)
    @PostMapping("save")
    @PreAuthorize("hasAuthority('dataDict:save')")
    public ResponseEntity<ApiModel> save(@Validated({AddGroup.class}) @RequestBody SysDict sysDict) {
        int count = sysDictService.count(Wrappers.<SysDict>lambdaQuery()
                .eq(SysDict::getDiceCode, sysDict.getDiceCode())
        );
        AppAssertUtil.isErr(count > 0, String.format("字典编码%s已重复", sysDict.getDiceCode()));

        sysDictService.save(sysDict);

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.DELETE, module = LogModules.DICT)
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAuthority('dataDict:delete')")
    public ResponseEntity<ApiModel> delete(@PathVariable("id") String id) {
        SysDict sysDict = sysDictService.getById(id);
        AppAssertUtil.isNull(sysDict, "字典不存在");

        sysDictService.update(  Wrappers.<SysDict>lambdaUpdate().eq(SysDict::getDiceCode, sysDict.getDiceCode()).set(SysDict::getDisable, DeleteType.DISABLE));
        sysDictService.update(  Wrappers.<SysDict>lambdaUpdate().eq(SysDict::getParentDiceCode, sysDict.getDiceCode()).set(SysDict::getDisable, DeleteType.DISABLE));

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.RECOVER, module = LogModules.DICT)
    @PutMapping("recovery/{id}")
    @PreAuthorize("hasAuthority('dataDict:recovery')")
    public ResponseEntity<ApiModel> recovery(@PathVariable("id") String id) {
        SysDict sysDict = sysDictService.getById(id);
        AppAssertUtil.isNull(sysDict, "字典不存在");

        sysDictService.update(  Wrappers.<SysDict>lambdaUpdate().eq(SysDict::getDiceCode, sysDict.getDiceCode()).set(SysDict::getDisable, DeleteType.NORMAL));
        sysDictService.update(  Wrappers.<SysDict>lambdaUpdate().eq(SysDict::getParentDiceCode, sysDict.getDiceCode()).set(SysDict::getDisable, DeleteType.NORMAL));

        return ResponseEntity.ok(ApiModel.ok());
    }


    @SameUrlData
    @SysLog(value = LogModules.UPDATE, module = LogModules.DICT)
    @PutMapping("update")
    @PreAuthorize("hasAuthority('dataDict:update')")
    public ResponseEntity<ApiModel> update(@Validated({UpdateGroup.class}) @RequestBody SysDict sysDict) {
        int count = sysDictService.count(Wrappers.<SysDict>lambdaQuery()
                .ne(SysDict::getId, sysDict.getId())
                .eq(SysDict::getDiceCode, sysDict.getDiceCode())
        );
        AppAssertUtil.isErr(count > 0, String.format("字典编码%s已重复", sysDict.getDiceCode()));

        sysDictService.updateById(sysDict);

        return ResponseEntity.ok(ApiModel.ok());
    }

    @SysLog(value = LogModules.LIST, module = LogModules.DICT)
    @GetMapping("dictList")
    @PreAuthorize("hasAuthority('dataDict:dictList')")
    public ResponseEntity page(@RequestParam(value = "dictName", required = false) String dictName, @RequestParam(value = "diceCodes", required = false) List<String> diceCodes) {
        List<SysDict> sysDicts = dictService.queryDictList(diceCodes, dictName);
        if (CollectionUtils.isEmpty(sysDicts)) {
            return ResponseEntity.ok(LayuiTools.toLayuiTreeTableModel(new ArrayList(0)));
        }
        return ResponseEntity.ok(LayuiTools.toLayuiTreeTableModel(sysDicts));
    }

    @SysLog(value = LogModules.IMPORT, module = LogModules.DICT)
    @PostMapping("import")
    @PreAuthorize("hasAuthority('dataDict:import')")
    public ResponseEntity importDict(MultipartFile file) throws Exception {
        ImportParams params = PoiImportHelper.buildImportParams(new String[]{SysDict.DICE_NAME, SysDict.PARENT_DICE_CODE, SysDict.DICE_CODE, SysDict.DICE_VALUE, SysDict.DESCRIPTION}, new Class[]{ImportGroup.class});
        ExcelImportResult result = ExcelImportUtil.importExcelMore(file.getInputStream(), SysDictVerifyEntity.class, params);
        List<String> errors = PoiImportHelper.getErrors(result);

        List ers = dictService.importDict(result.getList());
        errors.addAll(ers);

        return CollectionUtils.isEmpty(errors) ? ResponseEntity.ok(ApiModel.ok()) : ResponseEntity.ok(ApiModel.error(ErrorCode.UNVALID, CodeMsg.VALID_ERR_MSG, errors));
    }

    @SysLog(value = "数据字典模板", module = LogModules.DICT)
    @GetMapping("template")
    @PreAuthorize("hasAuthority('dataDict:import')")
    public void template(HttpServletResponse response) throws UnsupportedEncodingException {
        ExcelExportEntityWrapper wrapper = new ExcelExportEntityWrapper();
        wrapper.entity(SysDict.DICE_NAME, "diceName", 20)
                .entity(SysDict.PARENT_DICE_CODE, "parentDiceCode", 20)
                .entity(SysDict.DICE_CODE, "diceCode", 20)
                .entity(SysDict.DICE_VALUE, "diceValue", 20)
                .entity(SysDict.DESCRIPTION, "description", 20);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), wrapper.getResult(), new ArrayList<>());
        PoiExportHelper.exportExcel(response, "数据字典模板", workbook);
    }

    @SysLog(value = LogModules.EXPORT, module = LogModules.DICT)
    @PostMapping("export")
    @PreAuthorize("hasAuthority('dataDict:export')")
    public void export(@RequestParam(value = "dictName", required = false) String dictName, @RequestParam(value = "diceCodes", required = false) String[] diceCodes, HttpServletResponse response) throws IOException {
        List<SysDict> codes = dictService.queryDictCode(dictName, diceCodes);

        Set<String> dictCodes = codes.stream().map(SysDict::getDiceCode).collect(Collectors.toSet());
        Set<String> parentCodes = codes.stream().map(SysDict::getParentDiceCode).collect(Collectors.toSet());
        dictCodes.addAll(parentCodes);

        List<Map> dictList = new ArrayList<>(0);
        if (!CollectionUtils.isEmpty(dictCodes)) {
            dictList = dictService.queryList(dictCodes);
        }


        ExcelExportEntityWrapper wrapper = new ExcelExportEntityWrapper();
        wrapper.entity(SysDict.DICE_NAME, "diceName", 20)
                .entity(SysDict.DICE_CODE, "diceCode", 20)
                .entity(SysDict.DICE_VALUE, "字典值", 20)
                .entity("状态", "disable", 20)
                .entity(SysDict.DESCRIPTION, "description", 20);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), wrapper.getResult(), dictList);
        PoiExportHelper.exportExcel(response, "数据字典列表", workbook);
    }
}
