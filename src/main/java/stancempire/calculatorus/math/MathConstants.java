package stancempire.calculatorus.math;

import java.math.BigDecimal;
import java.math.MathContext;

public class MathConstants 
{

	public static final BigDecimal E()
	{
		
		BigDecimal E = BigDecimal.valueOf(0);
		
		for(int i = 0; i < 31; i++)
		{
			
			E = E.add(BigDecimal.ONE.divide(CalcMath.fac(i), MathContext.DECIMAL128));
			
		}
		
		return E.round(MathContext.DECIMAL128);
		
	}
	
}
