package per.wzx.component;

/**
 * 包含坐标坐标和状态参数
 */
public class Cell implements Cloneable{
    private int x;
    private int y;
    private boolean state;

    /**
     * 初始化一个特定坐标和state状态的cell
     * @param x 方块的横坐标
     * @param y 方块的纵坐标
     * @param state 方块是否被占用
     */
    public Cell(int x, int y,boolean state) {
        this.x=x;
        this.y=y;
        this.state=state;
    }

    /**
     * 初始化一个特定坐标和unstated状态的cell
     * @param x 方块的横坐标
     * @param y 方块的纵坐标
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        state=false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     *cell下移
     */
    public void down() {
        x++;
    }

    /**
     * cell左移
     */
    public void left() {
        y--;
    }

    /**
     * cell右移
     */
    public void right() {
        y++;
    }
    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * 判断两个cell坐标是否相同
     * @param cell 方块对象
     * @return boolen
     */
    public boolean equal(Cell cell) {
        if (this.getX() == cell.getX() && this.getY() == cell.getY()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        Cell cell= (Cell) super.clone();
        //Cell cell=new Cell(this.getX(),this.getY(),this.isState());
        return cell;
    }
}
