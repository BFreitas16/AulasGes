package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.handlers.IClassServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.ActivateClassController;
import presentation.fx.inputcontroller.CreateClassController;
import presentation.fx.model.ActivateClassModel;
import presentation.fx.model.CreateClassModel;

public class Startup extends Application {
    
	private static IClassServiceRemote classService;

	@Override 
    public void start(Stage stage) throws IOException {
    
		// This line to resolve keys against Bundle.properties
		// ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
        ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));
		
        BorderPane root = new BorderPane();
    	FXMLLoader createClassLoader = new FXMLLoader(getClass().getResource("/fxml/createClass.fxml"), i18nBundle);
    	FXMLLoader activateClassLoader = new FXMLLoader(getClass().getResource("/fxml/activateClass.fxml"), i18nBundle);
    	
    	root.setLeft(createClassLoader.load());
    	root.setRight(activateClassLoader.load());
    	CreateClassController createClassController = createClassLoader.getController(); 
    	ActivateClassController activateClassController = activateClassLoader.getController(); 
    	
    	CreateClassModel createClassModel = new CreateClassModel(classService);
    	ActivateClassModel activateClassModel = new ActivateClassModel(classService);
    	createClassController.setModel(createClassModel);
    	createClassController.setClassService(classService);
    	createClassController.setI18NBundle(i18nBundle);
    	activateClassController.setModel(activateClassModel);
    	activateClassController.setClassService(classService);
    	activateClassController.setI18NBundle(i18nBundle);
    	
        Scene scene = new Scene(root, 1400, 450);
       
        stage.setTitle(i18nBundle.getString("application.title"));
        stage.setScene(scene);
        stage.show();
    }
	
	public static void startGUI(IClassServiceRemote classService) {
		Startup.classService = classService;
        launch();
	}
}
