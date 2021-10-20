package com.blaife.oshi.entity.server;

import lombok.Data;

/**
 * @author blaife
 * @description SysFile 系统文件实体类
 * @date 2021/4/25 21:35
 */
@Data
public class SysFile {

    /**
     * 盘符路径
     */
    private String dirName;

    /**
     * 盘符类型
     */
    private String sysTypeName;

    /**
     * 文件类型
     */
    private String typeName;

    /**
     * 总大小
     */
    private String total;

    /**
     * 剩余大小
     */
    private String free;

    /**
     * 已使用大小
     */
    private String used;

    /**
     * 资源使用率
     */
    private double usage;

    @Override
    public String toString() {
        return "\n\tSysFile ： {" +
                "\n\t\t盘符路径 ： " + dirName +
                ",\n\t\t盘符名称 ：" + sysTypeName +
                ",\n\t\t文件类型 ：" + typeName +
                ",\n\t\t总大小 ：" + total +
                ",\n\t\t剩余大小 ：" + free +
                ",\n\t\t已使用大小 ：" + used +
                ",\n\t\t资源使用率 ：" + usage +
                "\n\t}\n";
    }
}
