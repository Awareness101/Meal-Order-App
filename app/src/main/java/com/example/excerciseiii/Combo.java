package com.example.excerciseiii;

import java.util.LinkedList;

public class Combo {

    private int id;
    private LinkedList<Item> items;
    private double price;

    public Combo(){

    }

    public Combo(int id, LinkedList<Item> items, double price){
        this.id = id;
        this.items = items;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
