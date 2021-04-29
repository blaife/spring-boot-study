package com.blaife.oshi.entity.whole;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Sensors;

import java.util.Arrays;

/**
 * @author blaife
 * @description 传感器信息
 * @data 2021/4/29 21:32
 */
public class SensorsInfo {

    public static void main(String[] args) {
        // 获取系统对象（系统对象中包含很多内容）
        SystemInfo si = new SystemInfo();
        // 获取硬件抽象层对象
        HardwareAbstractionLayer hal = si.getHardware();
        // 获取传感器信息
        Sensors sensors = hal.getSensors();
        System.out.println(sensors);
        System.out.println("CPU 温度: " + sensors.getCpuTemperature());
        System.out.println("CPU 电压: " + sensors.getCpuVoltage() + "V");
        System.out.println("风扇转速: " + Arrays.toString(sensors.getFanSpeeds()));
    }
}
