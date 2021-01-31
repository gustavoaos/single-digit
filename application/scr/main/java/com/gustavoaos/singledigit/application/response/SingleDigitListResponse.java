package com.gustavoaos.singledigit.application.response;

import com.gustavoaos.singledigit.domain.SingleDigit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SingleDigitListResponse {

    @Builder.Default
    List<SingleDigit> singleDigits = new ArrayList<>();
}
