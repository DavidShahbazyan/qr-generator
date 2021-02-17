package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.barcodegenerator.builder.barcodedata.WiFiNetworkBarcodeDataBuilder;
import am.davsoft.barcodegenerator.impl.WiFiEncryptionTypeEnum;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class WiFiNetworkSubViewController extends SubViewController {
    @FXML private JFXTextField txtFieldSSID;
    @FXML private JFXPasswordField passFieldPassword;
    @FXML private JFXCheckBox checkBoxHiddenNetwork;
    @FXML private JFXComboBox<WiFiEncryptionTypeEnum> comboBoxAuthType;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtFieldSSID, passFieldPassword);
        comboBoxAuthType.setItems(FXCollections.observableArrayList(WiFiEncryptionTypeEnum.values()));
        comboBoxAuthType.getSelectionModel().select(WiFiEncryptionTypeEnum.WPA_WPA2);
        passFieldPassword.visibleProperty().bind(Bindings.notEqual(comboBoxAuthType.getSelectionModel().selectedItemProperty(), WiFiEncryptionTypeEnum.NONE));
        txtFieldSSID.setValidators(ValidatorFactory.createRequiredFieldValidator("SSID is required!"));
        passFieldPassword.setValidators(ValidatorFactory.createConditionalRequiredFieldValidator(() -> passFieldPassword.isVisible(), "Password is required!"));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtFieldSSID.setText("");
        passFieldPassword.setText("");
        checkBoxHiddenNetwork.setSelected(false);
        comboBoxAuthType.getSelectionModel().select(WiFiEncryptionTypeEnum.WPA_WPA2);
    }

    @Override
    public BarcodeData getQRData() {
        return new WiFiNetworkBarcodeDataBuilder()
                .withSsid(txtFieldSSID.getText())
                .withEncryptionType(comboBoxAuthType.getValue())
                .withPass(passFieldPassword.getText())
                .withHidden(checkBoxHiddenNetwork.isSelected())
                .buildData();
    }
}
