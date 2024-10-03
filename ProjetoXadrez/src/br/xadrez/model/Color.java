package br.xadrez.model;

public class Color {
    // Nome da cor (ex: "Branco", "Preto")
    private final String name;
    // Armazena se a cor é branca (melhor para comparar)
    private final boolean isWhite; 

    // Constantes para cores brancas e pretas
    public static final Color WHITE = new Color("Brancas", true);
    public static final Color BLACK = new Color("Pretas", false);

    private Color(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    public String getName() {
        return name;
    }
    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Color color = (Color) obj;
        return color.isWhite() == this.isWhite();
    }    

}
