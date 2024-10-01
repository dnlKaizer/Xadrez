package br.xadrez.utils;

public class AnsiUtils {
    // Reset
    public static final String RESET = "\u001B[0m";

    // Text colors
    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String GREEN_TEXT = "\u001B[32m";
    public static final String YELLOW_TEXT = "\u001B[33m";
    public static final String BLUE_TEXT = "\u001B[34m";
    public static final String PURPLE_TEXT = "\u001B[35m";
    public static final String CYAN_TEXT = "\u001B[36m";
    public static final String WHITE_TEXT = "\u001B[37m";
    
    // Background colors
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    public static final String GRAY_BACKGROUND = "\033[48;5;238m";

    public void printRed(String string) {
        System.out.print(RED_BACKGROUND + "\u001B[4m" + string + RESET);
    }

    public void printBlue(String string) {
        System.out.print(BLUE_BACKGROUND + "\u001B[4m" + string + RESET);
    }

    public void printWhite(String string) {
        System.out.print(WHITE_BACKGROUND + "\u001B[4m" + string + RESET);
    }

    public void printBlack(String string) {
        System.out.print(BLACK_BACKGROUND + "\u001B[4m" + string + RESET);
    }

    public void printGray(String string) {
        System.out.print(GRAY_BACKGROUND + "\u001B[4m" + string + RESET);
    }

    public void printGreen(String string) {
        System.out.print(GREEN_BACKGROUND + "\u001B[4m" + string + RESET);
    }

    public void moveCursorUp(int num) {
        System.out.print("\u001B[" + num + "A");
    }

    public void moveCursorDown(int num) {
        System.out.print("\u001B[" + num + "B");
    }

    public void moveCursorLeft(int num) {
        System.out.print("\u001B[" + num + "D");
    }

    public void moveCursorRight(int num) {
        System.out.print("\u001B[" + num + "C");
    }

    public void cleanLine() {
        System.out.println("\u001B[2K");
    }

}
