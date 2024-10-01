package br.xadrez.model;

import org.fusesource.jansi.AnsiConsole;

public class TerminalColor {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String YELLOW = "\u001B[43m";
    public static final String BLUE = "\u001B[44m";

    public static void printRed(String string) {
        AnsiConsole.systemInstall();
        System.out.print(RED + string + RESET);
        AnsiConsole.systemUninstall();
    }

    public static void printBlue(String string) {
        AnsiConsole.systemInstall();
        System.out.print(BLUE + string + RESET);
        AnsiConsole.systemUninstall();
    }

}
