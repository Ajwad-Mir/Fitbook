package com.example.fitbook.shoes;

import java.util.List;

public class ShoesList {
    public Boolean shoelist_isempty;
    public List<Shoes> listofshoes;

    public ShoesList() {
    }

    public ShoesList(Boolean shoelist_isempty, List<Shoes> listofshoes) {
        this.shoelist_isempty = shoelist_isempty;
        this.listofshoes = listofshoes;
    }

    public Boolean getshoelist_isempty() {
        return shoelist_isempty;
    }

    public List<Shoes> getlistofshoes() {
        return listofshoes;
    }

    public void setshoelist_isempty(Boolean shoelist_isempty) {
        this.shoelist_isempty = shoelist_isempty;
    }
}
