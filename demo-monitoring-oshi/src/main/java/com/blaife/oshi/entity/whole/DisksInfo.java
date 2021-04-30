package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

/**
 * @author blaife
 * @description 磁盘信息
 * @data 2021/4/29 22:24
 */
public class DisksInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 获取磁盘信息
        HWDiskStore[] diskStores = hal.getDiskStores();

        for (HWDiskStore disk : diskStores) {
            boolean readwrite = disk.getReads() > 0 || disk.getWrites() > 0;
            System.out.format(" %s: (model: %s - S/N: %s) \n\tsize: %s, \n\treads: %s (%s), \n\twrites: %s (%s), \n\txfer: %s ms%n",
                    disk.getName(), disk.getModel(), disk.getSerial(),
                    disk.getSize() > 0 ? FormatUtil.formatBytesDecimal(disk.getSize()) : "?",
                    readwrite ? disk.getReads() : "?", readwrite ? FormatUtil.formatBytes(disk.getReadBytes()) : "?",
                    readwrite ? disk.getWrites() : "?", readwrite ? FormatUtil.formatBytes(disk.getWriteBytes()) : "?",
                    readwrite ? disk.getTransferTime() : "?");
            HWPartition[] partitions = disk.getPartitions();
            if (partitions == null) {
                // TODO 当所有操作系统实现时删除
                continue;
            }
            for (HWPartition part : partitions) {
                System.out.format(" |-- %s: %s (%s) Maj:Min=%d:%d, size: %s%s%n", part.getIdentification(),
                        part.getName(), part.getType(), part.getMajor(), part.getMinor(),
                        FormatUtil.formatBytesDecimal(part.getSize()),
                        part.getMountPoint().isEmpty() ? "" : " @ " + part.getMountPoint());
            }
        }

    }

}
