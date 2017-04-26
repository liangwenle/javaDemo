package com;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by wenle on 15-9-6.
 */

// 我的面板
class MyPanel extends JPanel implements KeyListener, Runnable {

    // 创建一个我的坦克组
    Hero hero = null;
    EnemyTank et = null;
    // 定义敌人坦克组
    Vector<EnemyTank> ets = new Vector<EnemyTank>();
    Vector<Node> nodes = new Vector<Node>();

    // 定义炸弹组
    Vector<Bomb> bombs = new Vector<Bomb>();

    int enSize = Recorder.getEnNum();

    // 定义三张图片
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    // 构造函数
    public MyPanel(String flag) {
        Recorder.getAllEnNum();
        hero = new Hero(200, 250);
        nodes = new Recorder().getNodesAndEnNums();
        if (flag.equals("newGame")) {
            // 初始化敌人坦克
            for (int i = 0; i < enSize; i++) {
                // 创建敌人坦克对象
                et = new EnemyTank((i + 1) * 38, 15);
                et.setDirect(3);
                et.setType(0);
                // 将MyPanel的坦克向量给该坦克
                et.setEts(ets);

                // 启动敌人坦克的线程
                Thread t = new Thread(et);
                t.start();
                // 加入
                ets.add(et);

                // 给敌人添加一个子弹
                Shot s = new Shot(et.x, et.y + 15, et.direct);
                // 把子弹加入
                et.ss.add(s);
                Thread t2 = new Thread(s);
                t2.start();

            }
        } else {
            System.out.println("初始化敌人坦克");
            // 初始化敌人坦克
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                // 创建敌人坦克对象
                et = new EnemyTank(node.x, node.y);
                et.setDirect(node.direct);
                et.setType(0);
                // 将MyPanel的坦克向量给该坦克
                et.setEts(ets);

                // 启动敌人坦克的线程
                Thread t = new Thread(et);
                t.start();
                // 加入
                ets.add(et);

                // 给敌人添加一个子弹
                Shot s = new Shot(et.x, et.y + 15, et.direct);
                // 把子弹加入
                et.ss.add(s);
                Thread t2 = new Thread(s);
                t2.start();

            }
        }

        try {
            image1 = ImageIO.read(new File("./tank/static/Image/bomb1.gif"));
            image2 = ImageIO.read(new File("./tank/static/Image/bomb2.gif"));
            image3 = ImageIO.read(new File("./tank/static/Image/bomb3.gif"));

        } catch (IOException e) {

            e.printStackTrace();
        } finally {

        }

        // 播放音频文件
        AePlayWave apw = new AePlayWave("./tank/static/mp3/111.wav");
        apw.start();

