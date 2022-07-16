package stancempire.calculatorus.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class InputReader
{

	private ArrayList<String> input;
	private int pNum;
	
	private InputReader(ArrayList<String> input, int pNum)
	{
		
		this.input = input;
		this.pNum = pNum;
		
	}
	
	public static InputReader createReader(String inputString)
	{
		
		String[] inputSequence = inputString.split("'"); //let ['] be the character to separate operations
		
		ArrayList<String> input = new ArrayList<>();
		
		for(int i = 0; i < inputSequence.length; i++)
		{
			
			input.add(inputSequence[i]);
			
		}
				
		return new InputReader(input, 0);
		
	}
	
	private InputReader createInternalReader(ArrayList<String> input, int pNum)
	{
				
		return new InputReader(input, pNum);
		
	}
	
	public BigDecimal compute()
	{
		
		System.out.println(input);

		
		while(input.contains("*" + pNum + "("))
		{
			
			int oIndex = input.indexOf("*" + pNum + "(");
			int cIndex = input.indexOf(")" + pNum + "*");
						
			BigDecimal calc = createInternalReader(new ArrayList<String>(input.subList(oIndex + 1, cIndex)), ++pNum).compute();
			
			for(int i = cIndex; i > oIndex; i--)
			{
				
				input.remove(i);
				
			}
			
			input.set(oIndex, calc.toPlainString());
			System.out.println(input);
			pNum--;			
			
		}
		
		while(input.contains(Operations.MULTIPLY) || input.contains(Operations.DIVIDE))
		{

			int index = indexOfEither(Operations.MULTIPLY, Operations.DIVIDE);
			
			if(index > 0)
			{
				
				if(input.get(index).equals(Operations.MULTIPLY))
				{
					
					BigDecimal a = new BigDecimal(input.get(index - 1));
					BigDecimal b = new BigDecimal(input.get(index + 1));
					BigDecimal calc = a.multiply(b, MathContext.DECIMAL128);
					input.set(index, "" + calc);
					input.remove(index + 1);
					input.remove(index - 1);
					
					
				}
				else if(input.get(index).equals(Operations.DIVIDE))
				{
					
					BigDecimal a = new BigDecimal(input.get(index - 1));
					BigDecimal b = new BigDecimal(input.get(index + 1));
					BigDecimal calc = a.divide(b, MathContext.DECIMAL128);
					input.set(index, "" + calc);
					input.remove(index + 1);
					input.remove(index - 1);
					
				}
				
			}
			
		}
		
		while(input.contains(Operations.ADD) || input.contains(Operations.SUBTRACT))
		{
			
			int index = indexOfEither(Operations.ADD, Operations.SUBTRACT);
			
			if(index > 0)
			{
				
				if(input.get(index).equals(Operations.ADD))
				{
					
					BigDecimal a = new BigDecimal(input.get(index - 1));
					BigDecimal b = new BigDecimal(input.get(index + 1));
					BigDecimal calc = a.add(b, MathContext.DECIMAL128);
					input.set(index, "" + calc);
					input.remove(index + 1);
					input.remove(index - 1);
					
				}
				else if(input.get(index).equals(Operations.SUBTRACT))
				{
					
					BigDecimal a = new BigDecimal(input.get(index - 1));
					BigDecimal b = new BigDecimal(input.get(index + 1));
					BigDecimal calc = a.subtract(b, MathContext.DECIMAL128);
					input.set(index, "" + calc);
					input.remove(index + 1);
					input.remove(index - 1);
					
				}
				
			}
			
		}
		
		return new BigDecimal(input.get(0)).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	public int indexOfEither(String string, String string2)
	{
		
		for(String element : input)
		{
			
			if(element.equals(string) || element.equals(string2))
			{
				
				return input.indexOf(element);
				
			}
			
		}
		
		return -1;
		
	}
	
}
