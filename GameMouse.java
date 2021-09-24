package Chess0922;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Stack;

//事件处理类
//实现接口：public class 子类 implements 接口，接口，，{}
public class GameMouse implements MouseListener,ChessConfig {
    private Graphics g;  //定义变量，保存传递过来的画笔对象
    private JFrame jf;
    public GamePanel gp;
    public int turn;
    public int flag ;
    public int xc ,yc; //棋子的交点值
    public boolean start;
    public String message;
    public Stack<Integer> stckx;
    public Stack<Integer> stcky;
    public boolean AI;
    ChessAI ai = new ChessAI();
    //set方法，初始化棋盘和画笔
    public void setG(Graphics g) {
        this.g = g;
    }

    public void setGP(GamePanel gp){
        this.gp = gp;
    }

    public GameMouse(){
        this.stckx=new Stack<>();
        this.stcky=new Stack<>();
        this.turn=1;
        this.flag=0;
        this.start=true;
        this.message="";
        this.AI= false;
    }

    //事件处理方法
    public void mouseClicked(MouseEvent e) {
        //获取当前坐标值
        int x = e.getX();
        int y = e.getY();

        //超出棋盘的点落在边界点上
        if(y>YY){
            y=YY;
        }
        if(x>XX){
            x=XX;
        }
        if (y<Y0){
            y=Y0;
        }
        if(x<X0){
            x=X0;
        }

        //计算棋子坐标
        xc = positionX(x);
        yc = positionY(y);

        //绘制棋子
        if (flag!=0){
            start=false;
        }

        if (isAvailable(x,y)&&isFree(xc,yc)&&start){
            if (turn==1){
                drawBlack(g,xc,yc);
                gp.setMap(xc,yc,1);
                message="白棋";
                turn=2;
                sumAll(gp.getMap(),xc,yc);
                if (AI&&flag!=1){
                    xc=ai.ai(gp.getMap())[0];
                    yc=ai.ai(gp.getMap())[1];
                    drawWhite(g,xc,yc);
                    gp.setMap(xc,yc,2);
                    message="黑棋";
                    turn=1;
                }

            }else if(turn==2&&!AI){
                drawWhite(g,xc,yc);
                gp.setMap(xc,yc,2);
                message="黑棋";
                turn=1;
            }
        }
        stckx.push(xc);
        stcky.push(yc);
        indicator(); //保持指示更新
        System.out.println("落点"+"("+xc+","+yc+")");
        sumAll(gp.getMap(), xc,yc);
        isWinner(flag);
        System.out.println("============================================");
    }
    //判断是否已经有棋子
    public boolean isFree(int x,int y){
        int [][] map = gp.getMap();
        if(gp.getChess(map,x,y)==0){
        return true;}
        return false;
    }
    //指示顺序
    public void indicator(){
        g.setColor(Color.PINK);
        g.fillRect(680,25,100,50);
        g.setColor(Color.black);
        g.setFont(gp.createFont());
        g.drawString("当前："+message,680,50);
    }

    public void drawBlack(Graphics g,int x,int y ){
        g.setColor(Color.BLACK);
        g.fillOval(x*SQUARE+X0-CHESS/2,y*SQUARE+Y0-CHESS/2,CHESS,CHESS);
    }
    public void drawWhite(Graphics g,int x,int y){
        g.setColor(Color.WHITE);
        g.fillOval(x*SQUARE+X0-CHESS/2,y*SQUARE+Y0-CHESS/2,CHESS,CHESS);
    }

    //判断棋子是否在棋盘上
    public boolean isAvailable(int x,int y){
        if (x>=X0&&y>=Y0&&x<=XX&&y<=YY){
            System.out.println("在棋盘上");
            return true;
        }return false;
    }

