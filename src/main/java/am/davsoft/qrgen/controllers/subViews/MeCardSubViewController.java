package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.barcodegenerator.builder.barcodedata.MeCardBarcodeDataBuilder;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class MeCardSubViewController extends SubViewController {
    @FXML private JFXTextField txtFieldName, txtFieldCompany, txtFieldPhone, txtFieldWebsite, txtFieldEmail, txtFieldAddress, txtFieldNote;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtFieldName, txtFieldPhone);
        txtFieldName.setValidators(ValidatorFactory.createRequiredFieldValidator("Name is required!"));
        txtFieldPhone.setValidators(ValidatorFactory.createRequiredFieldValidator("Phone is required!"));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtFieldName.setText("");
        txtFieldCompany.setText("");
        txtFieldPhone.setText("");
        txtFieldWebsite.setText("");
        txtFieldEmail.setText("");
        txtFieldAddress.setText("");
        txtFieldNote.setText("");
    }

    @Override
    public BarcodeData getQRData() {
        return new MeCardBarcodeDataBuilder()
                .withName(txtFieldName.getText())
                .withCompany(txtFieldCompany.getText())
                .withPhone(txtFieldPhone.getText())
                .withWebsite(txtFieldWebsite.getText())
                .withEmail(txtFieldEmail.getText())
                .withAddress(txtFieldAddress.getText())
                .withNote(txtFieldNote.getText())
                .buildData();
    }
}
