package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.Display;
import oshi.hardware.HardwareAbstractionLayer;


/**
 * @author blaife
 * @description 显示器信息
 * @date 2021/5/1 17:12
 */
public class DisplaysInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 显示器信息
        Display[] displays = hal.getDisplays();

        for (Display display : displays) {
            System.out.println("------------------------------------------------------");
            System.out.println(display.toString());
        }
    }

}
