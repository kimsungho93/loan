package com.ksh.loan.service;

import com.ksh.loan.dto.BalanceDTO.Request;
import com.ksh.loan.dto.BalanceDTO.Response;
import com.ksh.loan.dto.BalanceDTO.UpdateRequest;

public interface BalanceService {

    Response create(Long applicationId, Request request);

    Response update(Long applicationId, UpdateRequest request);

}
