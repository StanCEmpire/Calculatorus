package stancempire.calculatorus;

import stancempire.calculatorus.math.InputReader;

public class Main
{

	public static void main(String[] args)
	{
				
		
		CalculatorusLauncher.main(args);
		
		double result = InputReader.createReader("2'over'3'times'3'over'4'plus'2").compute();
		System.out.println(result);
		
	}
	
}
