package per.wzx.sendMail;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * for users to send their proposal
 */
public class MailDialog {
    private JPanel Panel;
    private JButton sendButton;
    private JButton quitbutton;
    private JLabel topLabel;
    private JTextArea textArea;
    private JScrollPane scrollPanel;
    private JLabel label1;
    private JTextField addressTextField;
    private JLabel label2;
    private JPasswordField passwordField;
    private String sendText;
    private String passWord;
    private String sender;

    private MailDialog() {
        quitbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendText = textArea.getText();
                passWord = String.valueOf(passwordField.getPassword());
                sender = addressTextField.getText();
//                System.out.println(sender);
//                System.out.println(passWord);
                try {
                    SendMail.send(sendText,sender,passWord);
                } catch (IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"发送失败，请检查网络");
                } catch (MessagingException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"发送失败，请检查网络,密码");

                }
            }
        });
    }

    public static void createMailDialog() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("发送邮件");
                frame.setContentPane(new MailDialog().Panel);
                frame.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        System.exit(0);
                    }
                });
                frame.setSize(500,400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }
}
