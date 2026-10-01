package com.wizm.bettercommands.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class CommandMatcher {

    private final Set<String> exactCommands = new HashSet<>();
    private final List<String> namespaces = new ArrayList<>();
    private final List<Pattern> regexPatterns = new ArrayList<>();

    public CommandMatcher(List<String> rules) {
        for (String rule : rules) {
            if (rule == null || rule.isEmpty()) continue;

            if (rule.startsWith("regex:")) {
                try {
                    regexPatterns.add(Pattern.compile(rule.substring(6), Pattern.CASE_INSENSITIVE));
                } catch (PatternSyntaxException ignored) {}
            } else if (rule.endsWith(":*")) {
                namespaces.add(rule.substring(0, rule.length() - 1).toLowerCase());
            } else {
                exactCommands.add(rule.toLowerCase());
            }
        }
    }

    public boolean matches(String command) {
        if (exactCommands.contains(command)) return true;
        for (int i = 0; i < namespaces.size(); i++) {
            if (command.startsWith(namespaces.get(i))) return true;
        }
        for (int i = 0; i < regexPatterns.size(); i++) {
            if (regexPatterns.get(i).matcher(command).matches()) return true;
        }
        return false;
    }
}
