package com.coolworks.uriparser;

import static com.coolworks.uriparser.UriParserConstants.*;
import static org.hamcrest.CoreMatchers.is;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import com.coolworks.uriparser.controller.UriParserController;

public class UriParserTest extends GuiTest {
	
	private static Robot robot;

	@BeforeClass
	public static void beforeTest() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void parseEmptyUri() throws URISyntaxException {
				
		click(PARSE_BUTTON_ID_SELECTOR);
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_SCHEME_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_AUTHORITY_TEXT_FIELD_ID_SELECTOR), uriAuthorityTextField -> uriAuthorityTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_PATH_TEXT_FIELD_ID_SELECTOR), uriPathTextField -> uriPathTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_QUERY_TEXT_FIELD_ID_SELECTOR), uriQueryTextField -> uriQueryTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_FRAGMENT_TEXT_FIELD_ID_SELECTOR), uriFragmentTextField -> uriFragmentTextField.getText().isEmpty());
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.isDisabled());
		waitUntil(GuiTest.<Button> find(UriParserConstants.PARSE_BUTTON_ID_SELECTOR), parseButton -> parseButton.isDisabled());
		
	}
	
	@Test
	public void parseValidUri() throws URISyntaxException {
				
		String validUriAsString = "scheme://authority/path?query#fragment";
		URI validUri = new URI(validUriAsString);
		
		copyToClipboard(validUriAsString);
		pasteFromClipboard(URI_TEXT_FIELD_ID_SELECTOR);
		click(PARSE_BUTTON_ID_SELECTOR);
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriTextField -> uriTextField.getText().equals(validUriAsString));
		waitUntil(GuiTest.<TextField> find(URI_SCHEME_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().equals(validUri.getScheme()));
		waitUntil(GuiTest.<TextField> find(URI_AUTHORITY_TEXT_FIELD_ID_SELECTOR), uriAuthorityTextField -> uriAuthorityTextField.getText().equals(validUri.getAuthority()));
		waitUntil(GuiTest.<TextField> find(URI_PATH_TEXT_FIELD_ID_SELECTOR), uriPathTextField -> uriPathTextField.getText().equals(validUri.getPath()));
		waitUntil(GuiTest.<TextField> find(URI_QUERY_TEXT_FIELD_ID_SELECTOR), uriQueryTextField -> uriQueryTextField.getText().equals(validUri.getQuery()));
		waitUntil(GuiTest.<TextField> find(URI_FRAGMENT_TEXT_FIELD_ID_SELECTOR), uriFragmentTextField -> uriFragmentTextField.getText().equals(validUri.getFragment()));
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.isDisabled());
		waitUntil(GuiTest.<Button> find(UriParserConstants.PARSE_BUTTON_ID_SELECTOR), parseButton -> parseButton.isDisabled());
		
	}

	@Test
	public void parseInvalidUri() throws URISyntaxException {
				
		String invalidUriAsString = "\\";
		
		copyToClipboard(invalidUriAsString);
		pasteFromClipboard(URI_TEXT_FIELD_ID_SELECTOR);
		click(PARSE_BUTTON_ID_SELECTOR);
		
		// main scene and exception dialog should be both visible
		GuiTest.<Integer> waitUntil(getWindows()::size, is(2));
		// close exception dialog
		closeCurrentWindow();
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriTextField -> uriTextField.getText().equals(invalidUriAsString));
		waitUntil(GuiTest.<TextField> find(URI_SCHEME_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_AUTHORITY_TEXT_FIELD_ID_SELECTOR), uriAuthorityTextField -> uriAuthorityTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_PATH_TEXT_FIELD_ID_SELECTOR), uriPathTextField -> uriPathTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_QUERY_TEXT_FIELD_ID_SELECTOR), uriQueryTextField -> uriQueryTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_FRAGMENT_TEXT_FIELD_ID_SELECTOR), uriFragmentTextField -> uriFragmentTextField.getText().isEmpty());
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> !uriSchemeTextField.isDisabled());
		waitUntil(GuiTest.<Button> find(UriParserConstants.PARSE_BUTTON_ID_SELECTOR), parseButton -> !parseButton.isDisabled());
		
	}
	
	@Test
	public void resetAfterSuccesfullParsing() throws URISyntaxException {
		
		parseValidUri();
		click(RESET_BUTTON_ID_SELECTOR);
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_SCHEME_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_AUTHORITY_TEXT_FIELD_ID_SELECTOR), uriAuthorityTextField -> uriAuthorityTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_PATH_TEXT_FIELD_ID_SELECTOR), uriPathTextField -> uriPathTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_QUERY_TEXT_FIELD_ID_SELECTOR), uriQueryTextField -> uriQueryTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_FRAGMENT_TEXT_FIELD_ID_SELECTOR), uriFragmentTextField -> uriFragmentTextField.getText().isEmpty());
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> !uriSchemeTextField.isDisabled());
		waitUntil(GuiTest.<Button> find(UriParserConstants.PARSE_BUTTON_ID_SELECTOR), parseButton -> !parseButton.isDisabled());
		
	}
	
	@Test
	public void resetAfterUnsuccesfullParsing() throws URISyntaxException {
		
		parseInvalidUri();
		click(RESET_BUTTON_ID_SELECTOR);
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_SCHEME_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> uriSchemeTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_AUTHORITY_TEXT_FIELD_ID_SELECTOR), uriAuthorityTextField -> uriAuthorityTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_PATH_TEXT_FIELD_ID_SELECTOR), uriPathTextField -> uriPathTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_QUERY_TEXT_FIELD_ID_SELECTOR), uriQueryTextField -> uriQueryTextField.getText().isEmpty());
		waitUntil(GuiTest.<TextField> find(URI_FRAGMENT_TEXT_FIELD_ID_SELECTOR), uriFragmentTextField -> uriFragmentTextField.getText().isEmpty());
		
		waitUntil(GuiTest.<TextField> find(URI_TEXT_FIELD_ID_SELECTOR), uriSchemeTextField -> !uriSchemeTextField.isDisabled());
		waitUntil(GuiTest.<Button> find(UriParserConstants.PARSE_BUTTON_ID_SELECTOR), parseButton -> !parseButton.isDisabled());
		
	}
	
	@Override
	protected Parent getRootNode() {
		try {
			URL uriParserViewFxmlUrl = getClass().getResource(URI_PARSER_VIEW_FXML_ABSOLUTE_NAME);
			FXMLLoader fxmlLoader = new FXMLLoader(uriParserViewFxmlUrl);
			fxmlLoader.setController(new UriParserController());
			return fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void copyToClipboard(String string) {
		Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    StringSelection stringSelection = new StringSelection(string);
	    systemClipboard.setContents(stringSelection, stringSelection);
	}
	
	private void pasteFromClipboard(String query) {
		click(query);
		robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
}
