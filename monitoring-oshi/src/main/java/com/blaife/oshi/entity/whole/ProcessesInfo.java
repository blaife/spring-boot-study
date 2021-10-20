package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

/**
 * @author blaife
 * @description 进程信息
 * @date 2021/4/28 20:43
 */
public class ProcessesInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 操作系统
        OperatingSystem os = si.getOperatingSystem();
        // 存储器
        GlobalMemory memory = hal.getMemory();
        System.out.println("进程数量: " + os.getProcessCount() + ", 线程数量: " + os.getThreadCount());
        // 按最高CPU排序
        OSProcess[] procs = os.getProcesses(os.getProcessCount(), OperatingSystem.ProcessSort.CPU);
        System.out.println("-------------------------------------------");
        System.out.println("   PID  %CPU %MEM       VSZ       RSS Name");
        for (OSProcess p : procs) {
            System.out.format(" %5d %5.1f %4.1f %9s %9s %s%n", p.getProcessID(),
                    100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
                    100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
                    FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
        }
    }
}
