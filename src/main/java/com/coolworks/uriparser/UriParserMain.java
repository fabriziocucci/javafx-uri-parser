package com.coolworks.uriparser;

import static com.coolworks.uriparser.UriParserConstants.STAGE_TITLE;
import static com.coolworks.uriparser.UriParserConstants.URI_PARSER_VIEW_FXML_ABSOLUTE_NAME;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.coolworks.uriparser.controller.UriParserController;

public class UriParserMain extends Application {

	public static void main(String[] args) {
		launch(UriParserMain.class, args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = getRootNodeFromFxmlFile();
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle(STAGE_TITLE);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Parent getRootNodeFromFxmlFile() throws IOException {
		URL uriParserViewFxmlUrl = getClass().getResource(URI_PARSER_VIEW_FXML_ABSOLUTE_NAME);
		FXMLLoader fxmlLoader = new FXMLLoader(uriParserViewFxmlUrl);
		fxmlLoader.setController(new UriParserController());
		return fxmlLoader.load();
	}

}
