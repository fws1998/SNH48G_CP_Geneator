package com.example.snh48g_cp_geneator.classes;

public class CPName {
    private String name;
    private String description;
    private String from;

    public CPName(String name, String description, String from) {
        this.name = name;
        this.description = description;
        this.from = from;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getFrom() {
        return from;
    }
}
