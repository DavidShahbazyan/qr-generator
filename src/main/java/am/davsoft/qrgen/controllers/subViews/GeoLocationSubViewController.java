package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.barcodegenerator.builder.barcodedata.GoogleGeoLocationBarcodeDataBuilder;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class GeoLocationSubViewController extends SubViewController {
    @FXML private JFXTextField txtFieldLatitude, txtFieldLongitude, txtFieldQuery;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtFieldLatitude, txtFieldLongitude);
        txtFieldLatitude.setValidators(ValidatorFactory.createRequiredFieldValidator("Latitude is required!"));
        txtFieldLongitude.setValidators(ValidatorFactory.createRequiredFieldValidator("Longitude is required!"));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtFieldLatitude.setText("");
        txtFieldLongitude.setText("");
        txtFieldQuery.setText("");
    }

    @Override
    public BarcodeData getQRData() {
        return new GoogleGeoLocationBarcodeDataBuilder()
                .withLatitude(txtFieldLatitude.getText())
                .withLongitude(txtFieldLongitude.getText())
                .withQuery(txtFieldQuery.getText())
                .buildData();
    }
}
