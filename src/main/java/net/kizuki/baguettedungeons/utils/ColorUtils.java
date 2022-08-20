package net.kizuki.baguettedungeons.utils;

public class ColorUtils {

    // Some basic utils for translating & signs into Minecraft compatible chat components.
    public static String colorize(String string) {
        return string.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
    }
    public static String stripColor(String string) {
        return string.replaceAll("\u00A7([0-9a-fk-or])", "");
    }

}
