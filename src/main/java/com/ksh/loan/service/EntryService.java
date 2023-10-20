package com.ksh.loan.service;

import com.ksh.loan.dto.EntryDTO;
import com.ksh.loan.dto.EntryDTO.Request;
import com.ksh.loan.dto.EntryDTO.Response;
import com.ksh.loan.dto.EntryDTO.UpdateResponse;

public interface EntryService {
    Response create(Long applicationId, Request request);

    Response get(Long applicationId);

    UpdateResponse update(Long entryId, Request request);
}
