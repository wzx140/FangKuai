package per.wzx.main;

import javax.swing.*;

/**
 * 最多支持20行
 */
public class MainStart {
    public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GamePanel(20,20);
                }
            });
    }
}
