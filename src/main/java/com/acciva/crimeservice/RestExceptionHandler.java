package com.acciva.crimeservice;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(code = BAD_REQUEST)
    @ResponseBody
    Map<String, String> invalidDataExceptionHandler(InvalidDataException e) {
        return getMessage(e.getMessage());
    }

    private Map<String, String> getMessage(String message) {
        HashMap<String, String> map = new HashMap();
        map.put("message", message);
        return map;
    }
}
