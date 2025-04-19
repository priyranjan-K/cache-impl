package com.sample.cache_impl.util;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Target({ElementType.FIELD,ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateValidator.class)
public @interface DateFormat {
    String message() default "Invalid date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class ValidDateValidator implements ConstraintValidator<DateFormat, String> {

    private static final String DATE_PATTERN = "dd/MM/yyyy";


    @Override
    public void initialize(DateFormat constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Null or empty value can be validated by @NotNull if needed
        }

        // Now validate the actual date using SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        sdf.setLenient(false); // This makes sure the date is strictly validated

        try {
            Date parsedDate = sdf.parse(value);
            return parsedDate != null; // If parsedDate is valid, the date exists
        } catch (ParseException e) {
            return false; // If exception occurs, the date is not valid
        }
    }
}
