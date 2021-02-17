package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.barcodegenerator.builder.barcodedata.SMSBarcodeDataBuilder;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class SMSSubViewController extends SubViewController {
    @FXML private JFXTextField txtFieldPhoneNumber;
    @FXML private JFXTextArea txtAreaSMSText;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtFieldPhoneNumber, txtAreaSMSText);
        txtFieldPhoneNumber.setValidators(ValidatorFactory.createRequiredFieldValidator("Phone number is required!"));
        txtAreaSMSText.setValidators(ValidatorFactory.createRequiredFieldValidator("SMS text is required!"));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtFieldPhoneNumber.setText("");
        txtAreaSMSText.setText("");
    }

    @Override
    public BarcodeData getQRData() {
        return new SMSBarcodeDataBuilder()
                .withPhone(txtFieldPhoneNumber.getText())
                .withMessage(txtAreaSMSText.getText())
                .buildData();
    }
}
