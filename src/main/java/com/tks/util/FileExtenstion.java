package com.tks.util;

public enum FileExtenstion {

    DOCX(".docx"),
    HTML(".html"),
    ODT(".odt"),
    PDF(".pdf"),
    XLS(".xls"),
    ODS(".ods"),
    PPT(".ppt"),
    TXT(".txt");

    public String extenstion;

    FileExtenstion(String value) {
        this.extenstion = value;
    }
}
