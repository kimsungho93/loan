package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Application;
import com.ksh.loan.domain.Entry;
import com.ksh.loan.dto.BalanceDTO;
import com.ksh.loan.dto.EntryDTO;
import com.ksh.loan.dto.EntryDTO.Request;
import com.ksh.loan.dto.EntryDTO.Response;
import com.ksh.loan.dto.EntryDTO.UpdateResponse;
import com.ksh.loan.exception.BaseException;
import com.ksh.loan.exception.ResultType;
import com.ksh.loan.repository.ApplicationRepository;
import com.ksh.loan.repository.EntryRepository;
import com.ksh.loan.service.BalanceService;
import com.ksh.loan.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final BalanceService balanceService;

    private final EntryRepository entryRepository;
    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        // 계약 체결 여부 검증
        if (!isContractedApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Entry entry = modelMapper.map(request, Entry.class);
        entry.setApplicationId(applicationId);

        entryRepository.save(entry);

        // 대출 잔고 관리
        balanceService.create(applicationId, BalanceDTO.Request.builder()
                .entryAmount(request.getEntryAmount())
                .build());

        return modelMapper.map(entry, Response.class);
    }

    @Override
    public Response get(Long applicationId) {
        Optional<Entry> entry = entryRepository.findByApplicationId(applicationId);

        if (entry.isPresent()) {
            return modelMapper.map(entry, Response.class);
        } else {
            return null;
        }
    }

    @Override
    public UpdateResponse update(Long entryId, Request request) {
        // entry
        Entry entry = entryRepository.findById(entryId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));

        // before -> after
        BigDecimal beforeEntryAmount = entry.getEntryAmount();
        entry.setEntryAmount(request.getEntryAmount());

        entryRepository.save(entry);

        // balance update
        Long applicationId = entry.getApplicationId();
        balanceService.update(applicationId,
                BalanceDTO.UpdateRequest.builder()
                        .beforeEntryAmount(beforeEntryAmount)
                        .afterEntryAmount(request.getEntryAmount())
                        .build());

        // response
        return UpdateResponse.builder()
                .entryId(entryId)
                .applicationId(applicationId)
                .beforeEntryAmount(beforeEntryAmount)
                .afterEntryAmount(request.getEntryAmount())
                .build();
    }

    private boolean isContractedApplication(Long applicationId) {
        Optional<Application> existed = applicationRepository.findById(applicationId);
        return existed.filter(application -> application.getContractedAt() != null).isPresent();
    }
}
