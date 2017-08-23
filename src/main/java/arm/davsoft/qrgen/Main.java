package arm.davsoft.qrgen;

import arm.davsoft.qrgen.api.QRType;
import arm.davsoft.qrgen.impl.QRTypeText;
import arm.davsoft.qrgen.impl.QRTypeWiFiNetwork;
import arm.davsoft.qrgen.util.QRGenerator;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * @author David.Shahbazyan
 * @since Dec 12, 2016
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ObjectProperty<WritableImage> img = new SimpleObjectProperty<>();

        Label title = new Label("QR-Gen");
        title.setFont(new Font(40));
        title.setTextAlignment(TextAlignment.CENTER);

        int maxCharsCount = 2950;
        Label charsLeft = new Label("Characters left: " + maxCharsCount);

        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                charsLeft.setText("Characters left: " + (maxCharsCount - newValue.length()));
                if (newValue.length() > maxCharsCount) {
                    textArea.setText(newValue.substring(0, maxCharsCount));
                }
            }
        });
        VBox.setVgrow(textArea, Priority.ALWAYS);

        Label resultTitle = new Label("Results");

        ImageView imageView = new ImageView();
        imageView.imageProperty().bind(img);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        Button btnSaveImage = new Button("Save to PC");
        btnSaveImage.setStyle("-fx-background-color: #00A; -fx-text-fill: #FFF;");
        btnSaveImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            fileChooser.setInitialFileName("qr.gif");
            fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Images", "*.GIF, *.gif"));
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(img.get(), null), "gif", file);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

        VBox resultBlock = new VBox(5, resultTitle, imageView, btnSaveImage);
        resultBlock.setAlignment(Pos.TOP_CENTER);
        resultBlock.visibleProperty().bind(img.isNotNull());
//        resultBlock.managedProperty().bind(img.isNotNull());

        Button btnReset = new Button("Reset");
        btnReset.setStyle("-fx-background-color: #A00; -fx-text-fill: #FFF;");
        btnReset.setOnAction(event -> {
            img.setValue(null);
            textArea.clear();
        });

        Button btnStart = new Button("Start");
        btnStart.setStyle("-fx-background-color: #0A0; -fx-text-fill: #FFF;");
        btnStart.setOnAction(event -> {
            if (!textArea.getText().isEmpty()) {
                try {
                    QRType qrType = new QRTypeText().setText(textArea.getText());
                    img.set(SwingFXUtils.toFXImage(QRGenerator.generateImage(qrType), null));
                } catch (WriterException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

        HBox btnBox = new HBox(5, btnReset, btnStart);
        btnBox.setAlignment(Pos.TOP_CENTER);

        VBox formBlock = new VBox(5, resultBlock, charsLeft, textArea, btnBox);
        formBlock.setAlignment(Pos.TOP_CENTER);

        HBox content = new HBox(5, resultBlock, formBlock);
        content.setAlignment(Pos.TOP_CENTER);

        VBox root = new VBox(5, title, content);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(15));

        primaryStage.setTitle("QR-Gen");
        primaryStage.getIcons().setAll(new Image("images/icons/png/16x16.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.requestFocus();
    }

}
