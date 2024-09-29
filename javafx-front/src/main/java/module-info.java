module JAVAFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires okhttp3;
	requires com.fasterxml.jackson.databind;
	
	opens swe2040ProjectGUI to javafx.base, javafx.graphics, javafx.fxml, com.fasterxml.jackson.databind, okhttp3;
}
