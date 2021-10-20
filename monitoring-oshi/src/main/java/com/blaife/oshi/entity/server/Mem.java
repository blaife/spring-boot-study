package com.blaife.oshi.entity.server;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * @author blaife
 * @description Men 内存实体类
 * @date 2021/4/25 21:18
 */
@Data
public class Mem implements Serializable {

    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    public double getTotal() {
        return NumberUtil.div(total, (1024 * 1024 * 1024), 2);
    }

    public double getUsed() {
        return NumberUtil.div(used, (1024 * 1024 * 1024), 2);
    }

    public double getFree() {
        return NumberUtil.div(free, (1024 * 1024 * 1024), 2);
    }

    /**
     * 使用占比
     * @return
     */
    public double getUsage() {
        return NumberUtil.mul(NumberUtil.div(used, total, 4), 100);
    }

    @Override
    public String toString() {
        return "Mem ：{" +
                "\n\t内存总量 ： " + total +
                ",\n\t已用内存=" + used +
                ",\n\t剩余内存=" + free +
                ",\n\t使用占比=" + getUsage() +
                "\n}";
    }
}
