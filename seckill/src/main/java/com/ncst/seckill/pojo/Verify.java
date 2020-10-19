package com.ncst.seckill.pojo;

import java.awt.image.BufferedImage;

/**
 * @author LSY
 * 用于返回 数学验证码对象
 */
public class Verify {
    private BufferedImage bufferedImage;
    private int calc;

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public void setCalc(int calc) {
        this.calc = calc;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public int getCalc() {
        return calc;
    }
}