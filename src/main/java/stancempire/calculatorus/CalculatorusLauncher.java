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

package stancempire.calculatorus;

import java.awt.image.BufferedImage;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stancempire.calculatorus.math.InputReader;
import stancempire.calculatorus.math.Operations;

public class CalculatorusLauncher extends Application
{
	
	private final Font buttonFont = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 24);
	private final Font inputFont = Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.ITALIC, 36);
	
	private String inputString = "";
	
	private Text outputText = new Text();
	private String outputString = "";
	
	private int pNum = 0;
	
	private TeXFormula visibleInput = new TeXFormula();
	private String tString = "";
	
	public static void main(String[] args)
	{
		
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception
	{

		Pane pane = new Pane();
		Canvas canvas = new Canvas(400, 600);
		pane.getChildren().add(canvas);
		
		//Buttons 0-9
		addNumberButton(100, 60, 0, 540, 0, pane, canvas);
		addNumberButton(100, 60, 0, 480, 1, pane, canvas);
		addNumberButton(100, 60, 100, 480, 2, pane, canvas);
		addNumberButton(100, 60, 200, 480, 3, pane, canvas);
		addNumberButton(100, 60, 0, 420, 4, pane, canvas);
		addNumberButton(100, 60, 100, 420, 5, pane, canvas);
		addNumberButton(100, 60, 200, 420, 6, pane, canvas);
		addNumberButton(100, 60, 0, 360, 7, pane, canvas);
		addNumberButton(100, 60, 100, 360, 8, pane, canvas);
		addNumberButton(100, 60, 200, 360, 9, pane, canvas);
		
		addDecimalPointButton(50, 60, 300, 540, pane, canvas);
		
		//Standard calculator buttons
		addEvaluateButton(50, 60, 350, 540, pane);
		addAllClearButton(100, 60, 100, 540, canvas, pane);
		addDelButton(100, 60, 200, 540, canvas, pane);
		
		//Basic operation buttons
		addBasicOperationButton(50, 60, 300, 480, Operations.ADD, Operations.ADD, "\\plus", canvas, pane);
		addBasicOperationButton(50, 60, 350, 480, Operations.SUBTRACT, Operations.SUBTRACT, "\\minus", canvas, pane);
		addBasicOperationButton(50, 60, 300, 420, Operations.MULTIPLY, Operations.MULTIPLY, "\\times", canvas, pane);
		addBasicOperationButton(50, 60, 350, 420, Operations.DIVIDE, Operations.DIVIDE, "\\div", canvas, pane);

		addStartPowerButton(50, 44, 0, 272, canvas, pane);
		addEndPowerButton(50, 44, 50, 272, canvas, pane);
		
		addFactorialButton(50, 44, 100, 272, canvas, pane);
		
		//Parenthesis
		addOpenParenthesisButton(50, 60, 300, 360, canvas, pane);
		addCloseParenthesisButton(50, 60, 350, 360, canvas, pane);
		
		//Functions
		addFunctionButton(100, 44, 0, 316, "sin", Operations.SINE, canvas, pane);
		addFunctionButton(100, 44, 100, 316, "cos", Operations.COSINE, canvas, pane);
		addFunctionButton(100, 44, 200, 316, "tan", Operations.TANGENT, canvas, pane);
		
		//Constants
		addPiButton(50, 44, 300, 316, canvas, pane);
		addEButton(50, 44, 350, 316, canvas, pane);

		addText(5, 50, outputText, pane);
		
		stage.setScene(new Scene(pane, 400, 600));
		stage.setResizable(false);
		stage.show();
		
	}
	
	private void addFactorialButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("!");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'!'", canvas);
			addToVisibleInput("!", canvas);
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addStartPowerButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("^");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'^'" + pNum + "('", canvas);
			addToVisibleInput("^{", canvas);
			pNum++;
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addEndPowerButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("`");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{

			addToInput("')" + (pNum - 1) + "'", canvas);
			addToVisibleInput("{}", canvas);
			tString = tString.replace("{}", "}");
			setVisibleInput(tString, canvas);
			pNum--;

		});
		pane.getChildren().add(button);
		
	}
	
	private void addNumberButton(int width, int height, int x, int y, int number, Pane pane, Canvas canvas)
	{
		
		Button button = new Button();
		String numberStr = String.valueOf(number);
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(numberStr);
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput(numberStr, canvas);
			addToVisibleInput(numberStr, canvas);

		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addDecimalPointButton(int width, int height, int x, int y, Pane pane, Canvas canvas)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(".");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput(".", canvas);
			addToVisibleInput(".", canvas);

		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addEvaluateButton(int width, int height, int x, int y, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("=");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			System.out.println(tString);

			//remove separator from start
			if(inputString.charAt(0) == '\'')
			{
				
				inputString = inputString.substring(1);
				
			}
			
			//remove separator from end
			if(inputString.charAt(inputString.length() - 1) == '\'')
			{
				
				inputString = inputString.substring(0, inputString.length() - 1);
				
			}
			
			//remove double separators from symbols placed next to each other
			inputString = inputString.replace("''", "'");
			
			InputReader reader = InputReader.createReader(inputString);
			
			try
			{
				
				setOutput(reader.compute().toPlainString());
				
			}
			catch(NumberFormatException | IndexOutOfBoundsException e)
			{
				
				setOutput("INPUT ERROR");
				
			}
			catch(ArithmeticException e)
			{
				
				setOutput("MATH ERROR");
				
			}
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addAllClearButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("AC");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			setInput("");
			setVisibleInput("", canvas);
			setOutput("");
			tString = "";
			pNum = 0;
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addDelButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("DEL");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			//Currently non-functional
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addBasicOperationButton(int width, int height, int x, int y, String buttonString, String internalString, String displayString, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(buttonString);
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'" + internalString + "'", canvas);
			addToVisibleInput(displayString, canvas);
						
		});
		
		pane.getChildren().add(button);
	}
	
	private void addOpenParenthesisButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("(");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'" + pNum + "(" + "'", canvas);
			addToVisibleInput("(", canvas);
			pNum++;
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addCloseParenthesisButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(")");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("')" + (pNum - 1) + "'", canvas);
			addToVisibleInput(")", canvas);
			pNum--;
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addFunctionButton(int width, int height, int x, int y, String name, String function, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(name);
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'~'" + function + "'", canvas);
			addToVisibleInput(functionLaTeXOf(function), canvas);
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addPiButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("\u03C0");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'*'pi", canvas);
			addToVisibleInput("\\pi", canvas);

		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addEButton(int width, int height, int x, int y, Canvas canvas, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("e");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'*'e", canvas);
			addToVisibleInput("\\mathbf{e}", canvas);
			refreshInput(canvas);

		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addText(int x, int y, Text text, Pane pane)
	{
		
		text.setFont(inputFont);
		text.relocate(x, y);
		pane.getChildren().add(text);
		
	}
	
	private void addToInput(String input, Canvas canvas)
	{
		
		if(!outputString.equals(""))
		{
			
			setOutput("");
			setInput("");
			setVisibleInput("", canvas);
			
		}
		
		inputString = inputString + input;
		
	}
	
	private void setInput(String input)
	{
		
		inputString = input;
		
	}
	
	private void addToVisibleInput(String input, Canvas canvas)
	{
		
		visibleInput.add(input);
		refreshInput(canvas);
		tString = tString + input;
		
	}
	
	private void setVisibleInput(String input, Canvas canvas)
	{
		
		visibleInput.setLaTeX(input);
		refreshInput(canvas);

	}
	
	private void setOutput(String output)
	{
		
		outputString = output;
		outputText.setText(outputString);
		
	}
	
	private String functionLaTeXOf(String function)
	{
		
		switch(function)
		{
		
			case Operations.SINE : return "\\sin";
			case Operations.COSINE : return "\\cos";
			case Operations.TANGENT : return "\\tan";
			case Operations.POWER : return "^";
			default : return "";
		
		}
		
	}
	
	private void refreshInput(Canvas canvas)
	{
		
		GraphicsContext ctx = canvas.getGraphicsContext2D();
		ctx.clearRect(0, 0, 400, 600);
		java.awt.Image aImage = visibleInput.createBufferedImage(TeXConstants.STYLE_TEXT, 36, java.awt.Color.BLACK, null);
		Image fImage = SwingFXUtils.toFXImage((BufferedImage)aImage, null);
		ctx.drawImage(fImage, 0, 0);
		
	}
	
}
