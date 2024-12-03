package csc325.collectionsproject.controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import csc325.collectionsproject.CollectionsApplication;
import csc325.collectionsproject.model.User;
import csc325.collectionsproject.model.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class CollectionViewController {
    @FXML
    private Button addItemBtn, newCollectionBtn,profileBtn, addItemInGridBtn;

    @FXML
    private GridPane itemGrid;

    private int row = 0;
    private int column = 0;




    @FXML
    void addNewItem(ActionEvent event) throws IOException {
        //addItem(String imageUrl, String labelText); commented out while adding component logic is incomplete
        //dont forget to remove gridlines from gridpane
        switchToAddItemView();
        //addItem("", "Added item!");
    }

    public void initialize() {
        UserSession session = UserSession.getInstance();

    }

    //currently copied from getCollectionName in AddItemController
 public String getDocument() throws ExecutionException, InterruptedException {
     try {
         // Use the singleton instance to get the active username
         UserSession active = UserSession.getInstance();
         String username = active.getLoggedInUser().getUsername(); // Retrieve the username

         // Navigate to the user's "Collections" sub-collection
         CollectionReference collectionsRef = CollectionsApplication.fstoreDB.collection("Users")
                 .document(username)
                 .collection("Collections");

         // Get all documents in the "Collections" sub-collection
         ApiFuture<QuerySnapshot> future = collectionsRef.get();
         QuerySnapshot querySnapshot = future.get();

         List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
         for (QueryDocumentSnapshot document : documents) {
             String collectionId = document.getId(); // Get the document ID
             System.out.println("Collection ID: " + collectionId);

             // Optionally, retrieve specific fields from the document
             String collectionTitle = document.getString("Collection Title");
             System.out.println("Collection Title: " + collectionTitle);
             return collectionTitle;
         }
     } catch (ExecutionException | InterruptedException e) {
         e.printStackTrace();
     }
     return null;
 }

    //Test method linked to addItemInGridBtn, change this back to addNewItem() once that part works
    @FXML
    void addNewItemTest(ActionEvent event) {
        addItem("", "Added item!");
    }

    @FXML
    public void switchToAddItemView() throws IOException {
        CollectionsApplication.setRoot("add-item-view");
    }

    @FXML
    public void switchToProfileView() throws IOException {
        CollectionsApplication.setRoot("profile-view");
    }

    @FXML
    public void switchToCreateCollectionView() throws IOException {
        CollectionsApplication.setRoot("create-collection-view");
    }

    //just pass a CollectionItem here when it works, or we can get the data from the database? whats easier
    //collection view grid logic uwu happy thanksgiving

    public void addItem(String imageUrl, String itemName) {
        try {
            //switchToAddItemView();
            // Load the FXML for the item component
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/csc325/collectionsproject/components/item-component.fxml"));
            Node itemNode = loader.load();

            // Get the controller of the item component
            ItemComponentController newItemController = loader.getController();
            newItemController.setImage(imageUrl != null ? imageUrl : "/csc325/collectionsproject/imgs/pipermelonart.png");
            newItemController.setLabel(itemName);
            //newItemController.setLabel(labelText != null ? labelText : "Collection Item Here");
            newItemController.setLabel(labelText);

            // Add the item to the grid at the next available position
            itemGrid.add(itemNode, column, row);
            //if column > 5, reset, increment row, add button to next

            // Update the grid indices, replace addItemInGridBtn
            column++;
            if (column >= 5) { // 5 items per row
                column = 0;
                row++;
            }
            itemGrid.getChildren().remove(addItemInGridBtn);
            itemGrid.add(addItemInGridBtn, column, row);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Retrieve the collection name from Firestore

}
