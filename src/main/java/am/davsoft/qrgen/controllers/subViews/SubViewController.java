package am.davsoft.qrgen.controllers.subViews;

import am.davsoft.barcodegenerator.api.barcodedata.BarcodeData;
import am.davsoft.qrgen.controllers.MainViewController;
import com.jfoenix.controls.base.IFXValidatableControl;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author David.Shahbazyan
 * @since Aug 28, 2018
 */
public abstract class SubViewController implements Initializable {
    private MainViewController parentViewController;
    private List<IFXValidatableControl> NODES_TO_VALIDATE = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public MainViewController getParentViewController() {
        return parentViewController;
    }

    public void setParentViewController(MainViewController parentViewController) {
        this.parentViewController = parentViewController;
    }

    protected void initControlsForValidation(IFXValidatableControl... controls) {
        NODES_TO_VALIDATE.clear();
        for (IFXValidatableControl control : controls) {
            ((Node) control).focusedProperty().addListener((o, oldVal, newVal) -> {
                if (!newVal) {
                    control.validate();
                }
            });
            NODES_TO_VALIDATE.add(control);
        }
    }

    public void prepareForm() {
        NODES_TO_VALIDATE.clear();
    }

    public boolean validateForm() {
        return NODES_TO_VALIDATE.stream().allMatch(IFXValidatableControl::validate);
    }

    public void resetForm() {
        NODES_TO_VALIDATE.forEach(IFXValidatableControl::resetValidation);
    }

    public abstract BarcodeData getQRData();
}
