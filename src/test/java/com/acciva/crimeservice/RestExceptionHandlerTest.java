package com.acciva.crimeservice;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setUp() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void validMessageOnInvalidDataExceptionHandler() {
        Map<String, String> messages = restExceptionHandler
                .invalidDataExceptionHandler(new InvalidDataException("test message"));
        assertEquals(1, messages.size());
        assertTrue(messages.containsKey("message"));
        assertEquals("test message", messages.get("message"));
    }

    @Test
    void expectNullPointerExceptionWhenNullObjectPassed() {
        assertThrows(NullPointerException.class,
                () -> restExceptionHandler.invalidDataExceptionHandler(null));
    }
}