package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.BarCodeData;
import am.davsoft.barcodegenerator.impl.BarCodeDataText;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class TextSubViewController extends SubViewController {
    @FXML private JFXTextArea txtAreaText;
    @FXML private Label lblSymbolsLeft;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtAreaText);
        calculateSymbolsLeft();
        txtAreaText.textProperty().addListener(observable -> calculateSymbolsLeft());
        txtAreaText.setValidators(ValidatorFactory.createRequiredFieldValidator("Text is required!"));
    }

    private void calculateSymbolsLeft() {
        lblSymbolsLeft.setText(String.valueOf(getParentViewController().getQrGenerator().getMaxCharsCount() - txtAreaText.getText().length()));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtAreaText.setText("");
    }

    @Override
    public BarCodeData getQRData() {
        return BarCodeDataText.newInstance().withText(txtAreaText.getText());
    }
}
