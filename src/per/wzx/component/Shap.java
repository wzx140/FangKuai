package per.wzx.component;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wzx on 17-3-6.
 */
public class Shap implements Cloneable {
    public final static int model1 = 0;
    public final static int model2 = 1;
    public final static int model3 = 2;
    private Cell[] cells;
    private ReentrantLock lock;
    private boolean blockFlag;  //shap是否block
    private int column;
    private int currentModel;

    public Shap(int model, int column) {
        this.column = column;
        if (model == model1) {
            init1();
        } else if (model == model2) {
            init2();
        } else if (model == model3) {
            init3();
        }
        lock = new ReentrantLock();
        blockFlag = false;
        currentModel = model;
    }

    private void init1() {
        cells = new Cell[9];
        cells[0] = new Cell(1, 4, true);
        cells[1] = new Cell(1, 5, true);
        cells[2] = new Cell(1, 6, true);
        cells[3] = new Cell(2, 4, true);
        cells[4] = new Cell(2, 5, false);
        cells[5] = new Cell(2, 6, false);
        cells[6] = new Cell(3, 4, false);
        cells[7] = new Cell(3, 5, false);
        cells[8] = new Cell(3, 6, false);

    }

    private void init2() {
        cells = new Cell[9];
        cells[0] = new Cell(1, 4, true);
        cells[1] = new Cell(1, 5, true);
        cells[2] = new Cell(1, 6, true);
        cells[3] = new Cell(2, 4, false);
        cells[4] = new Cell(2, 5, true);
        cells[5] = new Cell(2, 6, false);
        cells[6] = new Cell(3, 4, false);
        cells[7] = new Cell(3, 5, false);
        cells[8] = new Cell(3, 6, false);

    }

    private void init3() {
        cells = new Cell[9];
        cells[0] = new Cell(1, 4, true);
        cells[1] = new Cell(1, 5, true);
        cells[2] = new Cell(1, 6, true);
        cells[3] = new Cell(2, 4, false);
        cells[4] = new Cell(2, 5, false);
        cells[5] = new Cell(2, 6, true);
        cells[6] = new Cell(3, 4, false);
        cells[7] = new Cell(3, 5, false);
        cells[8] = new Cell(3, 6, false);
    }


    //由按键做出回应
    public void keyTyped(char e) throws IOException {
        lock.lock();
        try {

            if (e == 's') {
                down();
            } else if (e == 'w') {
                up();
            } else if (e == 'a') {
                left();
            } else if (e == 'd') {
                right();
            } else {
                throw new IOException("wrong key");
            }
        } finally {
            lock.unlock();
        }
    }

    //shap下移，应用于timedown类
    public void shapDown() {
        lock.lock();
        try {
            down();
        } finally {
            lock.unlock();
        }
    }


    private void up() {
        if (currentModel == 1) { //第二形态的shap 需要变换两次
            upOnce();
            upOnce();
        } else {
            upOnce();
        }

    }

    private void upOnce() {
        boolean tempState1,tempState2;

        tempState1 = cells[3].isState();
        cells[3].setState(cells[0].isState());

        tempState2 = cells[6].isState();
        cells[6].setState(tempState1);

        tempState1 = cells[7].isState();
        cells[7].setState(tempState2);

        tempState2 = cells[8].isState();
        cells[8].setState(tempState1);

        tempState1 = cells[5].isState();
        cells[5].setState(tempState2);

        tempState2 = cells[2].isState();
        cells[2].setState(tempState1);

        tempState1 = cells[1].isState();
        cells[1].setState(tempState2);

        cells[0].setState(tempState1);
    }
    private void down() {
        for (int i = 0; i < 9; i++) {
            cells[i].down();
        }
    }

    private void left() {
        if (getLeftCell() > 1) {     //判断是否越界
            for (int i = 0; i < 9; i++) {
                cells[i].left();
            }
        }

    }

    private void right() {
        if (getRightCell() < column) {  //判断是否越界
            for (int i = 0; i < 9; i++) {
                cells[i].right();
            }
        }
    }

    //return 最左边的cell
    private int getLeftCell() {
        Cell[] cells = getStatedCells();
        int y = cells[0].getY();
        for (Cell cell : cells) {
            if (cell.getY() < y) {
                y = cell.getY();
            }
        }
        return y;
    }

    //return 最右边的cell
    private int getRightCell() {
        Cell[] cells = getStatedCells();
        int y = cells[0].getY();
        for (Cell cell : cells) {
            if (cell.getY() > y) {
                y = cell.getY();
            }
        }
        return y;
    }

    //clone shap
    @Override
    public Object clone() throws CloneNotSupportedException {
        Shap shap = (Shap) super.clone();
        //Shap shap = new Shap(Shap.model1, column);
        shap.cells = new Cell[9];
        for (int i = 0; i < shap.cells.length; i++) {
            shap.cells[i] = (Cell) this.cells[i].clone();
        }
        return shap;
    }

    //下一按键e后，返回stated状态的cell[]，应用于shapcontrol
    public Cell[] getNextStatedCells(char e) throws CloneNotSupportedException, IOException {
        Shap shap = (Shap) this.clone();
        shap.keyTyped(e);
        return shap.getStatedCells();
    }

    //下一次timedown后，返回stated状态的cell[]
    public Cell[] getDownStatedCells() throws CloneNotSupportedException, IOException {
        Shap shap = (Shap) this.clone();
        shap.keyTyped('s');
        return shap.getStatedCells();
    }

    public void setBlockFlag(boolean blockFlag) {
        this.blockFlag = blockFlag;
    }

    public boolean isBlock() {
        return blockFlag;
    }

    //返回当前时刻stated状态的cell[]
    public Cell[] getStatedCells() {
        Cell[] temp = new Cell[4];
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (cells[i].isState()) {
                temp[count] = cells[i];
                count++;
            }
        }
        return temp;
    }
//    public void print() {
//        int count=0;
//        for (int i = 0; i < 9; i++) {
//            count++;
//                if (cells[i].isState()) {
//                    System.out.print("‘");
//                } else {
//                    System.out.print("。");
//                }
//            if (count%3==0) {
//                System.out.print("\n");
//            }
//        }
//        count=0;
//        for (int i = 0; i < 9; i++) {
//            count++;
//            System.out.print(cells[i].getX() + " " + cells[i].getY());
//            System.out.print('\t');
//            if (count%3==0) {
//                System.out.print("\n");
//            }
//        }
//    }
//public static void main(String[] args) {
//    Shap shap = new Shap(Shap.model2);
//    try {
//        shap.print();
//        Cell[] cells=shap.getDownStatedCells();
//        for (Cell cell : cells) {
//            System.out.print(cell.getX()+" "+cell.getY());
//            System.out.println(" ");
//        }
//        shap.print();
//
//
//    } catch (CloneNotSupportedException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//}

}
