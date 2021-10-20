package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * @author blaife
 * @description 处理器信息
 * @date 2021/4/26 22:06
 */
public class ProcessorInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 获取处理器信息
        CentralProcessor processor = hal.getProcessor();

        System.out.println("物理处理器数量: " + processor.getPhysicalProcessorCount());
        System.out.println("物理包数: " + processor.getPhysicalPackageCount());
        System.out.println("逻辑处理器数: " + processor.getLogicalProcessorCount());
        System.out.println("进程标识: " + processor.getProcessorID());
        System.out.println("标识符: " + processor.getIdentifier());
    }

}
