package core.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.maxed.photocleaner.core.services.ExtensionCorrecter;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionCorrecterTest {
    @Test
    void correctCase(){
        String actual = ".BmP";
        String expected = ".bmp";
        actual = ExtensionCorrecter.correct(actual);
        Assertions.assertEquals(expected,actual);
    }
    @Test
    void correctDot(){
        String actual = "bmp";
        String expected = ".bmp";
        actual = ExtensionCorrecter.correct(actual);
        Assertions.assertEquals(expected,actual);
    }
}