package com.ditto.constant;

import org.apache.tomcat.util.http.parser.Priority;

public enum BoardCategory {
    //게시판 분류
    EVENT("이벤트"), NEWS("소식"), INQUIRY("문의"), ANSWER("답변");


    private final String label;

    BoardCategory(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
