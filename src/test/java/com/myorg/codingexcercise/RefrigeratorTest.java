package com.myorg.codingexcercise;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kamoorr on 7/14/17.
 */

public class RefrigeratorTest {

    @Test
    public void putTest(){
        Refrigerator refrigerator = new Refrigerator(2, 100, 3, 40, 2, 20);

        Item largeItem1 = new Item("milk", 80);
        Item largeItem2 = new Item("organic-milk", 50);
        Item largeItem3 = new Item("large-soda", 90);
        Item largeItem4 = new Item("large-yogurt", 45);

        Item smallItem1 = new Item("ketchup", 20);
        Item smallItem2 = new Item("mayo", 10);
        Item smallItem3 = new Item("ginger-ale", 15);
        Item smallItem4 = new Item("bbq-sauce", 10);


        Assert.assertTrue(refrigerator.put(largeItem1));
        Assert.assertTrue(refrigerator.put(largeItem2));
        Assert.assertFalse(refrigerator.put(largeItem3));
        Assert.assertTrue(refrigerator.put(largeItem4));

        Assert.assertTrue(refrigerator.put(smallItem1));
        Assert.assertTrue(refrigerator.put(smallItem2));
        Assert.assertTrue(refrigerator.put(smallItem3));
        Assert.assertTrue(refrigerator.put(smallItem4));

        Assert.assertEquals(refrigerator.getUsedSpace(), 230);



    }
    @Test
    public void getTest(){

        Refrigerator refrigerator = new Refrigerator(2, 100, 3, 40, 2, 20);

        Item largeItem1 = new Item("milk", 80);
        Item largeItem2 = new Item("organic-milk", 50);
        Item largeItem3 = new Item("large-soda", 90);
        Item largeItem4 = new Item("large-yogurt", 45);

        Item smallItem1 = new Item("ketchup", 20);
        Item smallItem2 = new Item("mayo", 10);
        Item smallItem3 = new Item("ginger-ale", 15);
        Item smallItem4 = new Item("bbq-sauce", 10);
             
        refrigerator.put(largeItem1);
        refrigerator.put(largeItem2);        
        refrigerator.put(largeItem4);
                
        refrigerator.put(smallItem1);
        refrigerator.put(smallItem2);
        refrigerator.put(smallItem3);
        refrigerator.put(smallItem4);

        Assert.assertEquals(refrigerator.getUsedSpace(), 230);

        Item item = refrigerator.get("ginger-ale");
        Assert.assertEquals(item.getItemId(), "ginger-ale");
        Assert.assertEquals(refrigerator.getUsedSpace(), 215);


        //Add some medium sized stuff

        Item medItem1 = new Item("tomato-bag", 40);
        Item medItem2 = new Item("onion-bag", 35);
        Item medItem3 = new Item("fruits", 15);
        Assert.assertTrue(refrigerator.put(medItem1));
        Assert.assertTrue(refrigerator.put(medItem2));
        Assert.assertTrue(refrigerator.put(medItem3));

        Assert.assertEquals(refrigerator.getUsedSpace(), 305);

    }
}
