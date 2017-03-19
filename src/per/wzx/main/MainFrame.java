package per.wzx.main;

import per.wzx.sendMail.MailDialog;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    private JPanel contentPane;
    protected JTextArea textArea;
    protected JButton startButton;
    protected JButton stopButton;
    protected JButton suspendButton;

    /**
     * Launch the application.
     */

    /**
     * Create the frame.
     */
    public MainFrame() {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            //确认窗体，除了点击是，其余都是退出程序
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "想给我们提点意见吗");
                if (option == JOptionPane.YES_OPTION) {
                    dispose();
                    MailDialog.createMailDialog();
                } else if (option == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }

//            @Override
//            public void windowClosed(WindowEvent e) {
//                //super.windowClosed(e);
//                setVisible(true);
//
//            }
        });
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 450, 550);
        setLocationRelativeTo(null);
        setTitle("俄罗斯方块简易版");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        textArea = new JTextArea();
        textArea.setSize(300,400);
        textArea.setEditable(false);
        startButton = new JButton("开始");

        stopButton = new JButton("结束");

        suspendButton = new JButton("暂停/继续");
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(textArea, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(startButton)
                                .addGap(74)
                                .addComponent(stopButton)
                                .addPreferredGap(ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                .addComponent(suspendButton))
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(textArea, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(startButton)
                                        .addComponent(stopButton)
                                        .addComponent(suspendButton)))
        );
        contentPane.setLayout(gl_contentPane);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
