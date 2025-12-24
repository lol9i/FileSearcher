package com.filesearcher;

public class LanguageManager {
    private static String currentLanguage = "ru";

    public static String getString(String key) {
        if ("en".equals(currentLanguage)) {
            return getEnglishText(key);
        } else {
            return getRussianText(key);
        }
    }

    private static String getRussianText(String key) {
        switch (key) {
            case "title": return "Поиск файлов";
            case "folder": return "Папка:";
            case "word": return "Слово для поиска:";
            case "case_sensitive": return "Учитывать регистр";
            case "whole_word": return "Целое слово";
            case "ignore_spaces": return "Игнорировать пробелы";
            case "language": return "Язык:";
            case "browse": return "Обзор...";
            case "search": return "Начать поиск";
            case "searching": return "Поиск...";
            case "files_found": return "Найдено файлов:";
            case "no_files": return "Файлы не найдены";
            case "error": return "Ошибка";
            case "fill_fields": return "Заполните все поля!";
            case "ready": return "Готов к поиску";
            case "select_folder": return "Выберите папку для поиска";
            case "search_settings": return "Настройки поиска";
            case "search_results": return "Результаты поиска";
            case "not_folder": return "Папка не существует или указан файл!";
            default: return key;
        }
    }

    private static String getEnglishText(String key) {
        switch (key) {
            case "title": return "File Searcher";
            case "folder": return "Folder:";
            case "word": return "Search word:";
            case "case_sensitive": return "Case sensitive";
            case "whole_word": return "Whole word";
            case "ignore_spaces": return "Ignore spaces";
            case "language": return "Language:";
            case "browse": return "Browse...";
            case "search": return "Start Search";
            case "searching": return "Searching...";
            case "files_found": return "Files found:";
            case "no_files": return "No files found";
            case "error": return "Error";
            case "fill_fields": return "Fill all fields!";
            case "ready": return "Ready to search";
            case "select_folder": return "Select folder for search";
            case "search_settings": return "Search Settings";
            case "search_results": return "Search Results";
            case "not_folder": return "Folder doesn't exist or is not a directory!";
            default: return key;
        }
    }

    public static void setLanguage(String lang) {
        currentLanguage = lang;
    }

    public static String getCurrentLanguage() {
        return currentLanguage;
    }
}