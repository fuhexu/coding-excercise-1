package com.myorg.codingexcercise;
import java.util.HashMap;
import java.util.ArrayList;
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
    private ArrayList<ArrayList<Shelf>> shelvesUsed; //indices: 0 for small, 1 for medium, 2 for large
    
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
        this.shelvesUsed = new ArrayList<ArrayList<Shelf>>();
        for(int i = 0; i < 3; i++) {
        	shelvesUsed.add(new ArrayList<Shelf>());
        }
        for(int i = 0 ; i < smallShelfCount; i++) {
        	shelvesUsed.get(Refrigerator.S_IND).add(new Shelf(smallShelfCuFt));
        }
        for(int i = 0 ; i < mediumShelfCount; i++) {
        	shelvesUsed.get(Refrigerator.M_IND).add(new Shelf(mediumShelfCuFt));
        }
        for(int i = 0 ; i < largeShelfCount; i++) {
        	shelvesUsed.get(Refrigerator.L_IND).add(new Shelf(largeShelfCuFt));
        }
        this.itemStorage = new HashMap<String, Shelf>();
        this.itemLookup = new HashMap<String, Item>();

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
    	
    	//check what kind of shelf to put the item on, and check how many of that shelf space are available
    	boolean result = false;
    	int itemSize = item.getCubicFt();
		String itemId = item.getItemId();
		//rearrange items in shelves depending on input level
		//put item into smallest available shelf
    	if (itemSize <= this.smallShelfCuFt) {
    		for(Shelf s: shelvesUsed.get(Refrigerator.S_IND)) {
    			if (s.add(item)) {
            		itemLookup.put(itemId, item);
            		itemStorage.put(itemId, s);
            		result = true;
            		break;
    			}
    		}
    		if (!result) {
    			for(Shelf s: shelvesUsed.get(Refrigerator.M_IND)) {
        			if (s.add(item)) {
                		itemLookup.put(itemId, item);
                		itemStorage.put(itemId, s);
                		result = true;
                		break;
        			}
        		}
    		}
    		if (!result) {
    			for(Shelf s: shelvesUsed.get(Refrigerator.L_IND)) {
        			if (s.add(item)) {
                		itemLookup.put(itemId, item);
                		itemStorage.put(itemId, s);
                		result = true;
                		break;
        			}
        		}
    		}
    	}
    	else if (itemSize <= this.mediumShelfCuFt) {
    		reArrangeFridge(Refrigerator.M_IND);
			for(Shelf s: shelvesUsed.get(Refrigerator.M_IND)) {
    			if (s.add(item)) {
            		itemLookup.put(itemId, item);
            		itemStorage.put(itemId, s);
            		result = true;
            		break;
    			}
    		}
    		if (!result) {
    			for(Shelf s: shelvesUsed.get(Refrigerator.L_IND)) {
        			if (s.add(item)) {
                		itemLookup.put(itemId, item);
                		itemStorage.put(itemId, s);
                		result = true;
                		break;
        			}
        		}
    		}
        		
    	}
    	else if (itemSize <= this.largeShelfCuFt) {
    		reArrangeFridge(Refrigerator.L_IND);
    		for(Shelf s: shelvesUsed.get(Refrigerator.L_IND)) {
    			if (s.add(item)) {
            		itemLookup.put(itemId, item);
            		itemStorage.put(itemId, s);
            		result = true;
            		break;
    			}
    		}
    		//try to move items in large down to medium, medium down to small
    	}
        return result;
    }
    
    public void reArrangeFridge(int level) {
    	//rearrange the fridge to be more optimal
    	
    	//first move mediums to small where applicable
    	//then move larges to small or medium where applicable
    	if (level >= Refrigerator.M_IND) {
    		for(Shelf s: shelvesUsed.get(Refrigerator.M_IND)) {
    			for(Item i: s.getItemList()) {
    				if(i.getCubicFt() <  this.smallShelfCuFt ) {
    					for(Shelf sh: shelvesUsed.get(Refrigerator.S_IND)) {
    		    			if (sh.add(i)) {
    		    				s.removeItem(i.getItemId());
    	    					itemLookup.remove(i.getItemId());
    	    	        		itemStorage.remove(i.getItemId());
    		            		itemLookup.put(i.getItemId(), i);
    		            		itemStorage.put(i.getItemId(), sh);
    		            		break;
    		    			}
    		    		}
    				}
    			}
    		}
    	}
    	
    	if (level >= Refrigerator.L_IND) {
    		for(Shelf s: shelvesUsed.get(Refrigerator.L_IND)) {
    			for(Item i: s.getItemList()) {
    				if(i.getCubicFt() <  this.smallShelfCuFt ) {
    					for(Shelf sh: shelvesUsed.get(Refrigerator.S_IND)) {
    		    			if (sh.add(i)) {
    		    				s.removeItem(i.getItemId());
    	    					itemLookup.remove(i.getItemId());
    	    	        		itemStorage.remove(i.getItemId());
    		            		itemLookup.put(i.getItemId(), i);
    		            		itemStorage.put(i.getItemId(), sh);
    		            		break;
    		    			}
    		    		}
    				}
    				else if (i.getCubicFt() < this.mediumShelfCuFt) {
    					for(Shelf sh: shelvesUsed.get(Refrigerator.M_IND)) {
    		    			if (sh.add(i)) {
    		    				s.removeItem(i.getItemId());
    	    					itemLookup.remove(i.getItemId());
    	    	        		itemStorage.remove(i.getItemId());
    		            		itemLookup.put(i.getItemId(), i);
    		            		itemStorage.put(i.getItemId(), sh);
    		            		break;
    		    			}
    		    		}
    				}
    			}
    		}
    	}
    	
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
    	for(ArrayList<Shelf> shelves : this.shelvesUsed) {
    		for(Shelf s: shelves) {
    			sum += s.getSpaceStored();
    		}
    	}
    	return sum;
    }



}
