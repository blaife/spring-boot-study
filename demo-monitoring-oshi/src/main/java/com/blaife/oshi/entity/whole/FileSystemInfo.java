package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

/**
 * @author blaife
 * @description 文件系统信息
 * @data 2021/4/29 22:30
 */
public class FileSystemInfo {
    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 操作系统
        OperatingSystem os = si.getOperatingSystem();
        // 文件系统
        FileSystem fileSystem = os.getFileSystem();

        System.out.format("文件叙述: %d/%d%n", fileSystem.getOpenFileDescriptors(),
                fileSystem.getMaxFileDescriptors());

        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            System.out.format(
                    " %s (%s) [%s] %s of %s 剩余容量 (%.1f%%) is %s "
                            + (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s")
                            + " 位于 %s%n",
                    fs.getName(), fs.getDescription().isEmpty() ? "文件系统" : fs.getDescription(), fs.getType(),
                    FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
                    fs.getVolume(), fs.getLogicalVolume(), fs.getMount());

        }
    }
}
