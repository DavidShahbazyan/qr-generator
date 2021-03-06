package am.davsoft.qrgen.util;

import am.davsoft.qrgen.validators.ConditionalRequiredFieldValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.GlyphIcon;
import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.util.function.Supplier;

public final class ValidatorFactory {
    private static final String EM1 = "1em";
    private static final String ERROR = "error";
    private static final GlyphIcon REQUIRED_VALIDATOR_ICON = GlyphsBuilder.create(FontAwesomeIconView.class)
            .glyph(FontAwesomeIcon.WARNING)
            .size(EM1)
            .styleClass(ERROR)
            .build();

    public static RequiredFieldValidator createRequiredFieldValidator(String message) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(message);
        validator.setIcon(REQUIRED_VALIDATOR_ICON);
        return validator;
    }

    public static ConditionalRequiredFieldValidator createConditionalRequiredFieldValidator(Supplier<Boolean> condition, String message) {
        ConditionalRequiredFieldValidator validator = new ConditionalRequiredFieldValidator(condition);
        validator.setMessage(message);
        validator.setIcon(REQUIRED_VALIDATOR_ICON);
        return validator;
    }
}
