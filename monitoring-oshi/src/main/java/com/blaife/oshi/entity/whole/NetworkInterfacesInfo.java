package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.util.FormatUtil;

import java.util.Arrays;

/**
 * @author blaife
 * @description 网络接口信息
 * @date 2021/4/30 8:43
 */
public class NetworkInterfacesInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 获取网络接口信息
        NetworkIF[] networkIFs = hal.getNetworkIFs();

        for (NetworkIF net : networkIFs) {
            System.out.format("名称: %s (%s)%n", net.getName(), net.getDisplayName());
            System.out.format("    MAC 地址: %s %n", net.getMacaddr());
            System.out.format("    最大传输单元: %s, 速率: %s %n", net.getMTU(), FormatUtil.formatValue(net.getSpeed(), "bps"));
            System.out.format("    IPv4: %s %n", Arrays.toString(net.getIPv4addr()));
            System.out.format("    IPv6: %s %n", Arrays.toString(net.getIPv6addr()));
            boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
                    || net.getPacketsSent() > 0;
            System.out.format("    交换: 接收 %s/%s%s; 传输 %s/%s%s %n",
                    hasData ? net.getPacketsRecv() + " packets" : "?",
                    hasData ? FormatUtil.formatBytes(net.getBytesRecv()) : "?",
                    hasData ? " (" + net.getInErrors() + " err)" : "",
                    hasData ? net.getPacketsSent() + " packets" : "?",
                    hasData ? FormatUtil.formatBytes(net.getBytesSent()) : "?",
                    hasData ? " (" + net.getOutErrors() + " err)" : "");
        }
    }

}
