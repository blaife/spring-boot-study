package com.blaife.oshi.entity.server;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Blaife
 * @description Cpu 实体类
 * @date 2021/4/24 21:33
 */

@Data
public class Cpu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU 总使用率
     */
    private double total;

    /**
     * CPU 系统使用率
     */
    private double sys;

    /**
     * CPU 用户使用率
     */
    private double used;

    /**
     * CPU 当前等待率
     */
    private double wait;

    /**
     * CPU 当前空闲率
     */
    private double free;

    public double getTotal() {
        return NumberUtil.round(NumberUtil.mul(total, 100), 2).doubleValue();
    }

    public double getSys() {
        return NumberUtil.round(NumberUtil.mul(sys / total, 100), 2).doubleValue();
    }

    public double getUsed() {
        return NumberUtil.round(NumberUtil.mul(used / total, 100), 2).doubleValue();
    }

    public double getWait() {
        return NumberUtil.round(NumberUtil.mul(wait / total, 100), 2).doubleValue();
    }

    public double getFree() {
        return NumberUtil.round(NumberUtil.mul(free / total,100), 2).doubleValue();
    }

    @Override
    public String toString() {
        return "Cpu ：{" +
                "\n\t核心数 ： " + cpuNum +
                ",\n\t总使用率 ： " + total +
                ",\n\t系统使用率 ： " + sys +
                ",\n\t用户使用率 ： " + used +
                ",\n\t当前等待率 ： " + wait +
                ",\n\t当前空闲率 ： " + free +
                "\n}";
    }
}
