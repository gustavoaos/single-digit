package com.gustavoaos.singledigit.domain.exception;

import lombok.Getter;

import java.text.MessageFormat;

public class ParameterOutOfRangeException extends RuntimeException {

    @Getter private final String parameterName;
    @Getter private final String lowerRange;
    @Getter private final String upperRange;

    public ParameterOutOfRangeException(String parameterName, String lowerRange, String upperRange) {
        super(MessageFormat.format(
                "Parameter {0} must be greater than {1} and lower than {2}.",
                parameterName, lowerRange, upperRange)
        );

        this.parameterName = parameterName;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }
}
