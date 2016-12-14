package printing;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GVFilePrinter extends Application implements Printer {

    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    	
//    	String command="C:\\\"Program Files (x86)\"\\Graphviz2.38\\bin\\dot.exe -Tpng -o C:\\Users\\bubulkr\\git\\CSSE374\\Documents\\output.png "+path;
//    	Runtime rt = Runtime.getRuntime();
//    	Process pr = rt.exec(command);
    	Parameters para=this.getParameters();
    	
    	ProcessBuilder pb =new ProcessBuilder("dot","-Tpng","-o", "C:\\Users\\bubulkr\\git\\CSSE374\\Documents\\output.png");
    	Process p = pb.start();
    	
        primaryStage.setTitle("Title");
        Group root = new Group();
        Scene scene = new Scene(root, 600, 330, Color.WHITE);
        
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        
        final ImageView imv = new ImageView();
        final Image image2 = new Image(GVFilePrinter.class.getResourceAsStream("C:\\Users\\bubulkr\\git\\CSSE374\\Documents\\output.png"));
        imv.setImage(image2);

        final HBox pictureRegion = new HBox();
        
        pictureRegion.getChildren().add(imv);
        gridpane.add(pictureRegion, 1, 1);
        
        
        root.getChildren().add(gridpane);        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	@Override
	public void print(String path) {
		String[] args = {path};
		main(args);
	}
}
