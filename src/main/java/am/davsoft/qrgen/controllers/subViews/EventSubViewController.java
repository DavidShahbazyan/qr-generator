package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.barcodegenerator.builder.barcodedata.EventBarcodeDataBuilder;
import am.davsoft.qrgen.util.ValidatorFactory;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public class EventSubViewController extends SubViewController {
    @FXML private JFXTextField txtFieldTitle, txtFieldLocation;
    @FXML private JFXCheckBox checkBoxAllDayEvent;
    @FXML private JFXDatePicker datePickerStartDate, datePickerEndDate;
    @FXML private JFXTimePicker timePickerStartDate, timePickerEndDate;
    @FXML private JFXTextArea txtAreaDescription;
    @FXML private HBox hBoxStartDate, hBoxEndDate;

    @Override
    public void prepareForm() {
        super.prepareForm();
        super.initControlsForValidation(txtFieldTitle, txtFieldLocation, txtAreaDescription, datePickerStartDate, datePickerEndDate);
        checkBoxAllDayEvent.setSelected(true);
        datePickerStartDate.setValue(LocalDate.now());
        datePickerEndDate.setValue(LocalDate.now());
        timePickerStartDate.setValue(LocalTime.of(9, 0));
        timePickerEndDate.setValue(LocalTime.of(18, 0));
        timePickerStartDate.visibleProperty().bind(checkBoxAllDayEvent.selectedProperty().not());
        timePickerStartDate.managedProperty().bind(checkBoxAllDayEvent.selectedProperty().not());
        timePickerEndDate.visibleProperty().bind(checkBoxAllDayEvent.selectedProperty().not());
        timePickerEndDate.managedProperty().bind(checkBoxAllDayEvent.selectedProperty().not());
        txtFieldTitle.setValidators(ValidatorFactory.createRequiredFieldValidator("Title is required!"));
        txtFieldLocation.setValidators(ValidatorFactory.createRequiredFieldValidator("Location is required!"));
        txtAreaDescription.setValidators(ValidatorFactory.createRequiredFieldValidator("Description is required!"));
        datePickerStartDate.setValidators(ValidatorFactory.createRequiredFieldValidator("Start date is required!"));
        datePickerEndDate.setValidators(ValidatorFactory.createRequiredFieldValidator("End date is required!"));
        timePickerStartDate.setValidators(ValidatorFactory.createConditionalRequiredFieldValidator(() -> timePickerStartDate.isVisible(), "Start time is required!"));
        timePickerEndDate.setValidators(ValidatorFactory.createConditionalRequiredFieldValidator(() -> timePickerEndDate.isVisible(), "End time is required!"));
    }

    @Override
    public void resetForm() {
        super.resetForm();
        txtFieldTitle.setText("");
        txtFieldLocation.setText("");
        checkBoxAllDayEvent.setSelected(true);
        datePickerStartDate.setValue(LocalDate.now());
        datePickerEndDate.setValue(LocalDate.now());
        timePickerStartDate.setValue(LocalTime.of(9, 0));
        timePickerEndDate.setValue(LocalTime.of(18, 0));
        txtAreaDescription.setText("");
    }

    @Override
    public BarcodeData getQRData() {
        EventBarcodeDataBuilder builder = new EventBarcodeDataBuilder()
                .withTitle(txtFieldTitle.getText())
                .withLocation(txtFieldLocation.getText())
                .withAllDayEvent(checkBoxAllDayEvent.isSelected())
                .withDescription(txtAreaDescription.getText());
        if (checkBoxAllDayEvent.isSelected()) {
            builder.withStartDate(datePickerStartDate.getValue().atTime(LocalTime.MIN))
                    .withEndDate(datePickerEndDate.getValue().atTime(LocalTime.MAX));
        } else {
            builder.withStartDate(datePickerStartDate.getValue().atTime(timePickerStartDate.getValue()))
                    .withEndDate(datePickerEndDate.getValue().atTime(timePickerEndDate.getValue()));
        }
        return builder.buildData();
    }
}
