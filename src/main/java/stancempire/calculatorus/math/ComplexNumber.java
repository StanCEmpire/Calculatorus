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

public class ComplexNumber
{

	private final BigDecimal real;
	private final BigDecimal imaginary;
	
	private ComplexNumber(BigDecimal real, BigDecimal imaginary)
	{
		
		this.real = real;
		this.imaginary = imaginary;
		
	}
	
	public static ComplexNumber of(BigDecimal real, BigDecimal imaginary)
	{
		
		return new ComplexNumber(real, imaginary);
		
	}
	
	public BigDecimal getRealPart()
	{
		
		return real;
		
	}
	
	public BigDecimal getImaginaryPart()
	{
		
		return imaginary;
		
	}
	
	public BigDecimal getModulus()
	{
		
		return CalcMath.pow(getRealPart(), 2).add(CalcMath.pow(getImaginaryPart(), 2)).sqrt(MathContext.DECIMAL128).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
	@Override
	public String toString()
	{
		
		return real.toString() + (getImaginaryPart().signum() == -1 ? "-" : "+") + getImaginaryPart().abs().toString() + "i";
		
	}
	
	public ComplexNumber add(ComplexNumber x)
	{
		
		BigDecimal real = this.getRealPart().add(x.getRealPart());
		BigDecimal imaginary = this.getImaginaryPart().add(x.getImaginaryPart());
		
		return new ComplexNumber(real, imaginary);
		
	}
	
	public ComplexNumber subtract(ComplexNumber x)
	{
		
		BigDecimal real = this.getRealPart().subtract(x.getRealPart());
		BigDecimal imaginary = this.getImaginaryPart().subtract(x.getImaginaryPart());
		
		return new ComplexNumber(real, imaginary);
		
	}
	
	public ComplexNumber multiply(ComplexNumber x)
	{
		
		BigDecimal real = this.getRealPart().multiply(x.getRealPart()).subtract(this.getImaginaryPart().multiply(x.getImaginaryPart()));
		BigDecimal imaginary = this.getRealPart().multiply(x.getImaginaryPart()).add(this.getImaginaryPart().multiply(x.getRealPart()));
		
		return new ComplexNumber(real, imaginary);
		
	}
	
	public ComplexNumber divide(ComplexNumber x)
	{
		
		ComplexNumber numr = this.multiply(x.conjugate());
		ComplexNumber denm = x.multiply(x.conjugate());
		
		BigDecimal real = numr.getRealPart().divide(denm.getRealPart());
		BigDecimal imaginary = numr.getImaginaryPart().divide(denm.getRealPart());
		
		return new ComplexNumber(real, imaginary);
		
	}
	
	public ComplexNumber conjugate()
	{
		
		BigDecimal real = this.getRealPart();
		BigDecimal imaginary = this.getImaginaryPart().negate();
		
		return new ComplexNumber(real, imaginary);
		
	}
	
	public BigDecimal getArgument()
	{
		
		return CalcMath.arcTangent(getImaginaryPart().divide(getRealPart(), MathContext.DECIMAL128)).setScale(15, RoundingMode.HALF_UP).stripTrailingZeros();
		
	}
	
}
