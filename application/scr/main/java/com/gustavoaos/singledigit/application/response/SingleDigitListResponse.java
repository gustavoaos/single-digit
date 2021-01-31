package com.gustavoaos.singledigit.application.response;

import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.User;
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

    public static SingleDigitListResponse from(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Invalid user provided.");
        }

        return SingleDigitListResponse
                .builder()
                .singleDigits(user.getSingleDigits())
                .build();
    }
}
