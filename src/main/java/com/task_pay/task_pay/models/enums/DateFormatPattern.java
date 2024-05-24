package com.task_pay.task_pay.models.enums;

import lombok.Getter;

@Getter
public enum DateFormatPattern {
    ISO("yyyy-MM-dd"),
    US("MM/dd/yyyy"),
    EU("dd-MM-yyyy");
    private final String pattern;
    DateFormatPattern(String pattern) {
        this.pattern=pattern;
    }

}
