package core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.exeptions.TestExeption;
import ru.maxed.photocleaner.core.services.ExtensionValidator;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionValidatorTest {

    @Test
    void validateSame() {
        String originExtension = ".pdf";
        String processedExtension = ".pdf";
        Assertions.assertThrows(TestExeption.class,()->ExtensionValidator.validate(originExtension,processedExtension));
    }
    @Test
    void validateVoid() {
        String originExtension = "";
        String processedExtension = ".pdf";
        Assertions.assertThrows(TestExeption.class,()->ExtensionValidator.validate(originExtension,processedExtension));
    }
    @Test
    void validateWrongFormat() {
        String originExtension = ".p.df";
        String processedExtension = ".pdf";
        Assertions.assertThrows(TestExeption.class,()->ExtensionValidator.validate(originExtension,processedExtension));
    }
    @Test
    void validateNormal() {
        String originExtension = ".pdf";
        String processedExtension = "bmp";
        Assertions.assertDoesNotThrow(()->ExtensionValidator.validate(originExtension,processedExtension));
    }
}