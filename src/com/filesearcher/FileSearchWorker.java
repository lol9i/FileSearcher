package com.filesearcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileSearchWorker {

    public List<String> searchFiles(String directoryPath, SearchSettings settings) {
        List<String> results = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            return results;
        }

        searchInDirectory(directory, settings, results);
        return results;
    }

    private void searchInDirectory(File directory, SearchSettings settings,
                                   List<String> results) {
        File[] files = directory.listFiles();
        if (files == null) return;

        String searchPattern = prepareSearchPattern(settings);

        for (File file : files) {
            if (file.isDirectory()) {
                searchInDirectory(file, settings, results);
            } else {
                if (matches(file.getName(), searchPattern, settings)) {
                    results.add(file.getAbsolutePath());
                }
                else if (isTextFile(file) && containsInFile(file, searchPattern, settings)) {
                    results.add(file.getAbsolutePath() + " (содержит в тексте)");
                }
            }
        }
    }

    private String prepareSearchPattern(SearchSettings settings) {
        String word = settings.getSearchWord();

        if (settings.isIgnoreSpaces()) {
            word = word.replaceAll("\\s+", "");
        }

        if (!settings.isCaseSensitive()) {
            word = word.toLowerCase();
        }

        if (settings.isWholeWord()) {
            word = Pattern.quote(word);
            return "\\b" + word + "\\b";
        }

        return Pattern.quote(word);
    }

    private boolean matches(String text, String pattern, SearchSettings settings) {
        String processedText = text;

        if (settings.isIgnoreSpaces()) {
            processedText = processedText.replaceAll("\\s+", "");
        }

        if (!settings.isCaseSensitive()) {
            processedText = processedText.toLowerCase();
        }

        Pattern p;
        if (settings.isWholeWord()) {
            p = Pattern.compile(pattern);
        } else {
            p = Pattern.compile(pattern, Pattern.LITERAL);
        }

        return p.matcher(processedText).find();
    }

    private boolean isTextFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".txt") || name.endsWith(".java") ||
                name.endsWith(".xml") || name.endsWith(".json") ||
                name.endsWith(".html") || name.endsWith(".css") ||
                name.endsWith(".js") || name.endsWith(".properties");
    }

    private boolean containsInFile(File file, String pattern, SearchSettings settings) {
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            return matches(content, pattern, settings);
        } catch (IOException e) {
            return false;
        }
    }
}