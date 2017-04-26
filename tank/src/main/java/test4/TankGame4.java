/**
 * 功能：坦克游戏4.0
 * 1.画出坦克
 * 2.我的坦克可以上下左右移动
 * 3.可以发子弹，子弹可以连发（最多5颗子弹）
 * 4.当击中敌人坦克时，敌人坦克消失并有爆炸的效果
 * 5.我被击中后，显示爆炸效果
 * 6.防止敌人重叠运动
 *  6.1判断是否碰撞的函数放在Enemytank类中
 * 7.可以分关
 * 	7.1做一个空的开始panle
 *	7.2字体闪烁
 * 8.可以在游戏时暂停和继续
 * 	8.1用户点击停止的时候，子弹坦克的速度设为0并且方向不变
 * 9.可以记录玩家成绩
 * 	9.1文件流	
 * 	9.2单写一个记录类，记录游戏记录
 * 	9.3先完成保存共击溃多少辆敌人坦克
 * 	9.4存盘退出游戏，记录游戏状况
 * 10.java操作声音
 * 	10.1 文件操作
 * 11网络大战
 *
 */
package test4;

import javax.swing.*;

import java.awt.event.*;

public class TankGame4 extends JFrame implements ActionListener {
    // 添加部件
    MyPanel mp = null;
    MyStarPanle msp = null;
    // 做出我需要的菜单
    JMenuBar jmb = null;
    // 开始游戏
    JMenu jm1 = null;
    JMenuItem jmi = null;
    // 退出系统
    JMenuItem jmi1 = null;
    // 存盘退出
    JMenuItem jmi2 = null;
    // 回到上一局
    JMenuItem jmi3 = null;

    public static void main(String[] args) {

        TankGame4 tg = new TankGame4();

    }

    // 构造函数
    public TankGame4() {

        // 创建菜单及菜单选项
        jmb = new JMenuBar();
        jm1 = new JMenu("游戏(G)");
        // 设置快捷方式
        jm1.setMnemonic('G');

        jmi = new JMenuItem("开始新游戏(N)");
        jmi1 = new JMenuItem("退出系统(E)");
        jmi2 = new JMenuItem("存盘退出(SQ)");
        jmi3 = new JMenuItem("回到上一局(O)");
        // 对jmi响应
        jmi.addActionListener(this);
        jmi1.addActionListener(this);
        jmi2.addActionListener(this);
        jmi3.addActionListener(this);
        jmi.setActionCommand("newGame");
        jmi1.setActionCommand("Exit");
        jmi2.setActionCommand("saveExit");
        jmi3.setActionCommand("comGame");

        jm1.add(jmi);
        jm1.add(jmi2);
        jm1.add(jmi3);
        jm1.add(jmi1);
        jmb.add(jm1);

        msp = new MyStarPanle();
        Thread t = new Thread(msp);
        t.start();

        this.setJMenuBar(jmb);
        this.add(msp);
        // 设置窗体大小
        this.setSize(600, 400);
        // 设置标题
        this.setTitle("我的坦克游戏4.0");
        // 设置起初位置

        this.setLocation(200, 200);
        this.setIconImage((new ImageIcon("Image//tk.jpg")).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        // 对用户不同的点击做出不同的处理
        if (e.getActionCommand().equals("newGame")) {
            // 创建战场面板
            mp = new MyPanel("newGame");
            // 启动线程
            Thread t = new Thread(mp);
            t.start();
            // 先删除旧的面板
            this.remove(msp);
            // 添加组件
            this.add(mp);
            // 注册监听
            this.addKeyListener(mp);
            // 显示，刷新
            this.setVisible(true);

        } else if (e.getActionCommand().equals("Exit")) {
            // 保存击溃敌人数量
            Recorder.keepRecording();
            System.exit(0);
        } else if (e.getActionCommand().equals("saveExit")) {
            // 存盘退出

            Recorder.setEts(mp.ets);
            // 保存敌人的数量和敌人的坐标
            Recorder.keepRecAndEnemyTank();
            // 退出
            System.exit(0);
        } else if (e.getActionCommand().equals("comGame")) {

            // 创建战场面板
            mp = new MyPanel("comGame");

            // 启动线程
            Thread t = new Thread(mp);
            t.start();
            // 先删除旧的面板
            this.remove(msp);
            // 添加组件
            this.add(mp);
            // 注册监听
            this.addKeyListener(mp);
            // 显示，刷新
            this.setVisible(true);

        }
    }
}


