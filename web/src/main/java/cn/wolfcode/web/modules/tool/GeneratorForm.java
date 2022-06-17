package cn.wolfcode.web.modules.tool;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author Eastern unbeaten
 * @Email chenshiyun2011@163.com
 * @Date 2020/2/20 4:17 下午
 */
@Setter
@Getter
public class GeneratorForm {

    private String codeType;
    private String packageName;
    private String moduleName;
    private String dbType;
    private String outputDir;
    private String baseMenuId;
    private String menuName;
    private String tableId;
    private String baseContext;
    private String context;
    private String author;
    private String ip;
    private String prot;
    private String database;
    private String username;
    private String password;
    private String tableNames;
    private String tablePrefixs;

}
