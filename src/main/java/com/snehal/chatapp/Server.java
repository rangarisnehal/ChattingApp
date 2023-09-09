package com.snehal.chatapp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;
import java.text.*;
import java.net.*;

public class Server implements ActionListener {

    JTextField text;
    JPanel chatArea;

    static Box vertical = Box.createVerticalBox();

    static JFrame  f = new JFrame();

    static DataOutputStream dout;

    public Server(){

        f.setLayout(null);

        JPanel j1 = new JPanel();
        j1.setBackground(new Color(7,94,84));
        j1.setBounds(0,0, 450,70);
        j1.setLayout(null);
        f.add(j1);


//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("C://Users/user/Downloads/icons-20230909T082007Z-001/icons/3.png"));

        JLabel name = new JLabel("Snehal");
        name.setBounds(80,15,100,18);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        j1.add(name);

        JLabel status = new JLabel("Active Now");
        status.setBounds(80,35,100,18);
        status.setForeground(Color.white);
        status.setFont(new Font("SAN_SERIF",Font.BOLD,10));
        j1.add(status);


        chatArea = new JPanel();
        chatArea.setBounds(5, 75, 440, 570);
        f.add(chatArea);

        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7, 94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        f.add(send);

        f.setSize(450,700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        // Always use setVisible to last
        f.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String out = text.getText();
            JPanel p2 = formatLable(out);

            chatArea.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            chatArea.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    public static JPanel formatLable(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</html>");
        output.setFont(new Font("Tohoma", Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15,15,15,15));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String[] args) {
        new Server();
        try {
            ServerSocket skt = new ServerSocket(6001);
            while (true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true){
                    String msg = din.readUTF();
                    JPanel panel = formatLable(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){

        }

    }
}
