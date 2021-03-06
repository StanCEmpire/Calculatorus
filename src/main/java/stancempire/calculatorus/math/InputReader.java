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
		
		//Finds and indicates implicit multiplications
		for(int i = 0; i < input.size() - 1; i++)
		{
			
			if(i + 1 < input.size() && input.get(i).matches("\\-?([0-9]*\\.)?[0-9]+") && (input.get(i + 1).contains("(") || input.get(i + 1).contains("*") || input.get(i + 1).contains("~")))
			{
				
				input.add(i + 1, Operations.MULTIPLY);
				
			}
			else if(i + 2 < input.size() && input.get(i).contains("*") && (input.get(i + 2).contains("(") || input.get(i + 2).contains("*") || input.get(i + 2).contains("~")))
			{
				
				input.add(i + 2, Operations.MULTIPLY);
				
			}
			else if(input.get(i).contains(")") && (input.get(i + 1).contains("(") || input.get(i + 1).contains("*") || input.get(i + 1).contains("~") || input.get(i + 1).matches("\\-?([0-9]*\\.)?[0-9]+")))
			{
				
				input.add(i + 1, Operations.MULTIPLY);
				
			}
		}
		
		while(input.contains("*"))
		{
			
			int index = input.indexOf("*") + 1;
			String val = getConstant(input.get(index));
			input.set(index, val);
			input.remove(index - 1);
			
		}
		
		while(input.contains(pNum + "("))
		{
			
			int oIndex = input.indexOf(pNum + "(");
			int cIndex = input.indexOf(")" + pNum);
						
			BigDecimal calc = createInternalReader(new ArrayList<String>(input.subList(oIndex + 1, cIndex)), ++pNum).compute();
			
			for(int i = cIndex; i > oIndex; i--)
			{
				
				input.remove(i);
				
			}
			
			input.set(oIndex, calc.toPlainString());
			pNum--;			
			
		}
		
		while(input.contains("~"))
		{

			int index = input.indexOf("~") + 1;
			BigDecimal calc = computeFunction(input.get(index), new BigDecimal(input.get(index + 1)));
			input.set(index, "" + calc);
			input.remove(index + 1);
			input.remove(index - 1);
			
		}
		
		while(input.contains("^"))
		{
			
			int index = input.indexOf("^");
			
			BigDecimal x = new BigDecimal(input.get(index - 1));
			BigDecimal n = new BigDecimal(input.get(index + 1));
			BigDecimal calc = CalcMath.pow(x, n.intValueExact());
			input.set(index, "" + calc);
			input.remove(index + 1);
			input.remove(index - 1);	
			
		}
		
		while(input.contains("!"))
		{
			
			int index = input.indexOf("!");

			BigDecimal n = new BigDecimal(input.get(index - 1));
			BigDecimal calc = CalcMath.fac(n.intValueExact());
			input.set(index, "" + calc);
			input.remove(index - 1);
			
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
		
		return new BigDecimal(input.get(0)).setScale(14, RoundingMode.HALF_UP).stripTrailingZeros();
		
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
	
	private BigDecimal computeFunction(String function, BigDecimal value)
	{
		
		switch(function)
		{
		
			case Operations.SINE : return CalcMath.sine(value);
			case Operations.COSINE : return CalcMath.cosine(value);
			case Operations.TANGENT : return CalcMath.tangent(value);
			default : return BigDecimal.ZERO;
		
		}
		
	}
	
	private String getConstant(String constant)
	{
		
		switch(constant)
		{
		
			case "e" : return MathConstants.E.toPlainString();
			case "pi" : return MathConstants.PI.toPlainString();
			default : return "";
		
		}
		
	}
	
}
