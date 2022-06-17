package cn.wolfcode.web.modules.sys.entity;

import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Eastern unbeaten
 * @version Version 1.0
 * @date 2019/4/11 16:50
 * @email chenshiyun2011@163.com
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SysDictVerifyEntity extends SysDict implements IExcelModel, IExcelDataModel {

    private String errorMsg;
    private int rowNum;

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public int getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(int rowNum) {
        this.rowNum = rowNum + 1;
    }
}
