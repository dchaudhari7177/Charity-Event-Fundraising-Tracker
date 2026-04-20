package javafx.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Charity Tracker - JavaFX Application
 * Modern UI for Charity Event Management System
 */
public class CharityTrackerApp extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load main FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/fxml/main.fxml"));
        javafx.scene.layout.BorderPane root = loader.load();
        
        // Create scene
        Scene scene = new Scene(root, 1400, 900);
        
        // Load CSS stylesheet
        String css = getClass().getResource("/javafx/resources/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        // Configure primary stage
        primaryStage.setTitle("Charity Tracker - Event Management System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);
        
        // Set window icon (optional)
        try {
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/javafx/resources/icon.png")));
        } catch (Exception e) {
            // Icon not found, continue without it
        }
        
        // Show window
        primaryStage.show();
        
        System.out.println("✓ Charity Tracker JavaFX Application Started");
    }
    
    /**
     * Main entry point
     */
    public static void main(String[] args) {
        launch(args);
    }
}