        // // 初始化三张图片,三张图片组成一个炸弹
        // image1 = Toolkit.getDefaultToolkit().getImage(
        // Panel.class.getResource("/bomb1.gif"));
        // image2 = Toolkit.getDefaultToolkit().getImage(
        // Panel.class.getResource("/bomb2.gif"));
        // image3 = Toolkit.getDefaultToolkit().getImage(
        // Panel.class.getResource("/bomb3.gif"));

    }

    // 重写panit
    public void paint(Graphics g) {

        super.paint(g);

        g.fillRect(0, 0, 400, 300);

        // 画出提示坦克
        showInfo(g);

        // 画出自己的坦克
        if (hero.isLive) {
            drawtank(hero.getX(), hero.getY(), hero.getDirect(), 1, g);
        }

        // 画出子弹
        // 从ss中取出每颗子弹，并画出
        for (int i = 0; i < hero.ss.size(); i++) {
            Shot myshot = hero.ss.get(i);
            if (myshot != null && myshot.isLive == true) {
                g.setColor(Color.red);
                g.draw3DRect(myshot.x, myshot.y, 1, 1, false);
            }

            if (myshot.isLive == false) {
                // 从ss中删除子弹
                hero.ss.remove(myshot);

            }
        }

        // 画出炸弹
        for (int i = 0; i < bombs.size(); i++) {
            // 取出炸弹
            Bomb b = bombs.get(i);

            if (b.life > 6) {
                g.drawImage(image1, b.x, b.y, 35, 35, this);
            } else if (b.life > 3) {
                g.drawImage(image2, b.x, b.y, 35, 35, this);
            } else if (b.life > 0) {
                g.drawImage(image3, b.x, b.y, 35, 35, this);
            }
            // 让b的生命减少
            b.lifeDown();
            // 如果炸弹生命为0
            if (b.life == 0) {
                bombs.remove(b);
            }
        }

        // 画出敌人的坦克
        for (int i = 0; i < ets.size(); i++) {
            // 取出坦克
            EnemyTank et = ets.get(i);
            // 判断坦克是否有效
            if (et.isLive == true) {
                // 画出敌人的坦克
                drawtank(et.x, et.y, et.direct, et.type, g);

                // 画出敌人子弹
                // System.out.println("坦克子弹有" + et.ss.size());
                for (int j = 0; j < et.ss.size(); j++) {
                    // 取出子弹
                    Shot enemyshot = et.ss.get(j);
                    if (enemyshot.isLive) {
                        // System.out.println("坦克" + i + "的" + j + "颗子弹在" + "x="
                        // + enemyshot.x + "y=" + enemyshot.y);
                        g.draw3DRect(enemyshot.x, enemyshot.y, 1, 1, false);
                    } else {
                        // 如果敌人坦克死亡
                        et.ss.remove(enemyshot);
                    }
                }

            }
            // if (et.isLive == false) {
            // 从ets中删除死的坦克
            // ets.remove(et);
            // }
        }

    }

    // 画出提示坦克，该坦克不参与战斗
    public void showInfo(Graphics g) {

        this.drawtank(50, 320, 1, 0, g);
        g.setColor(Color.black);
        g.drawString(":", 67, 325);
        g.drawString(Recorder.getEnNum() + "", 75, 325);
        g.drawString("辆", 90, 325);

        this.drawtank(130, 320, 1, 1, g);
        g.setColor(Color.black);
        g.drawString(":", 146, 325);
        g.drawString(Recorder.getMyLivfe() + "", 154, 325);
        g.drawString("辆", 169, 325);

        // 画出玩家的总成绩
        g.setColor(Color.black);
        Font f = new Font("宋体", Font.BOLD, 20);
        g.setFont(f);
        g.drawString("你的总成绩是	", 410, 20);

        // 画出敌人坦克
        this.drawtank(430, 50, 1, 0, g);
        g.setColor(Color.black);
        g.drawString(Recorder.getAllEnNum() + "", 460, 55);
    }

    // 判断子弹是否击中敌人坦克的函数
    public void hitEnemyTank() {
        for (int i = 0; i < hero.ss.size(); i++) {
            // 取出子弹
            Shot myShot = hero.ss.get(i);
            // 判断子弹是否有效
            if (myShot.isLive == true) {
                // 取出坦克进行判断
                for (int j = 0; j < ets.size(); j++) {
                    // 取出坦克
                    EnemyTank et = ets.get(j);
                    if (et.isLive) {
                        if (hitTank(myShot, et)) {
                            // 减少敌人数量
                            Recorder.reduceEnNum();
                            // 增加我的战绩
                            Recorder.addEnNumRec();
                        }

                    }
                }
            }
        }
    }

    // 判断子弹是否击中我的坦克的函数
    public void hisMy() {
        for (int i = 0; i < ets.size(); i++) {
            // 取出敌人坦克
            EnemyTank et = ets.get(i);
            // 取出子弹
            for (int j = 0; j < et.ss.size(); j++) {
                // 判断子弹是否有效
                Shot enemyShot = et.ss.get(j);
                if (enemyShot.isLive) {
                    if (hero.isLive) {
                        this.hitTank(enemyShot, hero);
                    }

                }

            }

        }
    }

    // 判断子弹是否击中敌人的坦克的函数
    public boolean hitTank(Shot s, Tank tk) {
        boolean b2 = false;
        if (s.x <= tk.x + 15 && s.x >= tk.x - 15 && s.y <= tk.y + 15
                && s.y >= tk.y - 15) {
            // 击中
            // 子弹死亡
            s.isLive = false;
            // 敌人死亡
            tk.isLive = false;
            b2 = true;
            // 创建一个炸弹，放入Vector
            Bomb b = new Bomb(tk.x - 15, tk.y - 15);
            // 放入
            bombs.add(b);

        }
        return b2;
    }

    // 画出坦克的函数
    public void drawtank(int x, int y, int direct, int type, Graphics g) {
        // 判断什么坦克
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;
        }

        // 判断方向
        switch (direct) {
            case 1:
                // g.setColor(Color.red);

                // 画出我的坦克（到时封装成一个函数）
                // 1\画出一个左边矩形
                g.fill3DRect((x - 15), (y - 15), 8, 30, false);
                // 2、画出一个右边的矩形
                g.fill3DRect((x + 7), y - 15, 8, 30, false);
                // 3、画出一个中间的矩形
                g.fill3DRect((x - 7), (y - 10), 14, 20, false);
                // 4、画出一个中间的圆形
                g.fillOval((x - 8), (y - 7), 14, 14);
                // 4、画出一个中间的线
                g.drawLine(x, y - 15, x, y);
                break;

            case 2:
                // 1\画出一个上边矩形
                g.fill3DRect((x - 15), (y - 15), 30, 8, false);
                // 2、画出一个下边的矩形
                g.fill3DRect((x - 15), (y + 7), 30, 8, false);
                // 3、画出一个中间的矩形
                g.fill3DRect((x - 10), (y - 7), 20, 14, false);
                // 4、画出一个中间的圆形
                g.fillOval((x - 7), (y - 8), 14, 14);
                // 4、画出一个中间的线
                g.drawLine(x, y, (x + 15), (y));
                break;

            case 3:
                // 1\画出一个左边矩形
                g.fill3DRect((x - 15), (y - 15), 8, 30, false);
                // 2、画出一个右边的矩形
                g.fill3DRect((x + 7), (y - 15), 8, 30, false);
                // 3、画出一个中间的矩形
                g.fill3DRect((x - 7), (y - 10), 14, 20, false);
                // 4、画出一个中间的圆形
                g.fillOval((x - 8), (y - 7), 14, 14);
                // 4、画出一个中间的线
                g.drawLine(x, y, x, (y + 15));
                break;

            case 4:
                // 1\画出一个上边矩形
                g.fill3DRect((x - 15), (y - 15), 30, 8, false);
                // 2、画出一个下边的矩形
                g.fill3DRect((x - 15), (y + 7), 30, 8, false);
                // 3、画出一个中间的矩形
                g.fill3DRect((x - 10), (y - 7), 20, 14, false);
                // 4、画出一个中间的圆形
                g.fillOval((x - 7), (y - 9), 14, 14);
                // 4、画出一个中间的线
                g.drawLine(x - 15, y, (x), (y));
                break;
        }

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            this.hero.moveUp();
            this.hero.setDirect(1);// 设置方向
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.hero.moveRight();
            this.hero.setDirect(2);// 设置方向
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.hero.moveDown();
            this.hero.setDirect(3);// 设置方向
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.hero.moveLeft();
            this.hero.setDirect(4);// 设置方向
        }
        // 判断玩家是否按了f键
        if (e.getKeyCode() == KeyEvent.VK_F) {
            // 开火
            if (hero.ss.size() <= 4) {
                this.hero.shotEnemy();
            }
        }
        this.repaint();
    }

    public void keyReleased(KeyEvent e) {

    }

    public void run() {

        while (true) {
            // 让线程休息
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            // 判断是否击中敌人坦克
            this.hitEnemyTank();
            // 判断是否击中我的坦克
            this.hisMy();

            // 重绘
            this.repaint();

        }
    }

}