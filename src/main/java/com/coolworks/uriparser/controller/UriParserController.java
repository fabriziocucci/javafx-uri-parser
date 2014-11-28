package com.coolworks.uriparser.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import org.controlsfx.dialog.ExceptionDialog;

public class UriParserController implements Initializable {

	@FXML private Button parseButton;
	@FXML private Button resetButton;
	@FXML private TextField uriTextField;
	@FXML private TextField uriSchemeTextField;
	@FXML private TextField uriAuthorityTextField;
	@FXML private TextField uriPathTextField;
	@FXML private TextField uriQueryTextField;
	@FXML private TextField uriFragmentTextField;

	/**
	 * This {@link SimpleBooleanProperty} is used to disable the parsing controls when something has been just parsed.
	 */
	private final BooleanProperty isCurrentUriParsed = new SimpleBooleanProperty(false);

	/**
	 * {@link KeyCodeCombination} representing the ENTER button and no other combination involving it.
	 */
	private final KeyCodeCombination enterCombination = new KeyCodeCombination(KeyCode.ENTER);

	/**
	 * {@link KeyCodeCombination} representing the classic Windows style shortcut for copying something onto the system clipboard.
	 */
	private final KeyCodeCombination copyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// parse URI whenever the "parseButton" is fired
		parseButton.setOnAction(event -> parseCurrentUri());

		// reset view whenever the "resetButton" is fired
		resetButton.setOnAction(event -> resetView());

		// disable "parseButton" when something has been just parsed
		parseButton.disableProperty().bind(isCurrentUriParsed);

		// disable "uriTextField" when something has been just parsed
		uriTextField.disableProperty().bind(isCurrentUriParsed);

		// let "parseButton" handle the case of ENTER key pressed
		parseButton.setOnKeyPressed(this::parseCurrentUriIfEnterPressed);
		// let "clearButton" handle the case of ENTER key pressed
		resetButton.setOnKeyPressed(this::resetViewIfEnterPressed);
		// let "uriTextField" handle the case of ENTER key pressed
		uriTextField.setOnKeyPressed(this::parseCurrentUriIfEnterPressed);

		// let "uriSchemeTextField" handle the case of CTRL+C combination pressed
		uriSchemeTextField.setOnKeyPressed(this::copyToClipboardIfCopyCombinationIsPressed);
		// let "uriAuthorityTextField" handle the case of CTRL+C combination pressed
		uriAuthorityTextField.setOnKeyPressed(this::copyToClipboardIfCopyCombinationIsPressed);
		// let "uriPathTextField" handle the case of CTRL+C combination pressed
		uriPathTextField.setOnKeyPressed(this::copyToClipboardIfCopyCombinationIsPressed);
		// let "uriQueryTextField" handle the case of CTRL+C combination pressed
		uriQueryTextField.setOnKeyPressed(this::copyToClipboardIfCopyCombinationIsPressed);
		// let "uriFragmentTextField" handle the case of CTRL+C combination pressed
		uriFragmentTextField.setOnKeyPressed(this::copyToClipboardIfCopyCombinationIsPressed);

	}

	private void parseCurrentUri() {
		try {
			String uriAsString = uriTextField.getText();
			URI uri = new URI(uriAsString);
			updateUriComponents(uri);
			isCurrentUriParsed.set(true);
		} catch (URISyntaxException e) {
			showExceptionDialog(e);
		}
	}

	private void parseCurrentUriIfEnterPressed(KeyEvent keyEvent) {
		if (enterCombination.match(keyEvent)) {
			parseButton.fire();
		}
	}

	private void resetView() {
		uriTextField.clear();
		clearUriComponents();
		isCurrentUriParsed.set(false);
		uriTextField.requestFocus();
	}

	private void resetViewIfEnterPressed(KeyEvent keyEvent) {
		if (enterCombination.match(keyEvent)) {
			resetButton.fire();
		}
	}

	private void updateUriComponents(URI uri) {
		Optional.ofNullable(uri.getScheme()).ifPresent(uriSchemeTextField::setText);
		Optional.ofNullable(uri.getAuthority()).ifPresent(uriAuthorityTextField::setText);
		Optional.ofNullable(uri.getPath()).ifPresent(uriPathTextField::setText);
		Optional.ofNullable(uri.getQuery()).ifPresent(uriQueryTextField::setText);
		Optional.ofNullable(uri.getFragment()).ifPresent(uriFragmentTextField::setText);
	}

	private void clearUriComponents() {
		uriSchemeTextField.clear();
		uriAuthorityTextField.clear();
		uriPathTextField.clear();
		uriQueryTextField.clear();
		uriFragmentTextField.clear();
	}

	private void copyToClipboardIfCopyCombinationIsPressed(KeyEvent keyEvent) {
		if (copyCombination.match(keyEvent)) {
			Clipboard systemClipboard = Clipboard.getSystemClipboard();
			ClipboardContent clipboardContent = new ClipboardContent();
			TextField textField = (TextField) keyEvent.getSource();
			clipboardContent.putString(textField.getText());
			systemClipboard.setContent(clipboardContent);
		}
	}

	private void showExceptionDialog(URISyntaxException e) {
		ExceptionDialog exceptionDialog = new ExceptionDialog(e);
		exceptionDialog.setTitle("Parsing Exception");
		exceptionDialog.setResizable(false);
		exceptionDialog.showAndWait();
	}

}