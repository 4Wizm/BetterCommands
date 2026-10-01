package com.wizm.bettercommands.util;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    private static final Pattern HEX = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final Method CHAT_COLOR_OF;
    private static final boolean HEX_SUPPORTED;

    static {
        Method m = null;
        boolean supported = false;
        try {
            Class<?> clazz = Class.forName("net.md_5.bungee.api.ChatColor");
            m = clazz.getMethod("of", String.class);
            supported = true;
        } catch (Throwable ignored) {}
        CHAT_COLOR_OF = m;
        HEX_SUPPORTED = supported;
    }

    public static String color(String message) {
        if (message == null || message.isEmpty()) return "";
        if (HEX_SUPPORTED && message.indexOf('&') >= 0) {
            message = applyHex(message);
        }
        return legacy(message);
    }

    private static String applyHex(String message) {
        Matcher matcher = HEX.matcher(message);
        if (!matcher.find()) return message;
        StringBuffer buffer = new StringBuffer(message.length() + 16);
        do {
            String replacement;
            try {
                replacement = CHAT_COLOR_OF.invoke(null, "#" + matcher.group(1)).toString();
            } catch (Throwable t) {
                replacement = "";
            }
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(replacement));
        } while (matcher.find());
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String legacy(String message) {
        char[] chars = message.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(chars[i + 1]) > -1) {
                chars[i] = '\u00A7';
                chars[i + 1] = Character.toLowerCase(chars[i + 1]);
            }
        }
        return new String(chars);
    }
}