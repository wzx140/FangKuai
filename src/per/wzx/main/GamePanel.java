package per.wzx.main;

import per.wzx.component.Block;
import per.wzx.component.Cell;
import per.wzx.component.Shap;
import per.wzx.controler.ShapControl;
import per.wzx.controler.TimeDown;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Timer;

/**
 * 一个棋盘类
 */
public class GamePanel extends MainFrame {
    private HashMap<Point, Integer> gamePanel;  //棋盘数据
    private Block block;
    private Shap shap;
    private int row;  //棋盘行数
    private int column;  //棋盘列数
    private Cell[] temp;  //cell的一个暂时缓存
    private boolean over;  //游戏结束标志
    private boolean suspend; //游戏暂停标志
    private float suspendTime; //暂停的时间
    //    private boolean over;
    public GamePanel(int row, int column) {
        this.row = row;
        this.column = column;
        gamePanel = new HashMap<>();
        initGamePanel();
        block = new Block(column,row);
        block.addCells(initBlockedCells());  //添加最底层blocked cells
        initListenner();
        over = true;
        suspendTime=0;
    }

    //初始化监听器
    private void initListenner() {
        ShapControl shapControl = ShapControl.getShapControl();
        shapControl.setGamePanel(this);
        textArea.addKeyListener(shapControl);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                over = false;
                if (getShap() == null) {
                    shapInit();
                    print();
                    textArea.requestFocus();
                }
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!over) {
                    over = true;
                    suspendTime=0;
                    shap=null;
                    block=new Block(column,row);
                    block.addCells(initBlockedCells());
                    printOver();
                    textArea.requestFocus();
                }
            }
        });
        suspendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!over&&!suspend) {
                    suspend = true;
                } else if (!over&&suspend) {
                    suspend = false;
                }
                textArea.requestFocus();
            }
        });
    }

    public float getSuspendTime() {
        return suspendTime;
    }

    public void setSuspendTime() {
        suspendTime+=0.5;
    }
    //游戏结束 分数结算
    private void gameAccount() {
        shap=null;
        int score = block.getScore();
        block=new Block(column,row);
        block.addCells(initBlockedCells());
        printOver();
        textArea.append("你的分数是"+score+'\n');
        textArea.append("暂停了"+suspendTime+"s");
    }

    //结束画面
    private void printOver() {
        textArea.setText("OVER \n");
    }
    //出现一个随机shap
    public void shapInit() {
        shap = new Shap(randomModel(), column);
        print();
        if (!isOver()) {
            TimeDown timeDown = new TimeDown(this);
            java.util.Timer timer = new Timer();
            timer.schedule(timeDown, 500, 500);
        } else {
            gameAccount();
        }
    }

    //get inited blocked cells
    private Cell[] initBlockedCells() {
        Cell[] cells = new Cell[column];
        for (int i = 0; i < column; i++) {
            cells[i] = new Cell(row + 1, i + 1, true);
        }
        return cells;
    }

    //初始化一个全部为不占用的棋盘
    private void initGamePanel() {
        for (int i = 1; i <= row + 1; i++) {
            for (int j = 1; j <= column; j++) {
                gamePanel.put(new Point(i, j), 0);
            }
        }
    }

    //打印棋盘
    public void print() {
        setData();  //更新棋盘数据
        int count = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= row + 1; i++) {
            for (int j = 1; j <= column; j++) {
                count++;
                builder.append(printCell(gamePanel.get(new Point(i, j))));
                if (count % column == 0) {
                    builder.append('\n');
                }
            }
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textArea.setText(builder.toString());
            }
        });

    }

    public boolean getOver() {
        return over;
    }
    //将blocked shap添加到block
    public void addBlock() {
        block.addCells(shap.getStatedCells());
    }

    public Shap getShap() {
        return shap;
    }

    public Block getBlock() {
        return block;
    }

    private boolean isOver() {
        Cell[] cells = block.getLastCells();
        for (Cell cell : cells) {
            if (cell.getX() == 1) {
                over = true;
                return true;
            }
        }
        return false;
    }

    public boolean isSuspend() {
        return suspend;
    }
    private String printCell(Integer state) {
        if (state.equals(0)) {
            return "o ";
        } else {
            return "●";
//            return "·";
        }
    }

    private void setData() {
        initGamePanel();
        if (shap == null) {
            temp = block.getCells();
            setGamePanel(temp);  //用cell数组代替棋盘里的相同位置的cell
        } else {
            temp = block.getCells();
            setGamePanel(temp);

            temp = shap.getStatedCells();
            setGamePanel(temp);
        }
    }

    private void setGamePanel(Cell[] temp) {
        for (Cell cell : temp) {
            gamePanel.put(new Point(cell.getX(), cell.getY()), state(cell.isState()));
        }
    }

    //将boolen转化为1,2
    private int state(boolean cellState) {
        if (cellState) {
            return 1;
        } else {
            return 0;
        }
    }

    private int randomModel() {
        double i = Math.random() * 3;
        if (i <= 1) {
            return 0;
        } else if (i <= 2) {
            return 1;
        } else {
            return 2;
        }
    }

}
