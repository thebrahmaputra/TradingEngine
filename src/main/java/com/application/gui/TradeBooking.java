package com.application.gui;

import javax.swing.*;

public class TradeBooking {
    private JPanel mainView;
    private JTextArea mainHeader;
    private JButton submitButton;

    public static void main(String args[]){
        JFrame fr = new JFrame("TradeBooking");
        fr.setContentPane(new TradeBooking().mainView);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.pack();
        fr.setVisible(true);
    }
}
