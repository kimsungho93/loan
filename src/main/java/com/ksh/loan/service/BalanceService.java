package com.ksh.loan.service;

import com.ksh.loan.dto.BalanceDTO;
import com.ksh.loan.dto.BalanceDTO.RepaymentRequest;
import com.ksh.loan.dto.BalanceDTO.Request;
import com.ksh.loan.dto.BalanceDTO.Response;
import com.ksh.loan.dto.BalanceDTO.UpdateRequest;

public interface BalanceService {


    Response create(Long applicationId, Request request);

    Response get(Long applicationId);

    Response update(Long applicationId, UpdateRequest request);

    Response repaymentUpdate(Long applicationId, RepaymentRequest request);

    void delete(Long applicationId);

}
