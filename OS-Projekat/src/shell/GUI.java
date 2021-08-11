package shell;

import java.io.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	private static String textToShow;
	private static TextArea top = new TextArea();
	private static TextField bottom = new TextField();
	private PipedInputStream inp = new PipedInputStream();
	private PipedOutputStream out = new PipedOutputStream();
	private int len = 0;

	public static void setSBClear() {
		textToShow = "";
		bottom.clear();
	}

	public static void setHelp() {
		System.out.println("g");
		String help;

		help = "DIR \t\t\t Displays a list of files and subdirectories in a directory.\n";
		help += "GOTO \t\t Changes dir.\n";
		help += "MD \t\t\t Make dir.\n";
		help += "DD \t\t\t Delete dir.\n";
		help += "RD \t\t\t Rename dir.\n";
		help += "LOAD \t\t\t Load new procces and send him in the background. \n";
		help += "LSPR \t\t List of processes.\n";
		help += "TRMPR \t\t Terminate process.\n";
		help += "BLPR \t\t Blocks process.\n";
		help += "UBLPR \t\t Unblocks process.\n";
		help += "CLEAR \t\t Clears terminal.\n";
		help += "EXIT \t\t\t Closes program.\n";

		textToShow += help;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		VBox root = new VBox();
		top = new TextArea();
		bottom = new TextField();
		inp.connect(out);
		textToShow = "";

		root.getChildren().setAll(top, bottom);

		top.setMinSize(1000, 550);
		top.setEditable(false);
		// top.setStyle("-fx-text-fill:red;");

		bottom.setMinSize(1000, 50);
		bottom.setOnAction(e -> {
			try {
				byte niz[] = bottom.getText().getBytes();
				out.write(niz);
				len = niz.length;

				ShellCommands.readACommand(inp, len);

				textToShow += ">" + bottom.getText() + "\n";
				String st = ShellCommands.returnCommand();
				textToShow += st;

				top.setText(textToShow);
				bottom.clear();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		bottom.setOnKeyPressed((e) -> {
			if (e.getCode().equals(KeyCode.UP)) {
				String last = ShellCommands.previous();
				if (!last.equals("")) {
					bottom.setText(last);
				}
			} else if (e.getCode().equals(KeyCode.DOWN)) {
				String next = ShellCommands.next();
				if (!next.equals("")) {
					bottom.setText(next);
				}
			}
		});

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Console");
		primaryStage.show();

	}

	public static void main(String[] args) throws IOException {
		launch(args);
	}
}