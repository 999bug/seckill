package com.ncst.seckill.pojo;

import java.awt.image.BufferedImage;

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