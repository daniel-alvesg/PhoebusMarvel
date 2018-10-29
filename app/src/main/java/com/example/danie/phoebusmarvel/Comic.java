package com.example.danie.phoebusmarvel;

import java.io.Serializable;

public class Comic implements Serializable {

    private String raro;
    private String title;
    private int quantidade;
    private String description;
    private String preDescription;
    private double price;
    private String thumbnail;

    public String getRaro() {
        return raro;
    }

    public void setRaro(String raro) {
        this.raro = raro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getPreDescription() {
        return preDescription;
    }

    public void setPreDescription(String preDescription) {
        this.preDescription = preDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
