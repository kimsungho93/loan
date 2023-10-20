package com.ksh.loan.service;

import com.ksh.loan.dto.RepaymentDTO.Request;
import com.ksh.loan.dto.RepaymentDTO.Response;

public interface RepaymentService {
    Response create(Long applicationId, Request request);
}
