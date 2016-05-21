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
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interest interest = (Interest) o;

        if (category != null ? !category.equals(interest.category) : interest.category != null)
            return false;
        return !(value != null ? !value.equals(interest.value) : interest.value != null);

    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
