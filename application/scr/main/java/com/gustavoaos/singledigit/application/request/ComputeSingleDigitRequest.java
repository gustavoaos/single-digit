package com.gustavoaos.singledigit.application.request;

import com.gustavoaos.singledigit.domain.SingleDigit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ComputeSingleDigitRequest {

    String n;
    String k;

    public SingleDigit toDomain() {
        return new SingleDigit(n, k);
    }

}
