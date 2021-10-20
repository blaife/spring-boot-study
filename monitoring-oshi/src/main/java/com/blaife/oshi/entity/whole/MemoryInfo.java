package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * @author blaife
 * @description 内存信息
 * @data 2021/4/27 21:51
 */
public class MemoryInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 获取内存对象
        GlobalMemory memory  = hal.getMemory();

        System.out.println("总内存: " + memory.getTotal());
        System.out.println("可用内存: " + memory.getAvailable());
        System.out.println("每页大小: " + memory.getPageSize());
        System.out.println("Swap Total: " + memory.getSwapTotal());
        System.out.println("Swap Used: " + memory.getSwapUsed());
        System.out.println("Swap Pages In: " + memory.getSwapPagesIn());
        System.out.println("Swap Pages Out: " + memory.getSwapPagesOut());
    }

}
