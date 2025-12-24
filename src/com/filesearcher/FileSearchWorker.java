package com.filesearcher;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileSearchWorker {

    public List<String> searchFiles(String directoryPath, SearchSettings settings) {
        List<String> results = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            return results;
        }

        searchInDirectory(directory, settings, results, settings.isRecursive());
        return results;
    }

    private void searchInDirectory(File directory, SearchSettings settings,
                                   List<String> results, boolean recursive) {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                if (recursive) {
                    searchInDirectory(file, settings, results, true);
                }
            } else {
                if (matches(file.getName(), settings.getSearchWord(), settings)) {
                    results.add(file.getAbsolutePath());
                    continue;
                }

                if (isTextFile(file) && containsInFile(file, settings.getSearchWord(), settings)) {
                    results.add(file.getAbsolutePath() + " (" + getContainsTextLabel() + ")");
                }
            }
        }
    }

    private String getContainsTextLabel() {
        if ("en".equals(LanguageManager.getCurrentLanguage())) {
            return "contains in text";
        } else {
            return "содержит в тексте";
        }
    }

    private boolean matches(String text, String searchWord, SearchSettings settings) {
        String processedText = text;
        String processedSearchWord = searchWord;

        if (!settings.isCaseSensitive()) {
            processedText = processedText.toLowerCase();
            processedSearchWord = processedSearchWord.toLowerCase();
        }

        if (settings.isWholeWord()) {
            String wordBoundary = "\\b";
            return processedText.matches(".*" + wordBoundary + processedSearchWord + wordBoundary + ".*");
        } else {
            return processedText.contains(processedSearchWord);
        }
    }

    private boolean isTextFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".txt") || name.endsWith(".java") ||
                name.endsWith(".xml") || name.endsWith(".json") ||
                name.endsWith(".html") || name.endsWith(".css") ||
                name.endsWith(".js") || name.endsWith(".properties") ||
                name.endsWith(".md") || name.endsWith(".cfg") ||
                name.endsWith(".ini") || name.endsWith(".csv") ||
                name.endsWith(".log") || name.endsWith(".yml") ||
                name.endsWith(".yaml");
    }

    private boolean containsInFile(File file, String searchWord, SearchSettings settings) {
        try {
            if (file.length() > 10 * 1024 * 1024) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (matches(line, searchWord, settings)) {
                            return true;
                        }
                    }
                }
                return false;
            } else {
                String content = new String(Files.readAllBytes(file.toPath()));
                return matches(content, searchWord, settings);
            }
        } catch (Exception e) {
            return false;
        }
    }
}