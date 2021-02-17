package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.barcodegenerator.builder.barcodedata.URLBarcodeDataBuilder;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class URLSubViewController extends SubViewController {
    @FXML private JFXTextField txtFieldURL;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtFieldURL);
        txtFieldURL.setValidators(ValidatorFactory.createRequiredFieldValidator("URL is required!"));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtFieldURL.setText("");
    }

    @Override
    public BarcodeData getQRData() {
        return new URLBarcodeDataBuilder()
                .withUrl(txtFieldURL.getText())
                .buildData();
    }
}
