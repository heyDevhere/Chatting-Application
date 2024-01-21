package chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.EmptyBorder;

public class Server implements ActionListener {
    JTextField text;
    static JPanel a1;
    static JPanel textPanel; // New panel for displaying text
    static JFrame f = new JFrame();
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;

    Server() {
        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 460, 58);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 30, 30);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(45, 5, 50, 50);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 30, 30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 30, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel threedots = new JLabel(i15);
        threedots.setBounds(410, 20, 15, 30);
        p1.add(threedots);

        JLabel name = new JLabel("Gaitonde");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(110, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p1.add(status);

        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBounds(0, 55, 310, 485);
        f.add(textPanel);
        

        a1 = new JPanel();
        a1.setBounds(310, 55, 140, 485);
        a1.setLayout(new FlowLayout(FlowLayout.RIGHT));

        ImageIcon i18 = new ImageIcon(ClassLoader.getSystemResource("icons/gaii.PNG"));
        Image i19 = i18.getImage().getScaledInstance(150, 485, Image.SCALE_DEFAULT);
        ImageIcon i20 = new ImageIcon(i19);
        JLabel sacred = new JLabel(i20);
        sacred.setBounds(0, 0, 150, 485);
        sacred.setOpaque(true);
        sacred.setBackground(Color.GRAY);
        a1.add(sacred);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5, 550, 310, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                    text.setText("");
                }
            }
        });
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320, 548, 123, 40);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 18));
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        f.add(send);

        f.setSize(450, 600);
        f.setUndecorated(true);
//        f.setLocation(745, 0);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new Server();
        ServerSocket skt = new ServerSocket(6001);
        int initialSpacing = 10; // Initial spacing
        int currentSpacing = initialSpacing; 
        while(true) {
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                while(true) {
                String msg = din.readUTF();
                JPanel panel=formatMessage("Bunty",msg);
                JPanel right=new JPanel(new BorderLayout());
                right.add(panel,BorderLayout.LINE_END);
                vertical.add(right);
                textPanel.add(panel);
                textPanel.add(Box.createVerticalStrut(10));

    // Update the UI
    f.repaint();
    f.invalidate();
    f.validate();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String out = text.getText();
        JPanel messagePanel = formatMessage("Gaitonde", out);
        textPanel.add(messagePanel);
        textPanel.add(Box.createVerticalStrut(18));
//        Data bhejne ke liye
        try {
            dout.writeUTF(out);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        text.setText("");
        f.repaint();
        f.invalidate();
        f.validate();
    }

     public static JPanel formatMessage(String sender, String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel senderLabel = new JLabel(sender);        
        senderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        senderLabel.setForeground(Color.WHITE);
        senderLabel.setBackground(new Color(0, 0, 0));
        senderLabel.setOpaque(true);
        senderLabel.setBorder(new EmptyBorder(5, 5, 5, 10));

        panel.add(senderLabel);

        JLabel messageLabel = new JLabel("<html><p style=\"width: 150px\">" + message + "</p></html>");
        messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        messageLabel.setBackground(new Color(144, 238, 144));
        messageLabel.setOpaque(true);
        messageLabel.setBorder(new EmptyBorder(15, 15, 15, 20));

        panel.add(messageLabel);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        return panel;
    }
}
