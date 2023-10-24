package com.ksh.loan.service;

import com.ksh.loan.dto.RepaymentDTO.ListResponse;
import com.ksh.loan.dto.RepaymentDTO.Request;
import com.ksh.loan.dto.RepaymentDTO.Response;
import com.ksh.loan.dto.RepaymentDTO.UpdateResponse;

import java.util.List;

public interface RepaymentService {
    Response create(Long applicationId, Request request);

    List<ListResponse> get(Long applicationId);

    UpdateResponse update(Long repaymentId, Request request);

    void delete(Long repaymentId);
}
