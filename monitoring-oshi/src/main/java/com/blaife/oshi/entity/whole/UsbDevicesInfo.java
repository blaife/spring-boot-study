package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.UsbDevice;

/**
 * @author blaife
 * @description USB设备信息
 * @date 2021/5/1 17:22
 */
public class UsbDevicesInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 显示器信息
        UsbDevice[] usbDevices = hal.getUsbDevices(true);

        for (UsbDevice usbDevice : usbDevices) {
            System.out.println("---------------------------------------------------------------");
            System.out.println(usbDevice.toString());
        }
    }

}
