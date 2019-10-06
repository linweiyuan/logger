package com.linweiyuan.logger.util;

import com.linweiyuan.logger.model.LogBorder;
import com.linweiyuan.logger.model.LogColor;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExceptionLogger {
    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String get(Throwable t, LogColor color, String filter, Integer limit) {
        StringBuilder sb = new StringBuilder();

        Stream<String> stackTraceStream = Arrays.stream(t.getStackTrace())
                .map(StackTraceElement::toString);

        if (filter != null) {
            stackTraceStream = stackTraceStream.filter(s -> s.contains(filter));
        }

        if (limit != null) {
            stackTraceStream = stackTraceStream.limit(limit);
        }

        List<String> stackTraceElements = stackTraceStream.collect(Collectors.toList());
        stackTraceElements.add(0, fmt.format(new Date()) + " -> " + t.getLocalizedMessage());

        int maxLength = stackTraceElements.stream()
                .max(Comparator.comparingInt(String::length))
                .map(String::length)
                .get();

        sb.append(color.getValue()).append(LogBorder.LEFT_UP.getValue());
        for (int i = 0; i < maxLength + 2; i++) {
            sb.append(LogBorder.HORIZONTAL.getValue());
        }
        sb.append(LogBorder.RIGHT_UP.getValue()).append(LogColor.ANSI_RESET.getValue()).append("\n");

        stackTraceElements.forEach(element -> {
            sb.append(color.getValue()).append(LogBorder.VERTICAL.getValue()).append(" ").append(element);
            for (int i = element.length(); i <= maxLength; i++) {
                sb.append(" ");
            }
            sb.append(LogBorder.VERTICAL.getValue()).append(LogColor.ANSI_RESET.getValue()).append("\n");
        });

        sb.append(color.getValue()).append(LogBorder.LEFT_BOTTOM.getValue());
        for (int i = 0; i < maxLength + 2; i++) {
            sb.append(LogBorder.HORIZONTAL.getValue());
        }
        sb.append(LogBorder.RIGHT_BOTTOM.getValue()).append(LogColor.ANSI_RESET.getValue());
        return sb.toString();
    }

    public static void print(Throwable t) {
        print(t, LogColor.ANSI_RED_BACKGROUND, null, null);
    }

    public static void print(Throwable t, LogColor color) {
        print(t, color, null, null);
    }

    public static void print(Throwable t, String filter) {
        print(t, LogColor.ANSI_RED_BACKGROUND, filter, null);
    }

    public static void print(Throwable t, LogColor color, String filter) {
        print(t, color, filter, null);
    }

    public static void print(Throwable t, Integer limit) {
        print(t, LogColor.ANSI_RED_BACKGROUND, null, limit);
    }

    public static void print(Throwable t, LogColor color, Integer limit) {
        print(t, color, null, limit);
    }

    public static void print(Throwable t, String filter, Integer limit) {
        print(t, LogColor.ANSI_RED_BACKGROUND, filter, limit);
    }

    public static void print(Throwable t, LogColor color, String filter, Integer limit) {
        System.out.println(get(t, color, filter, limit));
    }
}
