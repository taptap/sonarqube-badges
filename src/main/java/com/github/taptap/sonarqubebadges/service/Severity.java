package com.github.taptap.sonarqubebadges.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Severity {
    public static final String INFO = "INFO";
    public static final String MINOR = "MINOR";
    public static final String MAJOR = "MAJOR";
    public static final String CRITICAL = "CRITICAL";
    public static final String BLOCKER = "BLOCKER";
    public static final List<String> ALL = Collections.unmodifiableList(Arrays.asList("INFO", "MINOR", "MAJOR", "CRITICAL", "BLOCKER"));

    private Severity() {
    }

    public static String defaultSeverity() {
        return "MAJOR";
    }
}