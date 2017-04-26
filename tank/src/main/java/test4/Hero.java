package test4;

/**
 * Created by wenle on 15-9-6.
 */
// 我的坦克类
class Hero extends Tank {

    public Hero(int x, int y) {
        super(x, y);

    }

    // 开火
    public void shotEnemy() {
        switch (this.direct) {
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
        // 启动子弹线程
        Thread t = new Thread(s);
        t.start();
    }

    // 坦克向上移动
    public void moveUp() {
        y -= speed;
    }

    // 坦克向右移动
    public void moveRight() {
        x += speed;
    }

    // 坦克向下移动
    public void moveDown() {
        y += speed;
    }

    // 坦克向左移动
    public void moveLeft() {
        x -= speed;
    }

}
