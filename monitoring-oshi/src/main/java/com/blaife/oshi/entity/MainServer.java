package com.blaife.oshi.entity;

import cn.hutool.core.util.NumberUtil;
import com.blaife.oshi.entity.server.*;
import lombok.Data;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;


import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author blaife
 * @description 主程序类
 * @data 2021/4/24 22:25
 */
@Data
public class MainServer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 休眠时间
     */
    private static final int OSHI_WAIT_SECOND = 1000;

    private Cpu cpu = new Cpu();

    private Mem mem = new Mem();

    private Sys sys = new Sys();

    private Jvm jvm = new Jvm();

    private List<SysFile> sysFiles = new LinkedList<>();


    /**
     * 入口方法
     */
    public void copyTo() {
        // 系统信息
        SystemInfo si = new SystemInfo();
        // 硬件抽象层
        HardwareAbstractionLayer hal = si.getHardware();

        // 载入CPU信息
        setCpuInfo(hal.getProcessor());

        // 载入内存信息
        setMemInfo(hal.getMemory());

        // 载入系统信息
        setSysInfo();

        // 载入JVM信息
        setJvmInfo();

        // 载入系统文件信息
        setSysFiles(si.getOperatingSystem());
    }

    /**
     * 载入CPU信息
     * @param processor 中央处理器
     */
    private void setCpuInfo(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSys(cSys);
        cpu.setUsed(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }

    /**
     * 载入Mem内存信息
     * @param memory 全局内存
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    /**
     * 载入Sys服务器信息
     */
    private void setSysInfo() {
        // 系统配置文件
        Properties props = System.getProperties();
        // 服务器名称
        String computerName = "";
        // 服务器IP
        String computerIp = "";
        try {
            computerName = InetAddress.getLocalHost().getHostName();
            computerIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        sys.setComputerName(computerName);
        sys.setComputerIp(computerIp);
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
    }

    /**
     * 载入Java虚拟机信息
     */
    private void setJvmInfo() {
        // 系统配置文件
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    /**
     * 载入SysFiles磁盘信息
     * @param os 操作系统
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(NumberUtil.round(NumberUtil.mul(used, total, 4), 100).doubleValue());
            sysFiles.add(sysFile);
        }
    }


    /**
     * 字节转换
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB" , (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB" , f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB" , f);
        } else {
            return String.format("%d B" , size);
        }
    }

}
