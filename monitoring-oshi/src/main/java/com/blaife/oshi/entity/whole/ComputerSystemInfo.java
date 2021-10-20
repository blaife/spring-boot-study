package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Firmware;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * @author blaife
 * @description 计算机系统信息
 * @date 2021/4/26 21:50
 */
public class ComputerSystemInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 获取计算机系统对象
        ComputerSystem computerSystem  = hal.getComputerSystem();

        System.out.println("厂商: " + computerSystem.getManufacturer());
        System.out.println("模型: " + computerSystem.getModel());
        System.out.println("编号: " + computerSystem.getSerialNumber());

        // 获取固件内容
        final Firmware firmware = computerSystem.getFirmware();
        System.out.println("固件:");
        System.out.println("  制造商: " + firmware.getManufacturer());
        System.out.println("  名称: " + firmware.getName());
        System.out.println("  描述: " + firmware.getDescription());
        System.out.println("  版本: " + firmware.getVersion());
        System.out.println("  发行时间: " + (firmware.getReleaseDate() == null ?
                "unknown" : firmware.getReleaseDate()));

        // 获取集线板内容
        final Baseboard baseboard = computerSystem.getBaseboard();
        System.out.println("集线板:");
        System.out.println("  制造商: " + baseboard.getManufacturer());
        System.out.println("  模型: " + baseboard.getModel());
        System.out.println("  版本: " + baseboard.getVersion());
        System.out.println("  编号: " + baseboard.getSerialNumber());
    }


}
