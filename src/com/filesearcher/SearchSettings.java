package com.filesearcher;

public class SearchSettings {
    private String searchWord;
    private boolean caseSensitive;
    private boolean wholeWord;
    private boolean ignoreSpaces;

    public SearchSettings(String searchWord, boolean caseSensitive,
                          boolean wholeWord, boolean ignoreSpaces) {
        this.searchWord = searchWord;
        this.caseSensitive = caseSensitive;
        this.wholeWord = wholeWord;
        this.ignoreSpaces = ignoreSpaces;
    }

    public String getSearchWord() { return searchWord; }
    public void setSearchWord(String searchWord) { this.searchWord = searchWord; }

    public boolean isCaseSensitive() { return caseSensitive; }
    public void setCaseSensitive(boolean caseSensitive) { this.caseSensitive = caseSensitive; }

    public boolean isWholeWord() { return wholeWord; }
    public void setWholeWord(boolean wholeWord) { this.wholeWord = wholeWord; }

    public boolean isIgnoreSpaces() { return ignoreSpaces; }
    public void setIgnoreSpaces(boolean ignoreSpaces) { this.ignoreSpaces = ignoreSpaces; }
}