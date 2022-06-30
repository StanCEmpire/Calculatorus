package stancempire.calculatorus;

import java.math.BigDecimal;

import stancempire.calculatorus.math.CalcMath;
import stancempire.calculatorus.math.MathConstants;

public class Main
{

	public static void main(String[] args)
	{
				
		System.out.println(CalcMath.tangent(MathConstants.PI.multiply(BigDecimal.valueOf(2))));
		System.out.println(MathConstants.E());
		CalculatorusLauncher.main(args);
		
	}
	
}
