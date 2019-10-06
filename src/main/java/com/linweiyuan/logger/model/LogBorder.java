package com.linweiyuan.logger.model;

public enum LogBorder {
    LEFT_UP("┌"),
    RIGHT_UP("┐"),
    LEFT_BOTTOM("└"),
    RIGHT_BOTTOM("┘"),
    HORIZONTAL("─"),
    VERTICAL("│");

    private String value;

    LogBorder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
