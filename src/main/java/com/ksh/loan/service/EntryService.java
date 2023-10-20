package com.ksh.loan.service;

import com.ksh.loan.dto.EntryDTO.Request;
import com.ksh.loan.dto.EntryDTO.Response;

public interface EntryService {
    Response create(Long applicationId, Request request);

    Response get(Long applicationId);
}
