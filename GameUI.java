package Chess0922;

import javax.swing.JFrame;


import java.awt.*;

/**
 * 界面类
 五子棋游戏：1.游戏界面:javax.swing(可视化组件类)      java.awt(元素组件)
 2.监听器：设置界面程序的交互功能
 3.在界面程序中显示内容(棋子)
 练习：1.绘制棋盘   2.把棋子落在棋盘的交点上  3.黑白交替    4.判断输赢问题？
 * @author chen
 *
 */
public class GameUI {

    public static void main(String[] args) {
        GameUI ui = new GameUI();
        ui.showUI();

    }

    //1.显示游戏界面
    public void showUI() {
        //窗体
        JFrame jf = new JFrame();
        GamePanel gp = new GamePanel(jf);

        //调用画布类 并添加进窗体
        jf.add(gp);
        //像素点>分辨率
        jf.setSize(800, 800);
        jf.setTitle("五子棋游戏");
        jf.getContentPane().setBackground(Color.pink);
        //居中显示
        jf.setLocationRelativeTo(null);
        //设置退出进程
        jf.setDefaultCloseOperation(3);
        //设置可见
        jf.setVisible(true);
        //3.画笔：图形内容显示在那个组件上，画笔就从该组件上获取
        //从窗体上获取画笔，一定要在窗体显示可见之后
        Graphics g = gp.getGraphics();



        //2.监听器
        //a.事件源：当前动作所发生的组件(窗体)
        //b.监听器：鼠标监听器方法
        //c.绑定事件处理类

        GameMouse mouse = new GameMouse();
        mouse.setG(g);
        mouse.setGP(gp);
        gp.setGM(mouse);
        //给窗体添加鼠标监听器方法
        gp.addMouseListener(mouse);

    }
}