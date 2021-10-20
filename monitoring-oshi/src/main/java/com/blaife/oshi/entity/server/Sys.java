package com.blaife.oshi.entity.server;

import lombok.Data;

import java.io.Serializable;

/**
 * @author blaife
 * @description Sys 系统实体类
 * @date 2021/4/25 21:27
 */
@Data
public class Sys implements Serializable {

    /**
     * 服务器名称
     */
    private String computerName;

    /**
     * 服务器IP
     */
    private String computerIp;

    /**
     * 项目路径
     */
    private String userDir;

    /**
     * 操作系统
     */
    private String osName;

    /**
     * 系统架构
     */
    private String osArch;

    @Override
    public String toString() {
        return "Sys ：{" +
                "\n\t服务器名称 ：" + computerName +
                ",\n\t服务器IP ：" + computerIp +
                ",\n\t项目路径 ：" + userDir +
                ",\n\t操作系统 ：" + osName +
                ",\n\t系统架构 ：" + osArch +
                "\n}";
    }
}
