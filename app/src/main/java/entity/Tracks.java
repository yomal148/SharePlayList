package entity;

import com.google.gson.annotations.SerializedName;

import entity.Item;

public class Tracks {
    public Tracks(Item[] items) {
        this.items = items;
    }
    @SerializedName("items")
    public Item[] items;


}