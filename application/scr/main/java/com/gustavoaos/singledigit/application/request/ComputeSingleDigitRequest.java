package com.gustavoaos.singledigit.application.request;

import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class ComputeSingleDigitRequest {

    @NotBlank(message = "N is mandatory")
    String n;

    @NotBlank(message = "K is mandatory")
    String k;

    public SingleDigit toDomain(SingleDigitStrategy strategy) {
        return new SingleDigit(n, k, strategy);
    }

}
