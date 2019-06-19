package com.henderson.codeTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main
{

	public static void main(String[] args)
	{
		PriceCalculator priceCalculator = new PriceCalculator();
		BigDecimal price = priceCalculator.getPrice("ABBBCdd");
		
		
		System.out.println("Final price = " + price.setScale(2, RoundingMode.CEILING));
	}

}
