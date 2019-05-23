package com.github.igabaydulin.flappybird.gui;

import com.github.igabaydulin.flappybird.result.Result;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class MainFrame extends JFrame {

    private int WIDTH;
    private int HEIGHT;

    private MainPanel panel;
    private MenuPanel menuPanel;

    private boolean click = true;

    private Result bestResult;

    private int dif = 0;
    private boolean b_dif = false;
    private boolean left = true;
    private boolean touch = false;

    public MainFrame() {
        loadResult();
        this.setLayout(new BorderLayout());
        Toolkit t = Toolkit.getDefaultToolkit();
        WIDTH = 640;
        HEIGHT = t.getScreenSize().height;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        HEIGHT = HEIGHT < 640 ? 640 : HEIGHT;
        HEIGHT = HEIGHT > 768 ? 768 : HEIGHT;
        int x = (t.getScreenSize().width - WIDTH) / 2;
        int y = (t.getScreenSize().height - HEIGHT) / 2;
        this.setBounds(x, y, WIDTH, HEIGHT);
        this.setResizable(false);
        this.setTitle("Flappy Bird");

        JLayeredPane lpane = new JLayeredPane();
        lpane.setBounds(0, 0, WIDTH, HEIGHT);
        lpane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.add(lpane, BorderLayout.CENTER);

        panel = new MainPanel();
        panel.setSize(WIDTH, HEIGHT);
        panel.setBounds(0, 0, WIDTH, HEIGHT);
        lpane.add(panel, 0, 0);

        menuPanel = new MenuPanel();
        menuPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        menuPanel.setBounds(0, 0, WIDTH, HEIGHT);
        menuPanel.setOpaque(false);
        menuPanel.setVisible(false);
        lpane.add(menuPanel, 1, 0);

        panel.setBird(new Bird(this.HEIGHT / 9, 70, this.HEIGHT * 3 / 8));
        panel.setTubes(
                new Tubes(50, WIDTH, HEIGHT),
                new Tubes(450, WIDTH, HEIGHT)
        );
        this.setVisible(true);
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    private void loadResult() {
        try {
            new File(new File(
                    getClass().getClassLoader().getResource("root").getFile()).getParentFile(), "result").mkdir();


            File file = new File(new File(
                    getClass().getClassLoader().getResource("root").getFile()).getParentFile(), "result/bestResult.flb");
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream oin = new ObjectInputStream(fis);
                bestResult = (Result) oin.readObject();
            } else {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(new Result(0));
                oos.flush();
                oos.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void checkResult() {
        if (bestResult == null || bestResult.getValue() < panel.getResult().getValue()) {
            bestResult = new Result(panel.getResult().getValue());
            menuPanel.setBestResult(panel.getResult());
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(getClass().getClassLoader().getResource("result/bestResult.flb").getFile());

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(panel.getResult());
                oos.flush();
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void drawGame(Bird bird) {
        if (this.panel.isGameStarted()) {
            while (bird.getY() + 30 < HEIGHT - bird.getSize() - 100) {
                try {
                    Thread.sleep(20);

                    bird.setYByTime(0.2);
                    bird.setSpeedByTime(0.2);
                    bird.incAngle();

                    if (bird.getSpeed() >= 0) {
                        bird.setG(9.8);
                    }

                    panel.getTubes()[0].decX();
                    if (panel.getTubes()[0].getX() < -150) {
                        panel.getTubes()[0].setX(WIDTH + 50);
                        panel.getTubes()[0].rand();
                    }
                    panel.getTubes()[1].decX();
                    if (panel.getTubes()[1].getX() < -150) {
                        panel.getTubes()[1].setX(WIDTH + 50);
                        panel.getTubes()[1].rand();
                    }
                    panel.paintBird(bird);
                    if (bird.getX() + 80 > panel.getTubes()[0].getX() + 10 &&
                            bird.getX() < panel.getTubes()[0].getX() + 100 + 4) {
                        if (bird.getY() - 3 < panel.getTubes()[0].getY() - 1 ||
                                bird.getY() + 60 > panel.getTubes()[0].getY() + panel.getTubes()[0].getHole()) {
                            gameOver(bird);
                        }
                    }

                    if (bird.getX() + 80 > panel.getTubes()[1].getX() + 10 &&
                            bird.getX() < panel.getTubes()[1].getX() + 100 + 4) {
                        if (bird.getY() < panel.getTubes()[1].getY() - 1 ||
                                bird.getY() + 60 > panel.getTubes()[1].getY() + panel.getTubes()[0].getHole()) {
                            menuPanel.setBestResult(bestResult);
                            gameOver(bird);
                            break;
                        }
                    }
                    if (bird.getX() + 75 > panel.getTubes()[0].getX() + 50 &&
                            bird.getX() < panel.getTubes()[0].getX() + 150 + 4) {
                        if (left) {
                            panel.getResult().inc();
                            left = false;
                            playCoinSound();
                        }
                    } else if (bird.getX() + 75 > panel.getTubes()[1].getX() + 50 &&
                            bird.getX() < panel.getTubes()[1].getX() + 150 + 4) {
                        if (!left) {
                            panel.getResult().inc();
                            left = true;
                            playCoinSound();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (!touch) {
                playGameOverSound();
            }
            checkResult();
            panel.setMenuVisible(true);
            panel.setGameOver(true);
            menuPanel.setScore(panel.getResult());
            menuPanel.setBestResult(bestResult);
            menuPanel.setVisible(true);
            panel.getResult().reset();
            left = true;
            touch = false;
            panel.setGameStarted(false);
        } else {
            try {
                Thread.sleep(33);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!panel.isMenuVisible()) {
                if (dif > -20 && dif < 20 && !b_dif) {
                    dif += 2;
                    bird.setY(bird.getY() + 2);
                } else if (dif >= 20) {
                    dif -= 2;
                    b_dif = true;
                } else if (dif > -20) {
                    dif -= 2;
                    bird.setY(bird.getY() - 2);
                } else {
                    dif += 2;
                    b_dif = false;
                }
            }
        }
    }

    private void gameOver(Bird bird) {
        checkResult();
        panel.setGameOver(true);
        menuPanel.setScore(panel.getResult());
        touch = true;
        playGameOverSound();
        bird.setIncAngle();
        while (bird.getY() + 30 < HEIGHT - bird.getSize() - 100) {
            try {
                Thread.sleep(20);

                bird.setYByTime(0.5);
                bird.setSpeedByTime(0.5);
                bird.incAngle();

                bird.setG(20);

                panel.paintBird(bird);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        bird.setY(HEIGHT - bird.getSize() - 130);
        panel.setMenuVisible(true);
        menuPanel.setVisible(true);
    }

    private void playGameOverSound() {
        Clip c;
        try {
            c = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("sound/collision.wav"));

            c.open(ais);
            c.start();
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void playCoinSound() {
        Clip c;
        try {
            c = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem
                    .getAudioInputStream(getClass().getClassLoader().getResource("sound/coin.wav"));

            c.open(ais);
            c.start();
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        final MainFrame mFrame = new MainFrame();

        mFrame.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (!mFrame.panel.isGameOver()) {
                    Clip c;
                    try {
                        c = AudioSystem.getClip();
                        AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("sound/jump.wav"));

                        c.open(ais);
                        c.start();
                    } catch (LineUnavailableException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedAudioFileException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    mFrame.panel.getBird().setSpeed(-70);
                    mFrame.panel.getBird().setG(20);
                    if (!mFrame.panel.isGameStarted() && mFrame.click) {

                        mFrame.panel.getResult().reset();
                        mFrame.panel.getBird().setY(mFrame.HEIGHT * 3 / 8);
                        mFrame.panel.setGameStarted(true);
                    }
                    for (int i = 0; i < 10; ++i) {
                        try {
                            Thread.sleep(0, 1);
                            mFrame.panel.getBird().decAngle();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        mFrame.menuPanel.p32.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mFrame.menuPanel.setVisible(false);
                mFrame.panel.setGameOver(false);

                mFrame.panel.getTubes()[0].setX(50 + mFrame.WIDTH);
                mFrame.panel.getTubes()[1].setX(450 + mFrame.WIDTH);
                mFrame.panel.getTubes()[0].rand();
                mFrame.panel.getTubes()[1].rand();

                mFrame.panel.getBird().setY(mFrame.HEIGHT * 3 / 8);
                mFrame.panel.getBird().removeAngle();

                mFrame.panel.setMenuVisible(false);
                mFrame.click = true;
            }
        });

        while (true) {
            mFrame.drawGame(mFrame.panel.getBird());
        }
    }
}
