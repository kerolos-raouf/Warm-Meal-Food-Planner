package com.example.warmmeal.model.pojo;

import java.util.ArrayList;

public class Ingredient {

    private String idIngredient;
    private String strIngredient;
    private String strDescription;


    public Ingredient() {
        this.idIngredient = "";
        this.strIngredient = "";
        this.strDescription = "";
    }


    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }
}
