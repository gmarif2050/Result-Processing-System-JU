package com.rps.utility.tabulation;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

public class UtilityService {
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
