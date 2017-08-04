package com.myorg.codingexcercise;

import java.util.ArrayList;
import java.util.HashMap;

/**This class represents shelves, which can store any number of items up to the amount of space
 * 
 * @author Fuhe
 *
 */
public class Shelf {
	private int space;
	private HashMap<String, Item> itemsStored;
	private int spaceStored;
	
	public Shelf(int s) {
		this.space = s;
		this.itemsStored = new HashMap<String, Item>();
		this.spaceStored = 0;
	}
	
	public boolean add(Item i) {
		boolean result = false;
		int spaceRemaining = space - spaceStored;
		if (spaceRemaining >= i.getCubicFt()) {
			itemsStored.put(i.getItemId(), i);
			result = true;
			spaceStored += i.getCubicFt();
		}
		return result;
	}
	
	public Item removeItem(String id) {
		Item result = null;
		if (itemsStored.containsKey(id)) {
			result = itemsStored.remove(id);
			spaceStored -= result.getCubicFt();
		}
		return result;
	}
	
	public ArrayList<Item> getItemList() {
		return new ArrayList<Item>(itemsStored.values());
	}
	
	public int getSpaceStored() {
		return this.spaceStored;
	}
	
	public int getTotalSpace() {
		return this.space;
	}
}
