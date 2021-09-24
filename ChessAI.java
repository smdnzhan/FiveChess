package Chess0922;

import java.util.HashMap;

public class ChessAI implements ChessConfig{
    public GamePanel gp;
    public GameMouse gm;
    public static HashMap<String,Integer> Weight = new HashMap<>();
    int[][] value = new int[11][11];

    public ChessAI() {
        Weight.put("1", 20);
        Weight.put("11",200);
        Weight.put("111",800);
        Weight.put("1111",2000);
        Weight.put("2",20);
        Weight.put("22",300);
        Weight.put("222",900);
        Weight.put("2222",4000); //默认AI下白子 简易版
    }
    public void clearValue(int[][]value){
        for (int i=0;i<=SQUARE_NUM;i++){
            for (int j=0;j<=SQUARE_NUM;j++){
                value[i][j]=0;
            }
        }
    }

    public int[] ai(int [][]map){

        for (int i =0;i<=SQUARE_NUM;i++){
            for (int j = 0;j<=SQUARE_NUM;j++){
                if (map[i][j]==0){
                    //向右
                    String zeroPoint ="";
                    for(int temp=1;i+temp<=SQUARE_NUM;temp++){
                        int first = map[i+1][j];
                        if (map[i+temp][j]!=0&&map[i+temp][j]==first){
                            zeroPoint+=map[i+temp][j]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }
                    //向左
                    zeroPoint ="";
                    for(int temp=1;i-temp>=0;temp++){
                        int first = map[i-1][j];
                        if (map[i-temp][j]!=0&&map[i-temp][j]==first){
                            zeroPoint+=map[i-temp][j]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }
                    //向上
                    zeroPoint ="";
                    for(int temp=1;j-temp>=0;temp++){
                        int first = map[i][j-1];
                        if (map[i][j-temp]!=0&&map[i][j-temp]==first){
                            zeroPoint+=map[i][j-temp]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }
                    //向下
                    zeroPoint ="";
                    for(int temp=1;j+temp<=SQUARE_NUM;temp++){
                        int first = map[i][j+1];
                        if (map[i][j+temp]!=0&&map[i][j+temp]==first){
                            zeroPoint+=map[i][j+temp]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }
                    //向左上
                    zeroPoint ="";
                    for(int temp=1;i-temp>=0&&j-temp>=0;temp++){
                        int first = map[i-1][j-1];
                        if (map[i-temp][j-temp]!=0&&map[i-temp][j-temp]==first){
                            zeroPoint+=map[i-temp][j-temp]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }
                    //右下
                    zeroPoint ="";
                    for(int temp=1;i+temp<=SQUARE_NUM&&j+temp<=SQUARE_NUM;temp++){
                        int first = map[i+1][j+1];
                        if (map[i+temp][j+temp]!=0&&map[i+temp][j+temp]==first){
                            zeroPoint+=map[i+temp][j+temp]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }
                    //右上
                    zeroPoint ="";
                    for(int temp=1;i+temp<=SQUARE_NUM&&j-temp>=0;temp++){
                        int first = map[i+1][j-1];
                        if (map[i+temp][j-temp]!=0&&map[i+temp][j-temp]==first){
                            zeroPoint+=map[i+temp][j-temp]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }

                    //左下
                    zeroPoint ="";
                    for(int temp=1;i-temp>=0&&j+temp<=SQUARE_NUM;temp++){
                        int first = map[i-1][j+1];
                        if (map[i-temp][j+temp]!=0&&map[i-temp][j+temp]==first){
                            zeroPoint+=map[i-temp][j+temp]+"";
                        }else
                            break; //碰到空位置就结束
                    }if (Weight.get(zeroPoint)!=null){
                        value[i][j]+=Weight.get(zeroPoint);
                    }
                }
                else
                    value[i][j]=0;
            }
        }
        int max = 0;
        int xx=0,yy=0;
        for (int i=0;i<=SQUARE_NUM;i++){
            for (int j=0;j<=SQUARE_NUM;j++){
                if (max<value[i][j]){
                    max = value[i][j];
                    xx=i;
                    yy=j;
                }
            }
        }
        clearValue(value);
    return new int[]{xx, yy};
    }
}
