package com.myorg.codingexcercise;

/**
 *
 * Created by kamoorr on 7/14/17.
 */
public class Item {

    String itemId;
    int cubicFt;

    public Item(String itemId, int cubicFt) {
        this.itemId = itemId;
        this.cubicFt = cubicFt;
    }

    public String getItemId() {
        return itemId;
    }

}