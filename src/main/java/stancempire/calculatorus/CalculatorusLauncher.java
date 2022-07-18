package stancempire.calculatorus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import stancempire.calculatorus.math.InputReader;
import stancempire.calculatorus.math.MathConstants;
import stancempire.calculatorus.math.Operations;

public class CalculatorusLauncher extends Application
{
	
	private final Font buttonFont = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 24);
	private final Font inputFont = Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.ITALIC, 36);
	
	private Text inputText = new Text();
	private String visibleInputString = "";
	
	private String inputString = "";
	
	private Text outputText = new Text();
	private String outputString = "";
	
	private int pNum = 0;
	
	public static void main(String[] args)
	{
		
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception
	{

		Pane pane = new Pane();
		
		//Buttons 0-9
		addNumberButton(100, 60, 0, 540, 0, pane);
		addNumberButton(100, 60, 0, 480, 1, pane);
		addNumberButton(100, 60, 100, 480, 2, pane);
		addNumberButton(100, 60, 200, 480, 3, pane);
		addNumberButton(100, 60, 0, 420, 4, pane);
		addNumberButton(100, 60, 100, 420, 5, pane);
		addNumberButton(100, 60, 200, 420, 6, pane);
		addNumberButton(100, 60, 0, 360, 7, pane);
		addNumberButton(100, 60, 100, 360, 8, pane);
		addNumberButton(100, 60, 200, 360, 9, pane);
		
		//Standard calculator buttons
		addEvaluateButton(100, 60, 300, 540, pane);
		addAllClearButton(100, 60, 100, 540, pane);
		addDelButton(100, 60, 200, 540, pane);
		
		//Basic operation buttons
		addBasicOperationButton(50, 60, 300, 480, "+", Operations.ADD, pane);
		addBasicOperationButton(50, 60, 350, 480, "-", Operations.SUBTRACT, pane);
		addBasicOperationButton(50, 60, 300, 420, "×", Operations.MULTIPLY, pane);
		addBasicOperationButton(50, 60, 350, 420, "÷", Operations.DIVIDE, pane);

		//Parenthesis
		addOpenParenthesisButton(50, 60, 300, 360, pane);
		addCloseParenthesisButton(50, 60, 350, 360, pane);
		
		//Functions
		addFunctionButton(100, 44, 0, 316, "sin", Operations.SINE, pane);
		addFunctionButton(100, 44, 100, 316, "cos", Operations.COSINE, pane);
		addFunctionButton(100, 44, 200, 316, "tan", Operations.TANGENT, pane);
		
		//Constants
		addPiButton(50, 44, 300, 316, pane);
		
		//Input and output
		addText(5, 5, inputText, pane);
		addText(5, 50, outputText, pane);
		
		stage.setScene(new Scene(pane, 400, 600));
		stage.setResizable(false);
		stage.show();
		
	}
	
	private void addNumberButton(int width, int height, int x, int y, int number, Pane pane)
	{
		
		Button button = new Button();
		String numberStr = String.valueOf(number);
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(numberStr);
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput(numberStr);
			addToVisibleInput(numberStr);

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
			setOutput(reader.compute().toPlainString());
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addAllClearButton(int width, int height, int x, int y, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("AC");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			setInput("");
			setVisibleInput("");
			setOutput("");
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addDelButton(int width, int height, int x, int y, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("DEL");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			setOutput("");
			setVisibleInput(visibleInputString.substring(0, visibleInputString.length() - 1));
			
			if(inputString.lastIndexOf("'") == inputString.length() - 1)
			{
				
				inputString = inputString.substring(0, inputString.length() - 1);
				inputString = inputString.substring(0, inputString.lastIndexOf("'"));
				setInput(inputString);
								
			}
			else
			{
				
				setInput(inputString.substring(0, inputString.length() - 1));
				
			}

		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addBasicOperationButton(int width, int height, int x, int y, String displayString, String internalString, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(displayString);
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'" + internalString + "'");
			addToVisibleInput(displayString);
						
		});
		
		pane.getChildren().add(button);
	}
	
	private void addOpenParenthesisButton(int width, int height, int x, int y, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("(");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'" + pNum + "(" + "'");
			addToVisibleInput("(");
			pNum++;
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addCloseParenthesisButton(int width, int height, int x, int y, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(")");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("')" + (pNum - 1) + "'");
			addToVisibleInput(")");
			pNum--;
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addFunctionButton(int width, int height, int x, int y, String name, String function, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText(name);
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput("'~'" + function + "'");
			addToVisibleInput(functionNameOf(function));
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addPiButton(int width, int height, int x, int y, Pane pane)
	{
		
		Button button = new Button();
		
		button.setPrefSize(width, height);
		button.relocate(x, y);
		button.setText("\u03C0");
		button.setFont(buttonFont);
		
		button.setOnMousePressed(event ->
		{
			
			addToInput(MathConstants.PI.toPlainString());
			addToVisibleInput("\u03C0");

		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addText(int x, int y, Text text, Pane pane)
	{
		
		text.setFont(inputFont);
		text.relocate(x, y);
		pane.getChildren().add(text);
		
	}
	
	private void addToInput(String input)
	{
		
		if(!outputString.equals(""))
		{
			
			setOutput("");
			setInput("");
			setVisibleInput("");
			
		}
		
		inputString = inputString + input;
		
	}
	
	private void setInput(String input)
	{
		
		inputString = input;
		
	}
	
	private void addToVisibleInput(String input)
	{
		
		visibleInputString = visibleInputString + input;
		inputText.setText(visibleInputString);
		
	}
	
	private void setVisibleInput(String input)
	{
		
		visibleInputString = input;
		inputText.setText(visibleInputString);

	}
	
	private void setOutput(String output)
	{
		
		outputString = output;
		outputText.setText(outputString);
		
	}
	
	private String functionNameOf(String function)
	{
		
		switch(function)
		{
		
			case Operations.SINE : return "sin";
			case Operations.COSINE : return "cos";
			case Operations.TANGENT : return "tan";
			default : return "";
		
		}
		
	}
	
}
