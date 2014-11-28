package com.coolworks.uriparser;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UriParserConstants {

	private UriParserConstants() { }
	
	/**
	 * The absolute name of the FXML file used to load the hierarchical scene graph.
	 */
	public static final String URI_PARSER_VIEW_FXML_ABSOLUTE_NAME = "/com/coolworks/uriparser/view/UriParserView.fxml";
	
	/**
	 * The title associated to the primary stage of this application.
	 */
	public static final String STAGE_TITLE = "URI Parser";
	
	/**
	 * The id selector of the {@link Button} associated to the parse action.
	 */
	public static final String PARSE_BUTTON_ID_SELECTOR = "#parse-button";
	
	/**
	 * The id selector of the {@link Button} associated to the reset action.
	 */
	public static final String RESET_BUTTON_ID_SELECTOR = "#reset-button";
	
	/**
	 * The id selector of the {@link TextField} associated to the URI.
	 */
	public static final String URI_TEXT_FIELD_ID_SELECTOR = "#uri-text-field";
	
	/**
	 * The id selector of the {@link TextField} associated to the URI scheme.
	 */
	public static final String URI_SCHEME_TEXT_FIELD_ID_SELECTOR = "#uri-scheme-text-field";
	
	/**
	 * The id selector of the {@link TextField} associated to the URI authority.
	 */
	public static final String URI_AUTHORITY_TEXT_FIELD_ID_SELECTOR = "#uri-authority-text-field";
	
	/**
	 * The id selector of the {@link TextField} associated to the URI path.
	 */
	public static final String URI_PATH_TEXT_FIELD_ID_SELECTOR = "#uri-path-text-field";
	
	/**
	 * The id selector of the {@link TextField} associated to the URI query.
	 */
	public static final String URI_QUERY_TEXT_FIELD_ID_SELECTOR = "#uri-query-text-field";
	
	/**
	 * The id selector of the {@link TextField} associated to the URI fragment.
	 */
	public static final String URI_FRAGMENT_TEXT_FIELD_ID_SELECTOR = "#uri-fragment-text-field";
	
}
