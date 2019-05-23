package com.github.igabaydulin.flappybird.gui;

import com.github.igabaydulin.flappybird.result.Result;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@SuppressWarnings("ConstantConditions")
public class MainPanel extends JPanel {

    private Graphics2D g2d;
    private int width, height;
    private Bird bird;
    private Tubes tubes1, tubes2;
    private Result result = new Result(0);
    private static Image background, road, bottom, readiness, readinessPointer, readinessBird, howToPlay;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private boolean menuVisible = false;

    static {
        try {
            background = ImageIO.read(MainPanel.class.getClassLoader().getResource("images/common/background.png"));
            road = ImageIO.read(MainPanel.class.getClassLoader().getResource("images/common/road.png"));
            bottom = ImageIO.read(MainPanel.class.getClassLoader().getResource("images/common/bottom.png"));
            readiness = ImageIO.read(MainPanel.class.getClassLoader().getResource("images/common/readiness.png"));
            readinessPointer = ImageIO.read(MainPanel.class.getClassLoader().getResource("images/common/readiness-pointer.png"));
            readinessBird = ImageIO.read(MainPanel.class.getClassLoader().getResource("images/common/readiness-bird.png"));
            howToPlay = ImageIO.read(MainPanel.class.getClassLoader().getResource("images/common/readiness-how-to-play.png"));
        } catch (IOException ignored) {
        }
    }

    MainPanel() {
        GridLayout gridLayout = new GridLayout(1, 1);
        this.setLayout(gridLayout);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    Bird getBird() {
        return bird;
    }

    void setBird(Bird bird) {
        this.bird = bird;
    }

    Tubes[] getTubes() {
        return new Tubes[]{tubes1, tubes2};
    }

    void setTubes(Tubes t1, Tubes t2) {
        tubes1 = t1;
        tubes2 = t2;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, this);
        paintTubes();
        if (!gameStarted && !gameOver) {
            paintStart();
        }
        g2d.drawImage(bottom, 0, height - 139, this);
        paintBird(bird);
        if (!gameOver) {
            paintLine();
        }
        if (!menuVisible) {
            paintText();
        }
        this.repaint();
    }

    void paintBird(Bird bird) {
        if (gameOver) {
            g2d.drawImage(bird.getLastImage(), bird.getX(), bird.getY(), this);
        } else {
            g2d.drawImage(bird.getImage(), bird.getX(), bird.getY(), this);
        }
    }

    private int moveLine = 60;

    private void paintLine() {
        try {
            Thread.sleep(0, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int x_l = moveLine;
        int y_l = height - 129;
        int line_width = 60;
        int line_height = 38;
        g2d.drawImage(road, x_l, y_l, this);
        for (int i = -2; i < 11; ++i) {
            g2d.copyArea(x_l, y_l, line_width, line_height, line_width * i, 0);
        }
        moveLine--;
        if (moveLine == 0) {
            moveLine = 60;
        }
        this.repaint();
    }

    private void paintTubes() {
        g2d.drawImage(Tubes.getTube1(), tubes1.getX(), tubes1.getY() - 331, this);
        g2d.drawImage(Tubes.getTube2(), tubes1.getX(), tubes1.getY() + tubes1.getHole(), this);

        g2d.drawImage(Tubes.getTube1(), tubes2.getX(), tubes2.getY() - 331, this);
        g2d.drawImage(Tubes.getTube2(), tubes2.getX(), tubes2.getY() + tubes2.getHole(), this);
    }

    private void paintText() {
        if (result != null) {
            int start = width / 2 - result.getLength() * 45 + result.getLength() * 10;
            for (int i = 0; i < result.getSymbols().length; ++i) {
                g2d.drawImage(Result.biSymbols[result.getSymbols()[i]], start + i * 60, 30, this);
            }
        }
    }

    private void paintStart() {
        g2d.drawImage(readiness, width / 2 - readiness.getWidth(this) / 2, height / 4 - 50, this);
        g2d.drawImage(readinessBird, width / 2 - readinessBird.getWidth(this) / 2 - 10, height / 2 - 50, this);
        g2d.drawImage(readinessPointer, width / 2 - readinessPointer.getWidth(this) / 2 - 10, height / 2 + 50, this);
        g2d.drawImage(howToPlay, width / 2 - howToPlay.getWidth(this) / 2 - 5, height / 2 + 100, this);
    }

    Result getResult() {
        return result;
    }

    boolean isGameOver() {
        return gameOver;
    }

    void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    boolean isMenuVisible() {
        return menuVisible;
    }

    void setMenuVisible(boolean menuVisible) {
        this.menuVisible = menuVisible;
    }

    boolean isGameStarted() {
        return gameStarted;
    }

    void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }
}
