package per.wzx.controler;

import per.wzx.main.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * shap controlor
 */
public class ShapControl extends KeyAdapter {
    private static ShapControl SHAP_CONTROL = null;
    private GamePanel gamePanel;

    public static ShapControl getShapControl() {
        if (SHAP_CONTROL == null) {
            SHAP_CONTROL = new ShapControl();
        }
        return SHAP_CONTROL;
    }

    /**
     * 关联一个棋盘
     */
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if (gamePanel.getShap() != null) {
            try {
                if (!gamePanel.isSuspend()&&!gamePanel.getBlock().isBlocked(gamePanel.getShap().getNextStatedCells(e.getKeyChar()))) {
                    try {
                        gamePanel.getShap().keyTyped(e.getKeyChar());
                        gamePanel.print();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else if (gamePanel.getOver()){
                    System.err.println("游戏已经结束");
                } else if (gamePanel.isSuspend()) {
                    System.err.println("游戏已经暂停");
                }
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
