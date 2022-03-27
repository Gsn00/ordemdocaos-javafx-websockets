package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class NoteController implements Initializable
{
	@FXML
	private ScrollPane pane;
	@FXML
	private HBox hbox;
	@FXML
	private Button btSave;
	@FXML
	private Button btExit;
	@FXML
	private TextArea txtArea;

	private static File dir = new File("C:\\ordemdocaos\\config");
	private static File note = new File(dir + "\\note.txt");

	public void onMouseEntered()
	{
		btSave.getScene().setCursor(Cursor.HAND);
	}

	public void onMouseExited()
	{
		btSave.getScene().setCursor(Cursor.DEFAULT);
	}

	public void onBtSave()
	{
		writeNote(txtArea.getText());
	}

	public void onBtExit()
	{
		writeNote(txtArea.getText());
		Stage stage = (Stage) btSave.getScene().getWindow();
		stage.close();
	}

	private void writeNote(String text)
	{
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(note)))
		{
			bw.write(text);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String readNote()
	{
		try (BufferedReader br = new BufferedReader(new FileReader(note)))
		{
			String text = "";
			String line = br.readLine();
			while (line != null)
			{
				text += line + "\n";
				line = br.readLine();
			}
			return text;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		if (!note.exists())
		{
			try
			{
				dir.mkdirs();
				note.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		txtArea.setText(readNote());
		
		hbox.setOnMousePressed(event -> {
			Stage stage = (Stage) hbox.getScene().getWindow();
			Point2D offset = new Point2D(event.getScreenX() - stage.getX(), event.getScreenY() - stage.getY());
			stage.setUserData(offset);
		});
		
		hbox.setOnMouseDragged(event -> {
			Stage stage = (Stage) hbox.getScene().getWindow();
			Point2D offset = (Point2D) stage.getUserData();
			double X = event.getScreenX() - offset.getX();
			double Y = event.getScreenY() - offset.getY();
			stage.setX(X);
			stage.setY(Y);
		});
	}
}
