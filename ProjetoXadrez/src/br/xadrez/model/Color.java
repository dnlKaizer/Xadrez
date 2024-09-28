package br.xadrez.model;

public class Color {
    private final String name; // Nome da cor (ex: "Branco", "Preto")
    private final boolean isWhite; 

    // Constantes para cores brancas e pretas
    public static final Color WHITE = new Color("Branco", true);
    public static final Color BLACK = new Color("Preto", false);

    // Construtor privado para impedir a criação de novas instâncias
    private Color(String name, boolean isWhite) {
        this.name = name;
        this.isWhite = isWhite;
    }

    public String getName() {
        return name; // Retorna o nome da cor
    }
    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public String toString() {
        return name; // Representação da cor como string
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Color color = (Color) obj;
        return color.isWhite() == this.isWhite();
    }    

}
