package test4;

/**
 * Created by wenle on 15-9-6.
 */
// 子弹类
class Shot implements Runnable {
    int x;
    int y;
    int direct;
    int speed = 2;
    boolean isLive = true;

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public void run() {

        while (true) {
            // 让线程休息
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            switch (direct) {
                case 1:// 上
                    y -= speed;
                    break;
                case 2:// 右
                    x += speed;
                    break;// 下
                case 3:
                    y += direct;
                    break;// 左
                case 4:
                    x -= speed;
                    break;

            }
            // 子弹什么时候死亡

            // 判断子弹是否碰到边缘
            if (x < 0 || x > 400 || y < 0 || y > 300) {
                this.isLive = false;
                break;
            }
        }
    }
}