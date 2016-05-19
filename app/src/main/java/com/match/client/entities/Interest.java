package com.match.client.entities;

import java.io.Serializable;

/**
 * Created by Juan Manuel Romera on 4/5/2016.
 */
public class Interest implements Serializable {

    private final String category; // category of interest

    private final String value; // value of interest

    public Interest(String category, String value) {
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "category='" + category + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
