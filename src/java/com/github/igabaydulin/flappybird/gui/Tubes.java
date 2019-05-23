package com.github.igabaydulin.flappybird.gui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@SuppressWarnings("ConstantConditions")
class Tubes {

    private int x, y, height;
    private static BufferedImage tube1, tube2;

    static {
        try {
            tube1 = ImageIO.read(Tubes.class.getClassLoader().getResource("images/tube/upper-tube.png"));
            tube2 = ImageIO.read(Tubes.class.getClassLoader().getResource("images/tube/bottom-tube.png"));
        } catch (IOException ignored) {
        }
    }

    Tubes(int st, int width, int height) {
        Random r = new Random();
        this.height = height;
        y = 100 + r.nextInt(height - 540);
        x = width + st;
    }

    void rand() {
        Random r = new Random();
        y = 100 + r.nextInt(height - 540);
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    void decX() {
        x -= 5;
    }

    int getY() {
        return y;
    }

    int getHole() {
        return 0xc8;
    }

    static BufferedImage getTube1() {
        return tube1;
    }

    static BufferedImage getTube2() {
        return tube2;
    }
}
