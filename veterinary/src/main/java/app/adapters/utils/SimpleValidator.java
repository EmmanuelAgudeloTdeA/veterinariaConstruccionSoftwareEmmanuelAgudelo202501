package app.adapters.utils;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Component
public class SimpleValidator {

    public String stringValidator(String value, String element) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(element + " no tiene un valor válido");
        }
        return value.trim();
    }

    public Long longValidator(String value, String element) throws ValidationException {
        try {
            return Long.parseLong(stringValidator(value, element));
        } catch (NumberFormatException e) {
            throw new ValidationException(element + " debe ser un número entero válido");
        }
    }

    public Date dateValidator(Date value, String element) throws ValidationException {
        if (value == null) {
            throw new ValidationException(element + " no tiene un valor válido");
        }
        return value;
    }

    public float floatValidator(float value, String element) throws ValidationException {
        if (value < 0) {
            throw new ValidationException(element + " debe ser un número positivo");
        }
        return value;
    }

    public int intValidator(int value, String element) throws ValidationException {
        if (value < 0) {
            throw new ValidationException(element + " debe ser un número positivo");
        }
        return value;
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
