package stancempire.calculatorus.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MathConstants 
{

	public static final BigDecimal E()
	{
		
		BigDecimal E = BigDecimal.valueOf(0);
		
		for(int i = 0; i < 31; i++)
		{
			
			E = E.add(BigDecimal.ONE.divide(CalcMath.fac(i), MathContext.DECIMAL128));
			
		}
		
		return E.setScale(15, RoundingMode.HALF_UP);
		
	}
	
	public static final BigDecimal PI = new BigDecimal("3.141592653589793");
	
}
