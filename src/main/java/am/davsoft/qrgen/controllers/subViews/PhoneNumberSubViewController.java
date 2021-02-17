package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.barcodegenerator.builder.barcodedata.PhoneNumberBarcodeDataBuilder;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class PhoneNumberSubViewController extends SubViewController {
    @FXML private JFXTextField txtFieldPhoneNumber;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtFieldPhoneNumber);
        txtFieldPhoneNumber.setValidators(ValidatorFactory.createRequiredFieldValidator("Phone number is required!"));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtFieldPhoneNumber.setText("");
    }

    @Override
    public BarcodeData getQRData() {
        return new PhoneNumberBarcodeDataBuilder()
                .withPhone(txtFieldPhoneNumber.getText())
                .buildData();
    }
}
