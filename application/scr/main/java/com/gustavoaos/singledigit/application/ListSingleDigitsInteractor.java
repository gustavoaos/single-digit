package com.gustavoaos.singledigit.application;

import com.gustavoaos.singledigit.application.response.SingleDigitListResponse;

public interface ListSingleDigitsInteractor {

    SingleDigitListResponse execute(String id);
}
