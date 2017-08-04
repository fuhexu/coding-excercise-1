package com.myorg.codingexcercise;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
/**
 *
 * You are about to build a Refrigerator which has SMALL, MEDIUM, and LARGE sized shelves.
 *
 * Method signature are given below. You need to implement the logic to
 *
 *  1. To keep track of items put in to the Refrigerator (add or remove)
 *  2. Make sure enough space available before putting it in
 *  3. Make sure space is used as efficiently as possible
 *  4. Make sure code runs efficiently
 *
 *
 * Created by kamoorr on 7/14/17.
 */
public class Refrigerator {
	private static final int L_IND = 2;
	private static final int M_IND = 1;
	private static final int S_IND = 0;
    /**
     * Refrigerator Total Cubic Feet (CuFt)
     */
    private int cubicFt;

    /**
     * Large size shelf count and size of one shelf
     */
    private int largeShelfCount;
    private int largeShelfCuFt;

    /**
     * Medium size shelf count and size of one shelf
     */
    private int mediumShelfCount;
    private int mediumShelfCuFt;

    /**
     * Medium size shelf count and size of one shelf
     */
    private int smallShelfCount;
    private int smallShelfCuFt;
    

    //we need a structure to track what's being used - retrievable by item id
    //private ArrayList<ArrayList<Shelf>> shelvesUsed; //indices: 0 for small, 1 for medium, 2 for large
    private ArrayList<Shelf> shelvesUsed;
    private HashMap<String, Shelf> itemStorage;
    private HashMap<String, Item> itemLookup;
    /**
     *
     *  Create a new refrigerator by specifying shelfSize and count for SMALL, MEDIUM, LARGE shelves
     * @param largeShelfCount
     * @param largeShelfCuFt
     * @param mediumShelfCount
     * @param mediumShelfCuFt
     * @param smallShelfCount
     * @param smallShelfCuFt
     */
   public Refrigerator(int largeShelfCount, int largeShelfCuFt, int mediumShelfCount, int mediumShelfCuFt, int smallShelfCount, int smallShelfCuFt) {

       /**
        * Calculating total cuft as local variable to improve performance. Assuming no vacant space in the refrigerator
        *
        */
        this.cubicFt = (largeShelfCount * largeShelfCuFt) + (mediumShelfCount * mediumShelfCuFt) + (smallShelfCount* smallShelfCuFt);

        this.largeShelfCount = largeShelfCount;
        this.largeShelfCuFt = largeShelfCuFt;

        this.mediumShelfCount = mediumShelfCount;
        this.mediumShelfCuFt = mediumShelfCuFt;

        this.smallShelfCount = smallShelfCount;
        this.smallShelfCuFt = smallShelfCuFt;
        this.shelvesUsed = new ArrayList<Shelf>();
        for(int i = 0 ; i < smallShelfCount; i++) {
        	shelvesUsed.add(new Shelf(smallShelfCuFt));
        }
        for(int i = 0 ; i < mediumShelfCount; i++) {
        	shelvesUsed.add(new Shelf(mediumShelfCuFt));
        }
        for(int i = 0 ; i < largeShelfCount; i++) {
        	shelvesUsed.add(new Shelf(largeShelfCuFt));
        }
        this.itemStorage = new HashMap<String, Shelf>(); //id-to-shelf lookup
        this.itemLookup = new HashMap<String, Item>(); //id-to-item lookup

    }

    /**
     * Implement logic to put an item to this refrigerator. Make sure
     *  -- You have enough vacant space in the refrigerator
     *  -- Make this action efficient in a way to increase maximum utilization of the space, re-arrange items when necessary
     *
     * Return
     *      true if put is successful
     *      false if put is not successful, for example, if you don't have enough space any shelf, even after re-arranging
     *
     *
     * @param item
     */
   
    public boolean put(Item item) {
    	/* Shelf Packing Algorithm:
    	 * 1. Go through every item, sort descending by size
    	 * 2. Go through every shelf, sort ascending by remaining space
    	 * 3. For every item, go through shelves ascending until a shelf that can hold the item is found
    	 * 		a. If the new item could be added, update the item list and shelves
    	 * 		b. If the new item could not be added, do not update the item list, do not update the shelves
    	 */
    	boolean result = false;
    	if (!itemLookup.containsKey(item.getItemId())) {
	    	ArrayList<Item> items = new ArrayList<Item>(itemLookup.values());
	    	Collections.sort(items, new Comparator<Item>() {
	    		public int compare(Item a1, Item a2) {
	    			return a2.getCubicFt() - a1.getCubicFt();
	    		}
	    	});
	    	items.add(item);
	    	//add the newest item last, since we want to see if it fits into a situation where every other item is optimally packed
	    	ArrayList<Shelf> shelves = new ArrayList<Shelf>();
	    	for(int n = 0 ; n < smallShelfCount; n++) {
	    		shelves.add(new Shelf(smallShelfCuFt));
	        }
	        for(int n = 0 ; n < mediumShelfCount; n++) {
	        	shelves.add(new Shelf(mediumShelfCuFt));
	        }
	        for(int n = 0 ; n < largeShelfCount; n++) {
	        	shelves.add(new Shelf(largeShelfCuFt));
	        }
	    	Collections.sort(shelves, new Comparator<Shelf>(){
	    		public int compare(Shelf s1, Shelf s2) {
	    			int remaining1 = s1.getTotalSpace() - s1.getSpaceStored();
	    			int remaining2 = s2.getTotalSpace() - s2.getSpaceStored();
	    			return remaining1 - remaining2;
	    		}
	    	});
	    	
	    	for(Item iteratedItems: items) {
	    		Shelf toAdd = null;
	    		for(Shelf s: shelves) {
	    			if (s.add(iteratedItems)) {
	    				toAdd = s;
	            		break;
	    			}
	    		}
	    		if (toAdd != null) {
	        		itemStorage.remove(iteratedItems.getItemId());
            		itemStorage.put(iteratedItems.getItemId(), toAdd);
            		Collections.sort(shelves, new Comparator<Shelf>(){
                		public int compare(Shelf s1, Shelf s2) {
                			int remaining1 = s1.getTotalSpace() - s1.getSpaceStored();
                			int remaining2 = s2.getTotalSpace() - s2.getSpaceStored();
                			return remaining1 - remaining2;
                		}
                	});
            		if (!itemLookup.containsKey(iteratedItems.getItemId())) {
            			itemLookup.put(iteratedItems.getItemId(), iteratedItems);
            			result = true;
            		}
	    		}
	    	}
	    	//if we added the item, then we update our sehlves
	    	if (result) {
	    		this.shelvesUsed = shelves;
	    	}
    	}
    	return result;
    }
    


    /**
     * remove and return the requested item
     * Return null when not available
     * @param itemId
     * @return
     */
    public Item get(String itemId) {
    	Item result = null;
    	if (itemLookup.containsKey(itemId) && itemStorage.containsKey(itemId)) {
    		result = itemLookup.get(itemId);
    		itemStorage.get(itemId).removeItem(itemId);
    		itemLookup.remove(itemId);
    		itemStorage.remove(itemId);
    	}
        return result;

    }

    /**
     * Return current utilization of the space
     * @return
     */
    public float getUtilizationPercentage() {
        return (float)getUsedSpace() / (float)this.cubicFt;
    }

    /**
     * Return current utilization in terms of cuft
     * @return
     */
    public int getUsedSpace() {
    	int sum = 0;
		for(Shelf s: this.shelvesUsed) {
			sum += s.getSpaceStored();
		}
    	return sum;
    }



}
