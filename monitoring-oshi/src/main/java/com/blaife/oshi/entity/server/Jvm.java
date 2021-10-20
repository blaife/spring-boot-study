package com.blaife.oshi.entity.server;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import lombok.Data;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.util.Date;

/**
 * @author blaife
 * @description Jvm 实体类
 * @data 2021/4/24 22:01
 */
@Data
public class Jvm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前 JVM 占用的内存总数 (M)
     */
    private double total;

    /**
     * JVM 最大可用内存总数 (M)
     */
    private double max;

    /**
     * JVM 空闲内存 (M)
     */
    private double free;

    /**
     * JDK 版本
     */
    private String version;

    /**
     * JDK 路径
     */
    private String home;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024), 2);
    }

    public double getMax() {
        return NumberUtil.div(max, (1024 * 1024), 2);
    }

    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024), 2);
    }

    /**
     * JVM内存使用占比
     */
    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(total - free, total, 4), 100);
    }

    /**
     * 获取 JDK 名称
     */
    public String getName() {
        return ManagementFactory.getRuntimeMXBean().getVmName();
    }

    /**
     * 获取 JDK 启动时间
     */
    public String getStartTime() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);
        return DateUtil.formatDateTime(date);
    }

    /**
     * 获取运行时长
     */
    public String getRunTime() {
        // 启动时间
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);

        // 运行了多长时间 ms（毫秒）
        long runMS = DateUtil.between(date, new Date(), DateUnit.MS);

        // 一天的毫秒数
        long nd = 1000 * 24 * 60 * 60;
        // 一小时的毫秒数
        long nh = 1000 * 60 * 60;
        // 一分钟的毫秒数
        long nm = 1000 * 60;

        long day = runMS / nd;
        long hour = runMS % nd / nh;
        long min = runMS % nd % nh / nm;

        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 运行时常（MS）
     * @return
     */
    public long getRunTimeForMS() {
        // 启动时间
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);

        // 运行了多长时间 ms（毫秒）
        return DateUtil.between(date, new Date(), DateUnit.MS);
    }

    @Override
    public String toString() {
        return "Jvm ：{" +
                "\n\t当前 JVM 占用的内存总数 (M) ： " + total +
                ", \n\tJVM 最大可用内存总数 (M) ： " + max +
                ", \n\tJVM 空闲内存 (M) ： " + free +
                ", \n\tJDK 版本 ： " + version +
                ", \n\tJDK 路径 ： " + home +
                ", \n\tJVM 内存使用占比 ： " + getUsage() +
                ", \n\tJDK 名称 ： " + getName() +
                ", \n\tJDK 启动时间 ： " + getStartTime() +
                ", \n\tJDK 运行时长 ： " + getRunTime() +
                ", \n\tJDK 运行时长（MS） ： " + getRunTimeForMS() +
                "\n}";
    }
}
