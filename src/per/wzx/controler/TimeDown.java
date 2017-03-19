package per.wzx.controler;

import per.wzx.main.GamePanel;

import java.io.IOException;
import java.util.TimerTask;

/**
 * timed shap down
 */
public class TimeDown extends TimerTask {
    private GamePanel gamePanel;

    public TimeDown(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    @Override
    public void run() {
        try {
            if (!gamePanel.getOver()&&!gamePanel.isSuspend()&&!gamePanel.getBlock().isBlocked(gamePanel.getShap().getDownStatedCells())) {
                gamePanel.getShap().shapDown();
                gamePanel.print();
            } else if (gamePanel.getOver()) {
                cancel();
            } else if (gamePanel.isSuspend()) {
                gamePanel.setSuspendTime();
                System.out.println("已暂停"+gamePanel.getSuspendTime()+"s");
            } else {
                gamePanel.addBlock();
                gamePanel.getBlock().dropLine();
                gamePanel.shapInit();
                cancel();
            }
        } catch (CloneNotSupportedException f) {
            f.printStackTrace();
        } catch (IOException f) {
            f.printStackTrace();
        }
    }
}
