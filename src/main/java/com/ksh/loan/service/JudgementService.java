package com.ksh.loan.service;


import static com.ksh.loan.dto.JudgementDTO.*;

public interface JudgementService {

    Response create(Request request);

    Response get(Long judgementId);

    Response getJudgementByApplication(Long applicationId);

    Response update(Long judgementId, Request request);
}
