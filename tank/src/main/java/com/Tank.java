package com;

import java.util.Vector;

/**
 * Created by wenle on 15-9-6.
 */
// 坦克类
class Tank {
    // 表示坦克的x坐标
    int x = 0;
    // 表示坦克的y坐标
    int y = 0;
    // 表示方向
    int direct = 1;
    // 表示速度
    int speed = 2;
    // 表示颜色
    int type;
    boolean isLive = true;

    // 子弹
    Vector<Shot> ss = new Vector<Shot>();
    Shot s = null;

    Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
