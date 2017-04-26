package com;

import javax.swing.*;
import java.awt.*;

/**
 * Created by wenle on 15-9-6.
 */

// 就是一个提示的作用
class MyStarPanle extends JPanel implements Runnable {
    int times = 0;

    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 400, 300);
        // 提示信息
        if (times % 2 == 0) {
            g.setColor(Color.yellow);
            // 开关信息的字体
            Font myFont = new Font("华文新魏", Font.BOLD, 40);
            g.setFont(myFont);
            g.drawString("stage:1", 130, 150);
        }

    }

    public void run() {

        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {

                e.printStackTrace();

            }
            times++;
            this.repaint();
        }
    }
}
