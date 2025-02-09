package csc325.collectionsproject;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import csc325.collectionsproject.controller.FirebaseWriter;
import csc325.collectionsproject.model.CollectionItem;

import csc325.collectionsproject.model.FirestoreContext;
import csc325.collectionsproject.model.ResourceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This is the main application class for Collection Perfection
 * This clsass initilizes Firebase Firestore, Firebase Authenitcation, and the GUI
 * @author Selin Saracoglu, Aubrey Morra, Serena D'Avanzo
 */

public class CollectionsApplication extends Application {
    public static Scene scene;    // Firestore reference
    public static Firestore fstoreDB;
    public static FirebaseAuth fauth;
    private final FirestoreContext contxtFirebase = new FirestoreContext();

    /**
     * Starts the JavaFX application
     * @param stage the primary stage for the application
     * @throws IOException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        //Initialize Firestore, authorization, db
        fstoreDB = contxtFirebase.firebase();
        fauth = FirebaseAuth.getInstance();
        //preload icon resources so that GUI pages can load without issue
        ResourceManager.initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(CollectionsApplication.class.getResource("registration-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 1260, 840);

        stage.setMaximized(true);
        stage.setTitle("Collection Perfection");
        Image icon = ResourceManager.getImage("orange_heart.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Sets the root to the specified FXML file
     * @param fxml
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file and returns it root node
     * @param fxml the name of the FXML file to load
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CollectionsApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Static method to get the Firestore instance.
     * @return the Firestore instance.
     */
    public static Firestore getFirestoreDB() {
        if (fstoreDB == null) {
            throw new IllegalStateException("Firestore has not been initialized.");
            //Shouldnt ever trigger as fstoreDB is initialized immediately in start()
        }
        return fstoreDB;
    }

    public static void main(String[] args) {launch();}




}