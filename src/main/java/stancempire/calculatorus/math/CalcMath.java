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
		
		for(int n = 1; n < b; n++)
		{
			
			result = result.multiply(a);
			
		}
		
		return result.setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal fac(int x)
	{
		
		BigDecimal result = BigDecimal.valueOf(x);

		if(x == 0)
		{
			
			return BigDecimal.ONE;
			
		}
		
		for(int n = 1; n < x; n++)
		{
			
			result = result.multiply(BigDecimal.valueOf(x - n));
			
		}
		
		return result.setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal sine(BigDecimal x)
	{
		
		BigDecimal result = BigDecimal.ZERO;
		
		BigDecimal sX = x.subtract(BigDecimal.valueOf(2*Math.PI*Math.floor(x.doubleValue()/(2*Math.PI))));
		
		for(int n = 0; n < 100; n++)
		{
			
			result = result.add(pow(sX, 2*n+1).divide(fac(2*n+1), MathContext.DECIMAL128).multiply(pow(BigDecimal.valueOf(-1), n)));
			
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
		
		return sine(x.add(BigDecimal.valueOf(Math.PI/2))).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal tangent(BigDecimal x)
	{
		
		return sine(x).divide(cosine(x), MathContext.DECIMAL128).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static boolean isEven(int x)
	{
		
		if(x/2*2 == x)
		{
			
			return true;
			
		}
	
		return false;
		
	}
	
	public static ComplexNumber rcis(BigDecimal modulus, BigDecimal argument)
	{
		
		BigDecimal real = modulus.multiply(cosine(argument));
		BigDecimal imaginary = modulus.multiply(sine(argument));
		
		return ComplexNumber.of(real, imaginary);
		
	}
	
	public static BigDecimal arcSine(BigDecimal x)
	{
		
		return BigDecimal.valueOf(Math.asin(x.doubleValue())).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal arcCosine(BigDecimal x)
	{
		
		return BigDecimal.valueOf(Math.acos(x.doubleValue())).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal arcTangent(BigDecimal x)
	{
		
		return BigDecimal.valueOf(Math.atan(x.doubleValue())).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
}
