package test4;

import java.util.Vector;

/**
 * Created by wenle on 15-9-6.
 */
// 敌人坦克,升级坦克，做成线程
class EnemyTank extends Tank implements Runnable {
    int times = 0;

    // 定义一个向量可以访问到MyPanel上的所有敌人坦克
    Vector<EnemyTank> ets = new Vector<EnemyTank>();

    EnemyTank(int x, int y) {
        super(x, y);

    }

    // 得到MyPanel敌人坦克向量
    public void setEts(Vector<EnemyTank> vv) {
        this.ets = vv;
    }

    // 判断是否碰到别的坦克
    public boolean isTouchotherEnemy() {
        boolean b = false;

        switch (this.direct) {
            case 1:
                // 取出所有坦克
                for (int i = 0; i < ets.size(); i++) {
                    // 取出第一辆坦克
                    EnemyTank et = ets.get(i);

                    // 如果不是自己
                    if (et != this) {

                        if (this.x >= et.x - 30 && this.x <= et.x + 30
                                && this.y >= et.y && this.y <= et.y + 30) {
                            return true;
                        }

                    }
                }

                break;
            case 2:
                // 取出所有坦克
                for (int i = 0; i < ets.size(); i++) {
                    // 取出第一辆坦克
                    EnemyTank et = ets.get(i);

                    // 如果不是自己
                    if (et != this) {

                        if (this.x >= et.x - 30 && this.x <= et.x
                                && this.y >= et.y - 30 && this.y <= et.y + 30) {
                            return true;
                        }

                    }
                }

                break;
            case 3:
                // 取出所有坦克
                for (int i = 0; i < ets.size(); i++) {
                    // 取出第一辆坦克
                    EnemyTank et = ets.get(i);

                    // 如果不是自己
                    if (et != this) {

                        if (this.x >= et.x - 30 && this.x <= et.x + 30
                                && this.y >= et.y - 30 && this.y <= et.y) {
                            return true;
                        }

                    }
                }
                break;
            case 4:
                // 取出所有坦克
                for (int i = 0; i < ets.size(); i++) {
                    // 取出第一辆坦克
                    EnemyTank et = ets.get(i);

                    // 如果不是自己
                    if (et != this) {

                        if (this.x >= et.x && this.x <= et.x + 30
                                && this.y >= et.y - 30 && this.y <= et.y + 30) {
                            return true;
                        }

                    }
                }

                break;

        }

        return b;

    }

    public void run() {

        while (true) {
            // 让线程休息
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            switch (this.direct) {
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (y > 15 && !isTouchotherEnemy()) {
                            y -= speed;
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }

                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (x < 385 && !isTouchotherEnemy()) {
                            x += speed;
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }

                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (y < 285 && !isTouchotherEnemy()) {
                            y += speed;
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }

                    break;
                case 4:
                    for (int i = 0; i < 30; i++) {
                        if (x > 15 && !isTouchotherEnemy()) {
                            x -= speed;
                        }

                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {

                            e.printStackTrace();
                        }
                    }

                    break;
            }

            // 判断敌人坦克是否添加子弹
            times++;
            if (times % 2 == 1) {
                if (isLive) {
                    if (ss.size() < 5) {

                        // 没有子弹
                        // 添加一颗
                        Shot s = null;
                        switch (direct) {
                            case 1:
                                s = new Shot(x, y - 15, 1);
                                ss.add(s);
                                break;
                            case 2:
                                s = new Shot(x + 15, y, 2);
                                ss.add(s);
                                break;
                            case 3:
                                s = new Shot(x, y + 15, 3);
                                ss.add(s);
                                break;
                            case 4:
                                s = new Shot(x - 15, y, 4);
                                ss.add(s);
                                break;

                        }
                        // 启动线程
                        Thread t = new Thread(s);
                        t.start();
                    }
                }

            }

            // 让坦克随机产生新的方向
            this.direct = (int) (Math.random() * 5);

            // 判断坦克是否死亡
            if (isLive == false) {
                // 让坦克死亡后，结束线程
                break;
            }
        }

    }

}
