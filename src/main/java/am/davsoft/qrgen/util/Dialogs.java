package am.davsoft.qrgen.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.File;

/**
 * @author David.Shahbazyan
 * @since Aug 30, 2018
 */
public final class Dialogs {
    private enum PopupType {INFORMATION, WARNING, CONFIRMATION, ERROR, NONE}

    private Dialogs() {}

    public static Window getParentWindow(Node node) {
        return node.getScene().getWindow();
    }

    public static File getUserHomeDir() { return new File(System.getProperty("user.home")); }

    public static JFXDialog createPopup(StackPane owner, String header, Node... body) {
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Label(header));
        JFXDialog dialog = new JFXDialog(owner, dialogLayout, JFXDialog.DialogTransition.CENTER);
        dialogLayout.setBody(body);
//        dialog.show();
        return dialog;
    }

    private static void showPopup(StackPane owner, String header, String content, PopupType popupType) {
        HBox container = new HBox();
        container.setSpacing(5);
        container.setAlignment(Pos.TOP_LEFT);
        JFXDialog dialog = createPopup(owner, header, container);

        Text text = new Text(content);

        JFXButton okButton = new JFXButton("Ok");
        okButton.setOnAction(event -> dialog.close());
        okButton.setFocusTraversable(false);
        okButton.setCursor(Cursor.HAND);

        JFXButton yesButton = new JFXButton("Yes");
        yesButton.setOnAction(event -> dialog.close());
        yesButton.setFocusTraversable(false);
        yesButton.setCursor(Cursor.HAND);

        JFXButton noButton = new JFXButton("No");
        noButton.setOnAction(event -> dialog.close());
        noButton.setFocusTraversable(false);
        noButton.setCursor(Cursor.HAND);

        JFXDialogLayout dialogLayout = (JFXDialogLayout) dialog.getContent();
        switch (popupType) {
            case INFORMATION:
                container.getChildren().setAll(new ImageView(new Image("images/icons/png/icon-info.png")), text);
                dialogLayout.setActions(okButton);
                break;
            case WARNING:
                container.getChildren().setAll(new ImageView(new Image("images/icons/png/icon-error.png")), text);
                dialogLayout.setActions(okButton);
                break;
            case CONFIRMATION:
                container.getChildren().setAll(new ImageView(new Image("images/icons/png/icon-help.png")), text);
                dialogLayout.setActions(noButton, yesButton);
                break;
            case ERROR:
                container.getChildren().setAll(new ImageView(new Image("images/icons/png/icon-high-risk.png")), text);
                dialogLayout.setActions(okButton);
                break;
            case NONE:
                container.getChildren().setAll(new ImageView(new Image("images/icons/png/icon-info.png")), text);
                dialogLayout.setActions(okButton);
                break;
        }
        dialog.show();
    }


    public static void showInfoPopup(StackPane ownerStackPane, String header, String content) {
        showPopup(ownerStackPane, header, content, PopupType.INFORMATION);
    }

    public static void showWarningPopup(StackPane ownerStackPane, String header, String content) {
        showPopup(ownerStackPane, header, content, PopupType.WARNING);
    }

    public static void showErrorDialog(StackPane ownerStackPane, String header, String content) {
        showPopup(ownerStackPane, header, content, PopupType.ERROR);
    }

    public static void showAboutAppDialog(StackPane ownerStackPane) {
        showInfoPopup(ownerStackPane, "About QR Generator", "The \"QR Generator\" application was developed by David Shahbazyan in 2018." +
                "\nThe program is completely intellectual property and belongs to its developer (David Shahbazyan)." +
                "\nEmail: d.shahbazyan@gmail.com");
    }
}
