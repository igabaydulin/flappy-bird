package com.github.igabaydulin.flappybird.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings({"ConstantConditions", "SameParameterValue"})
class Bird {

    private final int size;

    private int x;
    private int y;

    private double speed;

    private double G = 9.8;
    private BufferedImage[] animations = new BufferedImage[4];
    private BufferedImage currentImage, rotImage;
    private int animationStep = 0;

    private double angle = 0;
    private int tick = 0;

    {
        try {
            animations[0] = ImageIO.read(getClass().getClassLoader().getResource("images/birds/bird-animation1.png"));
            animations[1] = ImageIO.read(getClass().getClassLoader().getResource("images/birds/bird-animation2.png"));
            animations[2] = ImageIO.read(getClass().getClassLoader().getResource("images/birds/bird-animation3.png"));
            animations[3] = ImageIO.read(getClass().getClassLoader().getResource("images/birds/bird-animation2.png"));
            currentImage = animations[0];
        } catch (IOException ignored) {
        }
    }

    private void AffineTransformBird() {
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, 40, 40);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        rotImage = op.filter(currentImage, null);
    }

    void incAngle() {
        if (angle < 0.3) {
            angle += 0.02;
        } else if (angle < 1) {
            angle += 0.05;
        }
    }

    void decAngle() {
        if (angle > 0.6) {
            angle = 0;
        }
        if (angle > -0.75) {
            angle -= 0.08;
        }
    }

    void setIncAngle() {
        if (this.angle < 0.1) {
            this.angle = 0.1;
        }
    }

    void removeAngle() {
        angle = 0;
    }

    private void changeFBird() {
        animationStep = (++animationStep) % 4;
        currentImage = animations[animationStep];
    }

    Bird(int size, int x, int startY) {
        if (size < 1 || x < 1 || startY < 1) {
            throw new IllegalArgumentException
                    ("Каждый из параметров должен быть больше 0");
        }

        this.size = size;

        this.x = x;
        this.y = startY;
        this.speed = 0;
    }

    Image getImage() {
        if (tick % 40 == 0) {
            changeFBird();
        }
        tick = (++tick) % 40;
        AffineTransformBird();

        return rotImage;
    }

    Image getLastImage() {
        angle = Math.PI / 2;
        AffineTransformBird();
        return rotImage;
    }

    void setG(double g) {
        this.G = g;
    }

    double getSpeed() {
        return this.speed;
    }

    void setSpeed(double speed) {
        this.speed = speed;
    }

    void setSpeedByTime(double time) {
        this.speed = this.speed + this.G * time;
    }

    int getY() {
        return this.y;
    }

    void setY(int newY) {
        this.y = newY;
    }

    void setYByTime(double time) {
        this.y = this.y + (int) (this.speed * time + this.G * Math.pow(time, 2) / 2);
    }

    int getX() {
        return this.x;
    }

    int getSize() {
        return this.size;
    }
}