    //计算棋子位置
    public int positionX(int s){
        int res=0;
        if((s-X0)%SQUARE<=SQUARE/2){
            res = (s-X0)/SQUARE;
        }if((s-X0)%SQUARE>SQUARE/2){
            res = (s-X0)/SQUARE+1;
        }
        return res;
    }
    public int positionY(int s){
        int res=0;
        if((s-Y0)%SQUARE<=SQUARE/2){
            res = (s-X0)/SQUARE;
        }if((s-Y0)%SQUARE>SQUARE/2){
            res = (s-X0)/SQUARE+1;
        }
        return res;
    }
    //向上统计
    public int count_Up(int[][]map,int i,int j){
        int count = 0;
        int y=j;
        while (i >=0&&y>0&& i <=SQUARE_NUM&&y<=SQUARE_NUM){
            if (map[i][y]!=0){
                if (map[i][y]==map[i][y-1]){
                    count++;
                    y--;
                }else
                    return count;
            }
            else
                break;
        }
        return count;
    }
    //向下统计
    public int count_Down(int[][]map,int i,int j) {
        int count = 0;
        int y = j;
        while (i >= 0 && y >= 0 && i <= SQUARE_NUM && y < SQUARE_NUM) {
            if (map[i][y] != 0) {
                if (map[i][y] == map[i][y + 1]) {
                    count++;
                    y++;
                } else
                    return count;
            }
            else
                break;
        }
        return count;
    }
    //向左统计
    public int count_Left(int[][]map,int i,int j) {
        int count = 0;
        int x = i;
        while (x > 0 && j >= 0 && x <= SQUARE_NUM && j <= SQUARE_NUM) {
            if (map[x][j] != 0) {
                if (map[x][j] == map[x-1][j]) {
                    count++;
                    x--;
                } else
                    return count;
            }
            else
                break;
        }
        return count;
    }

    //向右统计
    public int count_Right(int[][]map,int i,int j) {
        int count = 0;
        int x = i;
        while (x >= 0 && j >= 0 && x < SQUARE_NUM && j <= SQUARE_NUM) {
            if (map[x][j] != 0) {
                if (map[x][j] == map[x+1][j]) {
                    count++;
                    x++;
                } else
                    return count;
            }
            else
                break;
        }
        return count;
    }
    //右下统计

    public int count_RightDown(int[][]map,int i,int j){

        int count = 0;
        int x=i;
        int y=j;
        while (x>=0&&y>=0&&x<SQUARE_NUM&&y<SQUARE_NUM){ //边界处对应处理
            if (map[x][y]!=0){
                if (map[x][y]==map[x+1][y+1]){
                    count++;
                    x++;y++;
                }else
                    return count;
            }
            else
                break;
        }
        return count;
    }
    //左上相同棋子统计
    public int count_LeftUp(int[][]map,int i,int j){

        int count = 0;
        int x=i;
        int y=j;
        while (x>0&&y>0&&x<=SQUARE_NUM&&y<=SQUARE_NUM){
            if (map[x][y]!=0){
                if (map[x][y]==map[x-1][y-1]){
                    count++;
                    x--;y--;
                }else
                    return count;
            }
            else
                break;
        }
        return count;
    }
    //右上相同棋子统计
    public int count_RightUp(int[][]map,int i,int j){

        int count = 0;
        int x=i;
        int y=j;
        while (x>=0&&y>0&&x<SQUARE_NUM&&y<=SQUARE_NUM){
            if (map[x][y]!=0){
                if (map[x][y]==map[x+1][y-1]){
                    count++;
                    x++;y--;
                }else
                    return count;
            }
            else
                break;
        }
        return count;
    }
    //左下相同棋子统计
    public int count_LeftDown(int[][]map,int i,int j){

        int count = 0;
        int x=i;
        int y=j;
        while (x>0&&y>=0&&x<=SQUARE_NUM&&y<SQUARE_NUM){
            if (map[x][y]!=0){
                if (map[x][y]==map[x-1][y+1]){
                    count++;
                    x--;y++;
                }else
                    return count;
            }
            else
                break;
        }
        return count;
    }
    //全向统计
    public void sumAll(int[][]map,int x,int y){
        int sum1 = count_LeftUp(map,x,y)+count_RightDown(map,x,y);
        int sum2 = count_LeftDown(map,x,y)+count_RightUp(map,x,y);
        int sum3 = count_Up(map,x,y)+count_Down(map,x,y);
        int sum4 = count_Left(map,x,y)+count_Right(map,x,y);
        if(map[x][y]==1&&((sum1>=4)||(sum2>=4)||(sum3>=4)||(sum4>=4))){
            flag=1;
        }else if(map[x][y]==2&&((sum1>=4)||(sum2>=4)||(sum3>=4)||(sum4>=4))){
            flag=2;
        }else
            flag=0;
    }

    //判断获胜方
    public void isWinner(int flag){
        if (flag == 1){
           JOptionPane.showMessageDialog(null,"黑棋胜利");
           this.start=false;
        }else if(flag==2){
            JOptionPane.showMessageDialog(null,"白棋胜利");
            this.start=false;
        }else{
            System.out.println("暂无获胜者");
        }
    }


    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {
    }

}