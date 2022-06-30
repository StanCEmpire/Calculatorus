package stancempire.calculatorus.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class CalcMath
{

	public static BigDecimal pow(BigDecimal a, int b)
	{
		
		BigDecimal result = a;
		
		if(b == 0)
		{
			
			return BigDecimal.ONE;
			
		}
		
		for(int i = 1; i < b; i++)
		{
			
			result = result.multiply(a);
			
		}
		
		return result.setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal fac(int n)
	{
		
		BigDecimal result = BigDecimal.valueOf(n);

		if(n == 0)
		{
			
			return BigDecimal.ONE;
			
		}
		
		for(int i = 1; i < n; i++)
		{
			
			result = result.multiply(BigDecimal.valueOf(n - i));
			
		}
		
		return result.setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal sine(BigDecimal x)
	{
		
		BigDecimal result = BigDecimal.ZERO;
		
		BigDecimal sX = x.subtract(BigDecimal.valueOf(2*Math.PI*Math.floor(x.doubleValue()/(2*Math.PI))));
		
		for(int i = 0; i < 100; i++)
		{
			
			result = result.add(pow(sX, 2*i+1).divide(fac(2*i+1), MathContext.DECIMAL128).multiply(pow(BigDecimal.valueOf(-1), i)));
			
		}
		
		int comp = sX.compareTo(BigDecimal.valueOf(Math.PI));
		
		if(comp == 1)
		{
			
			result.negate();
			
		}
		
		return result.setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal cosine(BigDecimal x)
	{
		
		return sine(x.add(BigDecimal.valueOf(Math.PI/2))).stripTrailingZeros();
		
	}
	
	public static BigDecimal tangent(BigDecimal x)
	{
		
		return sine(x).divide(cosine(x), MathContext.DECIMAL128).stripTrailingZeros();
		
	}
	
	public static boolean isEven(int n)
	{
		
		if(n/2*2 == n)
		{
			
			return true;
			
		}
	
		return false;
		
	}
	
}
