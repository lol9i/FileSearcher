package com.filesearcher;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}