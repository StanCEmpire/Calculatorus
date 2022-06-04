package stancempire.calculatorus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CalculatorusLauncher extends Application
{
	
	public static void main(String[] args)
	{
		
		launch(args);
		
	}

	@Override
	public void start(Stage stage) throws Exception
	{

		Pane pane = new Pane();
		Button button = new Button();
		Text text = new Text();
		text.setFont(new Font(18));
		text.setText("Hello " + System.getProperty("os.name") + " user!");
		text.relocate(0, 0);
		text.setVisible(false);
		
		button.setText("Hello");
		
		button.setOnAction(action ->
		{
			
			text.setVisible(true);
			button.setVisible(false);
			
		});
		
		button.setPrefHeight(30);
		button.setPrefWidth(60);
		pane.getChildren().add(button);
		pane.getChildren().add(text);

		stage.setScene(new Scene(pane, 200, 100));
		stage.setResizable(false);
		stage.show();
		
	}
	
}
