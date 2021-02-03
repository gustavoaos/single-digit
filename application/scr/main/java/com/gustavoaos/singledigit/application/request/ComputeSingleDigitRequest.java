package com.gustavoaos.singledigit.application.request;

import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ComputeSingleDigitRequest {

    String n;
    String k;

    public SingleDigit toDomain(SingleDigitStrategy strategy) {
        return new SingleDigit(n, k, strategy);
    }

}
