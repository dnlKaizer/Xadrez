package br.xadrez.main;

import org.fusesource.jansi.AnsiConsole;

import br.xadrez.controller.BoardController;

public class App {
    public static void main(String[] args) throws Exception {
        AnsiConsole.systemInstall();

        BoardController boardController = new BoardController();
        boardController.game();

        AnsiConsole.systemUninstall();
    }
}