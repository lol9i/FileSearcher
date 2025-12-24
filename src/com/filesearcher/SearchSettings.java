package com.filesearcher;

public class SearchSettings {
    private String searchWord;
    private boolean caseSensitive;
    private boolean wholeWord;
    private boolean recursive;

    public SearchSettings(String searchWord, boolean caseSensitive,
                          boolean wholeWord, boolean recursive) {
        this.searchWord = searchWord;
        this.caseSensitive = caseSensitive;
        this.wholeWord = wholeWord;
        this.recursive = recursive;
    }

    public String getSearchWord() { return searchWord; }
    public void setSearchWord(String searchWord) { this.searchWord = searchWord; }

    public boolean isCaseSensitive() { return caseSensitive; }
    public void setCaseSensitive(boolean caseSensitive) { this.caseSensitive = caseSensitive; }

    public boolean isWholeWord() { return wholeWord; }
    public void setWholeWord(boolean wholeWord) { this.wholeWord = wholeWord; }

    public boolean isRecursive() { return recursive; }
    public void setRecursive(boolean recursive) { this.recursive = recursive; }
}