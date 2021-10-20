package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.software.os.NetworkParams;
import oshi.software.os.OperatingSystem;

import java.util.Arrays;

/**
 * @author blaife
 * @description 网络参数信息
 * @date 2021/5/1 17:00
 */
public class NetworkParametersInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 操作系统
        OperatingSystem os = si.getOperatingSystem();
        // 网络参数
        NetworkParams networkParams = os.getNetworkParams();

        System.out.println("主机名: " + networkParams.getHostName());
        System.out.println("域名服务器: " + Arrays.toString(networkParams.getDnsServers()));
        System.out.println("网域名称: " + networkParams.getDomainName());
        System.out.println("Ipv4默认网关: " + networkParams.getIpv4DefaultGateway());
        System.out.println("Ipv6默认网关: " + networkParams.getIpv6DefaultGateway());
    }

}
