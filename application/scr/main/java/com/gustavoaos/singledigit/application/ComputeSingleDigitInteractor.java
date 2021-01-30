package com.gustavoaos.singledigit.application;

import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;

public interface ComputeSingleDigitInteractor {

    Integer execute(ComputeSingleDigitRequest request);

    Integer execute(ComputeSingleDigitRequest request, String id);
}
