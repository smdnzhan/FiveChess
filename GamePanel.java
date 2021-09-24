package Chess0922;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener,ChessConfig {
    private JFrame jf ;
    private int[][] map = new int[11][11];
    public GameMouse gm;

    public int[][] getMap(){
        return map;
    }
    public int getChess(int[][]map,int x,int y){
        return map[x][y];
    }
    public void setGM(GameMouse gm){this.gm=gm;}
    public void setMap(int x,int y,int z){
        map[x][y]=z;
    }


    //构造方法
    public GamePanel(JFrame jf) {
        this.jf = jf;
        this.setOpaque(false);
        createMenu();
    }
    //创建菜单
    private void createMenu() {
        JMenuBar jmb = new JMenuBar();

        JMenu jMenu1 = new JMenu("游戏");
        jMenu1.setFont(createFont());
        JMenu jMenu2 = new JMenu("选项");
        jMenu2.setFont(createFont());

        //创建选项
        // JMenuItem->JMenu->JMenuBar
        JMenuItem jmi1= new JMenuItem("新游戏");
        jmi1.setFont(createFont());
        JMenuItem jmi2 = new JMenuItem("悔棋");
        jmi2.setFont(createFont());
        JMenuItem jmi3 = new JMenuItem("认输");
        jmi3.setFont(createFont());
        JMenuItem jmi4 = new JMenuItem("人机对战");
        jmi4.setFont(createFont());

        jMenu1.add(jmi1);
        jMenu1.add(jmi2);
        jMenu2.add(jmi3);
        jMenu2.add(jmi4);

        //添加监听器
        jmi1.addActionListener(this);
        jmi2.addActionListener(this);
        jmi3.addActionListener(this);
        jmi4.addActionListener(this);
        //设置指令
        jmi1.setActionCommand("restart");
        jmi2.setActionCommand("back");
        jmi3.setActionCommand("surrender");
        jmi4.setActionCommand("AI");

        //添加进MenuBar
        jmb.add(jMenu1);
        jmb.add(jMenu2);
        jf.setJMenuBar(jmb);
    }
    //绘制棋盘 10*10 每个正方形边长60 绘制棋子
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawGrid(g);
        drawChess(g);
        g.setColor(Color.PINK); //用于保持指示器
        g.fillRect(680,25,100,50);
        g.setColor(Color.black);
        g.setFont(createFont());
        g.drawString("当前："+gm.message,680,50);
    }

    private void drawChess(Graphics g) {
        for (int i = 0; i <=SQUARE_NUM ; i++) {
            for (int j = 0; j <=SQUARE_NUM ; j++){
                if (map[i][j]==1){
                    new GameMouse().drawBlack(g,i,j);
                }else if (map[i][j]==2){
                    new GameMouse().drawWhite(g,i,j);
                }
            }
        }
    }

    public void drawGrid(Graphics g){
        for (int i = 0; i < SQUARE_NUM; i++) {
            for (int j = 0; j <SQUARE_NUM ; j++) {
                g.drawRect((X0+SQUARE*j),(Y0+SQUARE*i),SQUARE,SQUARE);
            }
        }
    }

    //获取字体
    public Font createFont(){
        return new Font(LETTER,Font.BOLD,LETTER_SIZE);
    }

    public void newGame() {
        gm.message = "";
        gm.start = true;
        gm.turn = 1;
        for (int i = 0; i <= SQUARE_NUM; i++) {
            for (int j = 0; j <= SQUARE_NUM; j++) {
                this.map[i][j] = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("restart")) {
            newGame();
            gm.stckx.empty();
            gm.stcky.empty();
            repaint();
            System.out.println("清空棋盘");
            JOptionPane.showMessageDialog(null, "新游戏");
            gm.start = true;
        } else if (command.equals("back")) { //悔棋
            if (!gm.stckx.isEmpty()) {
                System.out.println("悔棋，返回上一步状态");
                Integer temp_x = gm.stckx.pop();
                Integer temp_y = gm.stcky.pop();
                if (map[temp_x][temp_y] == 1) {
                    setMap(temp_x,temp_y,0);
                    gm.message = "黑棋";
                    gm.turn = 1;
                    repaint();
                } else if (map[temp_x][temp_y] == 2) {
                    gm.message = "白棋";
                    gm.turn = 2;
                    setMap(temp_x,temp_y,0);
                    repaint();
                } else if (gm.stckx.size() == 1) {  //悔第一颗黑子
                    gm.message = " ";
                    gm.turn = 1;
                    setMap(temp_x,temp_y,0);
                    repaint();
                }
            }else
                System.out.println("无法悔棋");
        }
        else if(command.equals("surrender")){
            JOptionPane.showMessageDialog(null, gm.message + "认输");
            newGame();
            repaint();
        }else if(command.equals("AI")){
            JOptionPane.showMessageDialog(null, "人机对战开始！");
            gm.AI=true;
            newGame();
            repaint();
        }
    }
}
