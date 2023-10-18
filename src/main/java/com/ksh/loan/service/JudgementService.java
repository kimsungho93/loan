package com.ksh.loan.service;


import com.ksh.loan.dto.ApplicationDTO;
import com.ksh.loan.dto.ApplicationDTO.GrantAmount;

import static com.ksh.loan.dto.JudgementDTO.*;

public interface JudgementService {

    Response create(Request request);

    Response get(Long judgementId);

    Response getJudgementByApplication(Long applicationId);

    Response update(Long judgementId, Request request);

    void delete(Long judgementId);

    GrantAmount grant(Long judgementId);
}
