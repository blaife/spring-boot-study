package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;

/**
 * @author blaife
 * @description 电源信息
 * @data 2021/4/29 21:44
 */
public class PowerSourceInfo {
    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 获取传感器信息
        PowerSource[] powerSources = hal.getPowerSources();

        if (powerSources.length == 0) {
            System.out.println("未找到电池");
        } else {
            for (PowerSource pSource : powerSources) {
                System.out.println("电源名称: " + pSource.getName());
                if (pSource.getTimeRemaining() < -1d) {
                    System.out.println("\t电源已连接");
                } else {
                    System.out.println("\t剩余时间: "
                            + (int) pSource.getTimeRemaining() / 3600 + "小时"
                            + (int) ((pSource.getTimeRemaining() / 60) % 60)+ "分钟");
                }
                System.out.format("\t电源容量: %.1f%%", pSource.getRemainingCapacity() * 100d);
            }
        }
    }
}
