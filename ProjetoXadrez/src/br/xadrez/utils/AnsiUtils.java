package br.xadrez.utils;

public class AnsiUtils {
    // Reset
    public static final String RESET = "\u001B[0m";

    // Cores de texto
    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String GREEN_TEXT = "\u001B[32m";
    public static final String YELLOW_TEXT = "\u001B[33m";
    public static final String BLUE_TEXT = "\u001B[34m";
    public static final String PURPLE_TEXT = "\u001B[35m";
    public static final String CYAN_TEXT = "\u001B[36m";
    public static final String WHITE_TEXT = "\u001B[37m";
    
    // Cores de fundo
    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String PURPLE_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    public static final String GRAY_BACKGROUND = "\033[48;5;238m";

    /**
     * Retorna o texto passado no parâmetro
     * com o fundo vermelho.
     * 
     * @param string {@code String} texto
     * @return {@code String} texto com fundo vermelho
      */
    public String printRed(String string) {
        return RED_BACKGROUND + string + RESET;
    }

    /**
     * Retorna o texto passado no parâmetro
     * com o fundo amarelo.
     * 
     * @param string {@code String} texto
     * @return {@code String} texto com fundo amarelo
      */
    public String printYellow(String string) {
        return YELLOW_BACKGROUND + string + RESET;
    }

    /**
     * Retorna o texto passado no parâmetro
     * com o fundo azul.
     * 
     * @param string {@code String} texto
     * @return {@code String} texto com fundo azul
      */
    public String printBlue(String string) {
        return BLUE_BACKGROUND + string + RESET;
    }

    /**
     * Retorna o texto passado no parâmetro
     * com o fundo branco.
     * 
     * @param string {@code String} texto
     * @return {@code String} texto com fundo branco
      */
    public String printWhite(String string) {
        return WHITE_BACKGROUND + string + RESET;
    }

    /**
     * Retorna o texto passado no parâmetro
     * com o fundo preto.
     * 
     * @param string {@code String} texto
     * @return {@code String} texto com fundo preto
      */
    public String printBlack(String string) {
        return BLACK_BACKGROUND + string + RESET;
    }

    /**
     * Retorna o texto passado no parâmetro
     * com o fundo cinza.
     * 
     * @param string {@code String} texto
     * @return {@code String} texto com fundo cinza
      */
    public String printGray(String string) {
        return GRAY_BACKGROUND + string + RESET;
    }

    /**
     * Retorna o texto passado no parâmetro
     * com o fundo verde.
     * 
     * @param string {@code String} texto
     * @return {@code String} texto com fundo verde
      */
    public String printGreen(String string) {
        return GREEN_BACKGROUND + string + RESET;
    }

    /**
     * Move o cursor para cima <b>n</b> linhas.
     * 
     * @param num {@code int} n
      */
    public void moveCursorUp(int num) {
        System.out.print("\u001B[" + num + "A");
    }

    /**
     * Move o cursor para baixo <b>n</b> linhas.
     * 
     * @param num {@code int} n
      */
    public void moveCursorDown(int num) {
        System.out.print("\u001B[" + num + "B");
    }

    /**
     * Move o cursor para a esquerda <b>n</b> colunas.
     * 
     * @param num {@code int} n
      */
    public void moveCursorLeft(int num) {
        System.out.print("\u001B[" + num + "D");
    }

    /**
     * Move o cursor para a direita <b>n</b> colunas.
     * 
     * @param num {@code int} n
      */
    public void moveCursorRight(int num) {
        System.out.print("\u001B[" + num + "C");
    }

    /**
     * Salva a posição atual do cursor.
      */
    public void save() {
        System.out.print("\u001B[s");
    }

    /**
     * Retorna o cursor para a posição salva.
      */
    public void restore() {
        System.out.print("\u001B[u");
    }

    /**
     * Limpa a linha do cursor.
      */
    public void cleanLine() {
        System.out.print("\u001B[2K");
    }
    
    /**
     * Substitui a linha do cursor
     * pelo texto do parâmetro.
     * 
     * @param str {@code String} texto
      */
    public void replaceLine(String str) {
        cleanLine();
        System.out.print(str);
    }

    /**
     * Move o cursor para a primeira coluna
     * da linha do cursor.
      */
    public void start() {
        System.out.print("\u001b[1G");
    }

    /**
     * Coloca o cursor na posição do tabuleiro.
     * <b>Só funciona se o menu estiver correto.</b>
     * 
     * @param row {@code int} linha
     * @param col {@code int} coluna
      */
    public void placeBoard(int row, int col) {
        System.out.print("\u001B[" + (row + 1) + ";" + ((col * 3) + 3) + "H");
    }

}
