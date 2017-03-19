package per.wzx.component;

import java.util.*;

/**
 * blocked cells
 */
public class Block {
    private ArrayList<Cell> blockCell; //存储blocked cells
    private int score;   //储存分数
    private int column;
    private int row;

    public int getScore() {
        return score;
    }

    public Block(int column,int row) {
        blockCell = new ArrayList<>();
        this.column = column;
        this.row = row;
        score=0;
    }

    public void addCell(Cell cell) {
        blockCell.add(cell);
    }

    public void addCells(Cell[] cells) {
        for (Cell cell : cells) {
            blockCell.add(cell);
        }
    }

    public boolean isNull() {
        if (blockCell.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //返回blockcell的数组
    public Cell[] getCells() {
        return (Cell[]) blockCell.toArray(new Cell[blockCell.size()]);
    }

    //return lastest four cells
    public Cell[] getLastCells() {
        Cell[] cells = new Cell[4];
        for (int i = 0; i < 4; i++) {
            cells[i] = blockCell.get(blockCell.size() - 1 - i);
        }
        return cells;
    }

    //判断given cells是否与blockcell重合，此处应give 将来的cells
    public boolean isBlocked(Cell[] cells) {
        for (Cell block : blockCell) {
            for (Cell cell : cells) {
                if (cell.equal(block)) {
                    return true;
                }
            }
        }
        return false;
    }

    //消除堆满的行
    public void dropLine() {
        Map<Integer, Integer> rowState = new HashMap<>(); //储存每行有多少个stated方块
        for (Cell cell : blockCell) {
            if (!rowState.containsKey(cell.getX())) {
                rowState.put(cell.getX(), 1);
            } else {
                rowState.put(cell.getX(), rowState.get(cell.getX()) + 1);
            }
        }
        Set<Map.Entry<Integer, Integer>> entries = rowState.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries) {
            if (entry.getValue() == column&&entry.getKey()!=row+1) {
                score++;
                downLines(entry.getKey());
            }
        }

    }

    //指定行删除并指定行以上下移一格
    private void downLines(int row) {
        Iterator<Cell> cellIter = blockCell.iterator();
        while (cellIter.hasNext()) {
            Cell cell = cellIter.next();
            if (cell.getX() == row) {
                cellIter.remove();
            } else if (cell.getX() < row) {
                cell.down();
            }
        }
    }

    
}
