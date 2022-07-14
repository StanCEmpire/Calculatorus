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

public class CalculatorusLauncher extends Application
{
	
	private final Font buttonFont = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 24);
	private final Font inputFont = Font.font("Times New Roman", FontWeight.NORMAL, FontPosture.ITALIC, 36);
	
	private Text inputText = new Text();
	private String inputString = "";
	
	private Text outputText = new Text();
	private String outputString = "";
	
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
		
		//Evaluate Button
		addEvaluateButton(100, 60, 300, 540, pane);
		
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
			
			addInput(String.valueOf(numberStr));
			
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
			
			InputReader reader = InputReader.createReader(inputString);
			setOutput(reader.compute().toPlainString());
			
		});
		
		pane.getChildren().add(button);
		
	}
	
	private void addText(int x, int y, Text text, Pane pane)
	{
		
		text.setFont(inputFont);
		text.relocate(x, y);
		pane.getChildren().add(text);
		
	}
	
	private void addInput(String input)
	{
		
		if(!outputString.isEmpty())
		{
			
			setOutput("");
			setInput("");
			
		}
		
		inputString = inputString + input;
		inputText.setText(inputString);
		
	}
	
	private void setInput(String input)
	{
		
		inputString = input;
		inputText.setText(inputString);
		
	}
	
	private void setOutput(String output)
	{
		
		outputString = output;
		outputText.setText(outputString);
		
	}
	
}
