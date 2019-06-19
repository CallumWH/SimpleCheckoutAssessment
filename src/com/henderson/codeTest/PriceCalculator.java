package com.henderson.codeTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

// * The initial pricing model is defined below:
//*            PRICE LIST
//*        StockCode   price    offers
//*          A         1.99     -
//*          B         3.23     buy TWO, get a THIRD one free
//*          C         0.59     -
//*          D         2.30     Two for 4.00

public class PriceCalculator
{
	private HashMap<String, Double>  inventoryPrices = new HashMap<String, Double>();
	private HashMap<String, Integer> shoppingCart    = new HashMap<String, Integer>();

	public PriceCalculator()
	{
		// Ideally, this would be parsed and read in from a .txt file
		addToInventory("A", 1.99);
		addToInventory("B", 3.23);
		addToInventory("C", 0.59);
		addToInventory("D", 2.30);
	}

	public BigDecimal getPrice(String stockCode)
	{
		try
		{
			parseStringToHashMap(stockCode);
		}
		catch (Exception e)
		{
			// reprompt for item code
			System.out.println("invalid stockCode");
		}

		for (String name : shoppingCart.keySet())
		{
			System.out.println("Item : " + name + "\n Value : " + shoppingCart.get(name) + "\n");
		}

		return new BigDecimal(calculateFinalPrice());
	}

	private double calculateFinalPrice()
	{
		double finalPrice = 0;

		for (String name : shoppingCart.keySet())
		{
			double price         = inventoryPrices.get(name);
			int    numberOfItems = shoppingCart.get(name);
			double discount      = 0;

			// calculate discounts according to the deal of the product
			switch (name)
			{
				case "B":

					discount = ((int) numberOfItems / 3) * price;
					break;

				case "D":
					discount = ((int) numberOfItems / 2) * 0.6;
					break;

				default:
					break;
			}

			finalPrice += (numberOfItems * price) - discount;
		}

		return finalPrice;
	}

	private void parseStringToHashMap(String stockCode) throws IOException
	{

		// convert to upper case to avoid case errors
		stockCode = stockCode.toUpperCase();

		for (int i = 0; i < stockCode.length(); i++)
		{
			String item = stockCode.substring(i, i + 1);

			// check if item is in the inventory
			if (checkValidItem(item))
			{
				addToShoppingCart(item);
			}
			else
			{
				// throw an error if it isn't in the inventory
				throw new IOException("Invalid stockCode");
			}
		}
	}

	private Boolean checkValidItem(String itemName)
	{
		if (inventoryPrices.get(itemName) != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private Boolean addToInventory(String itemName, double price)
	{
		if (inventoryPrices.get(itemName) == null)
		{
			inventoryPrices.put(itemName, price);
			return true;
		}
		else
		{
			return false;
		}
	}

	private void addToShoppingCart(String item)
	{
		if (shoppingCart.get(item) == null)
		{
			shoppingCart.put(item, 1);
		}
		else
		{
			shoppingCart.replace(item, shoppingCart.get(item) + 1);
		}
	}

}
