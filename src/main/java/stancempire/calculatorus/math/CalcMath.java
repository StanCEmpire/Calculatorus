/*
 * Calculatorus is a calculator application for Windows and MacOS.
 * Copyright (C) 2022 StanCEmpire and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
		
		return result.setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
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
		
		return result.setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
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
		
		return result.setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal cosine(BigDecimal x)
	{
		
		return sine(x.add(BigDecimal.valueOf(Math.PI/2))).setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal tangent(BigDecimal x)
	{
		
		return sine(x).divide(cosine(x), MathContext.DECIMAL128).setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
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
		
		return BigDecimal.valueOf(Math.asin(x.doubleValue())).setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal arcCosine(BigDecimal x)
	{
		
		return BigDecimal.valueOf(Math.acos(x.doubleValue())).setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public static BigDecimal arcTangent(BigDecimal x)
	{
		
		return BigDecimal.valueOf(Math.atan(x.doubleValue())).setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
}
