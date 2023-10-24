package com.ksh.loan.service;

import com.ksh.loan.dto.RepaymentDTO.ListResponse;
import com.ksh.loan.dto.RepaymentDTO.Request;
import com.ksh.loan.dto.RepaymentDTO.Response;

import java.util.List;

public interface RepaymentService {
    Response create(Long applicationId, Request request);

    List<ListResponse> get(Long applicationId);
}
