package ru.maxed.photocleaner.core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.maxed.photocleaner.core.exeptions.TestException;

import java.util.stream.Stream;

class ExtensionValidatorTest {
    private static Stream<Arguments> provideStrings() {
        return Stream.of(
                Arguments.of(".pdf", ".pdf"),
                Arguments.of("", ".pdf"),
                Arguments.of(".pdf", ".p.df")
        );
    }

    @ParameterizedTest
    @MethodSource("provideStrings")
    void validate(String processedExtension, String originExtension) {
        Assertions.assertThrows(TestException.class, () -> ExtensionValidator.validate(originExtension, processedExtension));
    }

    @Test
    void validateNormal() {
        String originExtension = ".pdf";
        String processedExtension = "bmp";
        Assertions.assertDoesNotThrow(() -> ExtensionValidator.validate(originExtension, processedExtension));
    }
}