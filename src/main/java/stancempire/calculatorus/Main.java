package stancempire.calculatorus;

import java.math.BigDecimal;

import stancempire.calculatorus.math.InputReader;

public class Main
{

	public static void main(String[] args)
	{
				
		
		//CalculatorusLauncher.main(args);
		
		BigDecimal result = InputReader.createReader("1'plus'1'over'3").compute();
		System.out.println(result);
		
	}
	
}
