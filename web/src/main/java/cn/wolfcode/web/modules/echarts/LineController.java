package cn.wolfcode.web.modules.echarts;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/3 15:00
 * @email chenshiyun2011@163.com
 */
@Controller
@RequestMapping("/line")
public class LineController {


    @GetMapping("/list.html")
    public String list() {
        return "echarts/line";
    }
}
