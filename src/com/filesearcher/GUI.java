package com.filesearcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class GUI extends JFrame {
    private JTextField directoryField;
    private JTextField searchField;
    private JCheckBox caseSensitiveCheck;
    private JCheckBox wholeWordCheck;
    private JCheckBox ignoreSpacesCheck;
    private JTextArea resultArea;
    private JComboBox<String> languageCombo;
    private JButton searchButton;
    private JButton browseButton;
    private JLabel statusLabel;

    public GUI() {
        System.setProperty("file.encoding", "UTF-8");

        initComponents();
        applyStyles();

        updateUITexts();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel folderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel folderLabel = new JLabel();
        folderLabel.setName("folder_label");
        folderPanel.add(folderLabel);

        directoryField = new JTextField(30);
        directoryField.setFont(new Font("Arial", Font.PLAIN, 14));
        folderPanel.add(directoryField);

        browseButton = new JButton();
        browseButton.setName("browse_button");
        browseButton.addActionListener(e -> browseDirectory());
        folderPanel.add(browseButton);
        settingsPanel.add(folderPanel);

        JPanel wordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JLabel wordLabel = new JLabel();
        wordLabel.setName("word_label");
        wordPanel.add(wordLabel);

        searchField = new JTextField(30);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        wordPanel.add(searchField);
        settingsPanel.add(wordPanel);

        JPanel checkboxesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        caseSensitiveCheck = new JCheckBox();
        caseSensitiveCheck.setName("case_check");
        checkboxesPanel.add(caseSensitiveCheck);

        wholeWordCheck = new JCheckBox();
        wholeWordCheck.setName("whole_check");
        checkboxesPanel.add(wholeWordCheck);

        ignoreSpacesCheck = new JCheckBox();
        ignoreSpacesCheck.setName("ignore_check");
        checkboxesPanel.add(ignoreSpacesCheck);
        settingsPanel.add(checkboxesPanel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel langLabel = new JLabel();
        langLabel.setName("lang_label");
        bottomPanel.add(langLabel);

        languageCombo = new JComboBox<>(new String[]{"Русский", "English"});
        languageCombo.addActionListener(e -> changeLanguage());
        bottomPanel.add(languageCombo);

        bottomPanel.add(Box.createHorizontalStrut(30));

        searchButton = new JButton();
        searchButton.setName("search_button");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.setBorder(BorderFactory.createLineBorder(new Color(50, 100, 150), 2));
        searchButton.addActionListener(e -> performSearch());
        bottomPanel.add(searchButton);

        settingsPanel.add(bottomPanel);

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        resultArea.setMargin(new Insets(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel();
        statusLabel.setName("status_label");
        statusPanel.add(statusLabel);

        mainPanel.add(settingsPanel, BorderLayout.NORTH);
        mainPanel.add(resultsPanel, BorderLayout.CENTER);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void applyStyles() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);

            UIManager.put("Button.foreground", Color.BLACK);
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));

            searchButton.setForeground(Color.BLACK);

        } catch (Exception e) {

        }
    }

    private void updateUITexts() {
        setTitle(LanguageManager.getString("title"));

        updateComponentTexts(this.getContentPane());

        searchButton.setForeground(Color.BLACK);
    }

    private void updateComponentTexts(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                String name = label.getName();
                if (name != null) {
                    switch (name) {
                        case "folder_label": label.setText(LanguageManager.getString("folder")); break;
                        case "word_label": label.setText(LanguageManager.getString("word")); break;
                        case "lang_label": label.setText(LanguageManager.getString("language")); break;
                        case "status_label":
                            if (statusLabel.getText().isEmpty() ||
                                    statusLabel.getText().equals("Готов к поиску") ||
                                    statusLabel.getText().equals("Ready to search")) {
                                label.setText(LanguageManager.getString("ready"));
                            }
                            break;
                    }
                }
            } else if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                String name = button.getName();
                if (name != null) {
                    switch (name) {
                        case "browse_button":
                            button.setText(LanguageManager.getString("browse"));
                            button.setForeground(Color.BLACK);
                            break;
                        case "search_button":
                            button.setText(LanguageManager.getString("search"));
                            button.setForeground(Color.BLACK);
                            break;
                    }
                }
            } else if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                String name = checkBox.getName();
                if (name != null) {
                    switch (name) {
                        case "case_check": checkBox.setText(LanguageManager.getString("case_sensitive")); break;
                        case "whole_check": checkBox.setText(LanguageManager.getString("whole_word")); break;
                        case "ignore_check": checkBox.setText(LanguageManager.getString("ignore_spaces")); break;
                    }
                }
            } else if (comp instanceof Container) {
                updateComponentTexts((Container) comp);
            }
        }
    }

    private void browseDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle(LanguageManager.getString("select_folder"));

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            directoryField.setText(chooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void changeLanguage() {
        String selected = (String) languageCombo.getSelectedItem();
        if ("English".equals(selected)) {
            LanguageManager.setLanguage("en");
        } else {
            LanguageManager.setLanguage("ru");
        }
        updateUITexts();
    }

    private void performSearch() {
        String directory = directoryField.getText();
        String searchWord = searchField.getText();

        if (directory.isEmpty() || searchWord.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    LanguageManager.getString("fill_fields"),
                    LanguageManager.getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        File dir = new File(directory);
        if (!dir.exists() || !dir.isDirectory()) {
            JOptionPane.showMessageDialog(this,
                    LanguageManager.getString("not_folder"),
                    LanguageManager.getString("error"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        SearchSettings settings = new SearchSettings(
                searchWord,
                caseSensitiveCheck.isSelected(),
                wholeWordCheck.isSelected(),
                ignoreSpacesCheck.isSelected()
        );

        statusLabel.setText(LanguageManager.getString("searching") + "...");
        searchButton.setEnabled(false);
        searchButton.setBackground(new Color(150, 180, 210));
        resultArea.setText("");

        SwingWorker<List<String>, String> worker = new SwingWorker<>() {
            @Override
            protected List<String> doInBackground() {
                FileSearchWorker searcher = new FileSearchWorker();
                return searcher.searchFiles(directory, settings);
            }

            @Override
            protected void done() {
                try {
                    List<String> results = get();

                    if (results.isEmpty()) {
                        resultArea.setText(LanguageManager.getString("no_files"));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        for (String result : results) {
                            sb.append("• ").append(result).append("\n\n");
                        }
                        sb.append("\n════════════════════════════════════════\n");
                        sb.append(LanguageManager.getString("files_found")).append(": ").append(results.size());
                        resultArea.setText(sb.toString());
                        resultArea.setCaretPosition(0);
                    }

                    statusLabel.setText(LanguageManager.getString("ready"));

                } catch (Exception e) {
                    resultArea.setText(LanguageManager.getString("error") + ": " + e.getMessage());
                    statusLabel.setText(LanguageManager.getString("error"));
                } finally {
                    searchButton.setEnabled(true);
                    searchButton.setBackground(new Color(70, 130, 180));
                    searchButton.setForeground(Color.BLACK);
                }
            }
        };

        worker.execute();
    }
}