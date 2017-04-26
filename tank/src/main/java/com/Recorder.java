package com;

import java.io.*;
import java.util.Vector;

/**
 * Created by wenle on 15-9-6.
 */
// 记录类，同时可以保存玩家的设置
class Recorder {
    // 记录每关有多少敌人
    private static int enNum = 10;
    // 设置我有多少生命
    private static int myLivfe = 3;
    // 记录总共消灭了多少辆坦克
    private static int allEnNum = 0;

    private static String myRecording = "./tank/static/myRecording.txt";
    // 从文件中恢复点
    static Vector<Node> nodes = new Vector<Node>();

    private static FileWriter fw = null;
    private static BufferedWriter bw = null;
    private static FileReader fr = null;
    private static BufferedReader br = null;

    private static Vector<EnemyTank> ets = new Vector<EnemyTank>();

    // 读取记录
    public Vector<Node> getNodesAndEnNums() {

        try {
            fr = new FileReader(myRecording);
            br = new BufferedReader(fr);
            String n = "";
            // 先读取一行
            n = br.readLine();
            allEnNum = Integer.parseInt(n);
            while ((n = br.readLine()) != null) {
                String[] xyz = n.split(" ");
                Node node = new Node(Integer.parseInt(xyz[0]),
                        Integer.parseInt(xyz[1]), Integer.parseInt(xyz[2]));
                nodes.add(node);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                br.close();
                fr.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return nodes;
    }

    // 保存击溃敌人的数量和敌人的坐标，方向
    public static void keepRecAndEnemyTank() {
        try {
            // 创建文件流
            fw = new FileWriter(myRecording);
            bw = new BufferedWriter(fw);
            bw.write(allEnNum + "\r\n");

            // 保存当前或者的敌人坐标和方向
            for (int i = 0; i < ets.size(); i++) {
                // 取出一辆坦克
                EnemyTank et = ets.get(i);
                if (et.isLive) {
                    // 活着就保存
                    String recode = et.x + " " + et.y + " " + et.direct
                            + "\r\n";
                    // 写入
                    bw.write(recode);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                bw.close();
                fw.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }

    }

    // 从文件中读取记录
    public static void getRecoring() {
        try {
            fr = new FileReader(myRecording);
            br = new BufferedReader(fr);
            String n = br.readLine();
            allEnNum = Integer.parseInt(n);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                br.close();
                fr.close();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // 把玩家击溃的敌人坦克数量保存到文件
    public static void keepRecording() {

        try {
            // 创建文件流
            fw = new FileWriter(myRecording);
            bw = new BufferedWriter(fw);
            bw.write(allEnNum + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                bw.close();
                fw.close();
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

    public static int getEnNum() {
        return enNum;
    }

    public static void setEnNum(int emNum) {
        Recorder.enNum = emNum;
    }

    public static int getMyLivfe() {
        return myLivfe;
    }

    public static void setMyLivfe(int myLivfe) {
        Recorder.myLivfe = myLivfe;
    }

    public static int getAllEnNum() {
        return allEnNum;
    }

    public static Vector<EnemyTank> getEts() {
        return ets;
    }

    public static void setEts(Vector<EnemyTank> ets1) {
        ets = ets1;
    }

    public static void setAllEnNum(int allEnNum) {
        Recorder.allEnNum = allEnNum;
    }

    // 减少敌人数
    public static void reduceEnNum() {
        enNum--;

    }

    // 消灭敌人
    public static void addEnNumRec() {
        allEnNum++;
    }
}