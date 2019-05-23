package com.github.igabaydulin.flappybird.gui;

import com.github.igabaydulin.flappybird.result.Result;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("ConstantConditions")
public class MenuPanel extends JPanel {
    private static Image gameOver, result, button, bronze, silver, gold;

    private Result score;
    private Result bestResult;

    private static BufferedImage[] biMiniSymbols = new BufferedImage[10];

    JPanel p32;

    static {
        try {
            biMiniSymbols[0] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point0.png"));
            biMiniSymbols[1] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point1.png"));
            biMiniSymbols[2] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point2.png"));
            biMiniSymbols[3] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point3.png"));
            biMiniSymbols[4] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point4.png"));
            biMiniSymbols[5] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point5.png"));
            biMiniSymbols[6] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point6.png"));
            biMiniSymbols[7] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point7.png"));
            biMiniSymbols[8] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point8.png"));
            biMiniSymbols[9] = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/points/mini-point9.png"));
        } catch (IOException ignored) {
        }
    }

    static {
        try {
            gameOver = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/common/game-over.png"));
            result = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/common/result.png"));
            button = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/common/play-button.png"));

            bronze = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/common/medal-bronze.png"));
            silver = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/common/medal-silver.png"));
            gold = ImageIO.read(MenuPanel.class.getClassLoader().getResource("images/common/medal-gold.png"));
        } catch (IOException ignored) {
        }
    }

    MenuPanel() {
        this.score = new Result(0);
        GridBagLayout gLayout = new GridBagLayout();
        this.setLayout(gLayout);

        JPanel p1 = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(gameOver,
                        (this.getWidth() - gameOver.getWidth(this)) / 2,
                        (this.getHeight() - gameOver.getHeight(this)) / 2, this);
                this.repaint();
            }
        };
        JPanel p2 = new JPanel();
        p2.setLayout(new GridBagLayout());
        JPanel p21 = new JPanel();
        p21.setOpaque(false);
        JPanel p22 = new JPanel() {
            private Graphics2D g2d;

            @Override
            public void paintComponent(Graphics g) {
                g2d = (Graphics2D) g;
                g.drawImage(result,
                        0, (this.getHeight() - result.getHeight(this)) / 2, this);
                paintScore();
                paintBestResult();
                paintMedal();
                this.repaint();
            }

            void paintScore() {
                if (score != null) {
                    int start = this.getWidth() - 68;
                    for (int i = score.getSymbols().length - 1; i >= 0; --i) {
                        g2d.drawImage(biMiniSymbols[score.getSymbols()[i]],
                                start = start - biMiniSymbols[score.getSymbols()[i]].getWidth(this) - 5,
                                this.getHeight() / 2 - 60, this);
                    }
                }
            }

            void paintBestResult() {
                if (bestResult != null) {
                    int start = this.getWidth() - 68;
                    for (int i = bestResult.getSymbols().length - 1; i >= 0; --i) {
                        g2d.drawImage(biMiniSymbols[bestResult.getSymbols()[i]],
                                start = start - biMiniSymbols[bestResult.getSymbols()[i]].getWidth(this) - 5,
                                this.getHeight() / 2 + 45, this);
                    }
                }
            }

            void paintMedal() {
                if (score.getValue() >= 100) {
                    g2d.drawImage(gold,
                            65,
                            150, this);
                } else if (score.getValue() >= 50) {
                    g2d.drawImage(silver,
                            65,
                            150, this);
                } else if (score.getValue() >= 15) {
                    g2d.drawImage(bronze,
                            65,
                            150, this);
                }
            }
        };
        JPanel p23 = new JPanel();
        p23.setOpaque(false);

        GridBagConstraints c21 = new GridBagConstraints();
        c21.weighty = 1;
        c21.weightx = 0.03;
        c21.fill = GridBagConstraints.BOTH;
        GridBagConstraints c22 = new GridBagConstraints();
        c22.weighty = 1;
        c22.weightx = 0.94;
        c22.fill = GridBagConstraints.BOTH;
        GridBagConstraints c23 = new GridBagConstraints();
        c23.weighty = 1;
        c23.weightx = 0.03;
        c23.fill = GridBagConstraints.BOTH;
        p2.add(p21, c21);
        p2.add(p22, c22);
        p2.add(p23, c23);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridBagLayout());
        JPanel p31 = new JPanel();
        p31.setOpaque(false);
        p32 = new JPanel() {

            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(button,
                        0, 0, this);
                this.repaint();
            }
        };
        JPanel p33 = new JPanel();
        p33.setOpaque(false);

        GridBagConstraints c31 = new GridBagConstraints();
        c31.weighty = 1;
        c31.weightx = 0.28;
        c31.fill = GridBagConstraints.BOTH;
        GridBagConstraints c32 = new GridBagConstraints();
        c32.weighty = 1;
        c32.weightx = 0.41;
        c32.fill = GridBagConstraints.BOTH;
        GridBagConstraints c33 = new GridBagConstraints();
        c33.weighty = 1;
        c33.weightx = 0.31;
        c33.fill = GridBagConstraints.BOTH;
        p3.add(p31, c31);
        p3.add(p32, c32);
        p3.add(p33, c33);

        p1.setOpaque(false);
        p2.setOpaque(false);
        p3.setOpaque(false);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridy = 0;
        c1.weighty = 0.25;
        c1.weightx = 1;
        c1.fill = GridBagConstraints.BOTH;

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridy = 1;
        c2.weighty = 0.5;
        c2.weightx = 1;
        c2.fill = GridBagConstraints.BOTH;

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridy = 2;
        c3.weighty = 0.25;
        c3.weightx = 1;
        c3.fill = GridBagConstraints.BOTH;

        this.add(p1, c1);
        this.add(p2, c2);
        this.add(p3, c3);
    }

    @Override
    public void paintComponent(Graphics g) {
        this.repaint();
    }

    void setBestResult(Result bestResult) {
        this.bestResult = new Result(bestResult.getValue());
    }

    void setScore(Result score) {
        this.score = new Result(score.getValue());
    }
}
