package com.example.ultimateiptvplayer.Fragments.Categorie;

public enum LANGAGES {
    FR("FR"),
    OTHER("OTHER");

    private final String langage;

    LANGAGES(String langage) {
        this.langage = langage;
    }

    public String getLangage() {
        return langage;
    }
}
