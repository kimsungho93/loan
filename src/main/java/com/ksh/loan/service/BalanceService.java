package com.ksh.loan.service;

import com.ksh.loan.dto.BalanceDTO.Request;
import com.ksh.loan.dto.BalanceDTO.Response;

public interface BalanceService {

    Response create(Long applicationId, Request request);
}
