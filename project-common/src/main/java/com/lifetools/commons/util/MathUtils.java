package com.lifetools.commons.util;

import java.math.BigDecimal;

/**
 * 数字计算工具类
 * Created by Zheng.Ke
 * Date 2016/8/25.
 */
public class MathUtils {

    /**
     * 提供精确的小数位四舍五入处理。
     * @param numberStr
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(String numberStr, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(numberStr);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }
}
