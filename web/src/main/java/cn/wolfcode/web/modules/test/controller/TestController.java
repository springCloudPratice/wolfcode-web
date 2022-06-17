package cn.wolfcode.web.modules.test.controller;


import link.ahsj.core.components.layui.treeselect.Hierarchy;
import link.ahsj.core.entitys.ApiModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户登录日志表 前端控制器
 * </p>
 *
 * @author Aimer
 * @since 2019-04-08
 */
@Log4j2
@RequestMapping("/test")
@Controller
public class TestController {

    @GetMapping("/list.html")
    public ModelAndView toList(ModelAndView mv) {
        mv.setViewName("test/demo/demo");
        return mv;
    }

    /**
     * 树形菜单
     * @param mv
     * @return
     */
    @GetMapping("/tree.html")
    public ModelAndView tree(ModelAndView mv) {
        mv.setViewName("test/demo/tree");
        return mv;
    }

    /**
     * 树形菜单
     * @return
     */
    @GetMapping("/listTree")
    public ResponseEntity listTree() {
        return ResponseEntity.ok(getData());
    }

    /**
     * 树形菜单
     * @param mv
     * @return
     */
    @GetMapping("/treeCheck.html")
    public ModelAndView treeCheck(ModelAndView mv) {
        mv.addObject("checkId", "31");
        mv.setViewName("test/demo/treeCheck");
        return mv;
    }

    /**
     * 模板后端接收请求
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<ApiModel> treeCheck(Map map) {
        log.info(map.toString());
        return ResponseEntity.ok(ApiModel.data(map));
    }

    public static List<Hierarchy> getData() {
        List<Hierarchy> hierarchy = new ArrayList<>();
        //一级
        hierarchy.add(new Hierarchy("1", "0000", "中国", ""));

        //二级
        hierarchy.add(new Hierarchy("2", "1", "广西", ""));
        hierarchy.add(new Hierarchy("3", "1", "广东", ""));
        hierarchy.add(new Hierarchy("4", "1", "湖南", ""));
        hierarchy.add(new Hierarchy("5", "1", "湖北", ""));
        hierarchy.add(new Hierarchy("6", "1", "福建", ""));

        //三级
        hierarchy.add(new Hierarchy("21", "2", "桂林", ""));
        hierarchy.add(new Hierarchy("22", "2", "南宁", ""));

        hierarchy.add(new Hierarchy("31", "3", "广州", ""));
        hierarchy.add(new Hierarchy("32", "3", "佛山", ""));

        hierarchy.add(new Hierarchy("41", "4", "岳阳", ""));
        hierarchy.add(new Hierarchy("42", "4", "衡阳", ""));

        hierarchy.add(new Hierarchy("51", "5", "武汉", ""));
        hierarchy.add(new Hierarchy("52", "5", "荆州", ""));

        hierarchy.add(new Hierarchy("61", "6", "福州", ""));
        hierarchy.add(new Hierarchy("62", "6", "厦门", ""));
        return hierarchy;
    }
}
