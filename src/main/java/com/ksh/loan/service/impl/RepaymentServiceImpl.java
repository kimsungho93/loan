package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Application;
import com.ksh.loan.domain.Entry;
import com.ksh.loan.domain.Repayment;
import com.ksh.loan.dto.BalanceDTO;
import com.ksh.loan.dto.BalanceDTO.RepaymentRequest.RepaymentType;
import com.ksh.loan.dto.RepaymentDTO.ListResponse;
import com.ksh.loan.dto.RepaymentDTO.Request;
import com.ksh.loan.dto.RepaymentDTO.Response;
import com.ksh.loan.dto.RepaymentDTO.UpdateResponse;
import com.ksh.loan.exception.BaseException;
import com.ksh.loan.exception.ResultType;
import com.ksh.loan.repository.ApplicationRepository;
import com.ksh.loan.repository.EntryRepository;
import com.ksh.loan.repository.RepaymentRepository;
import com.ksh.loan.service.BalanceService;
import com.ksh.loan.service.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;
    private final ApplicationRepository applicationRepository;
    private final EntryRepository entryRepository;
    private final BalanceService balanceService;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        // validation
        // 1. 계약을 완료한 신청 정보
        // 2. 집행이 되어있어야 함
        if (!isRepayableApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Repayment repayment = modelMapper.map(request, Repayment.class);
        repayment.setApplicationId(applicationId);

        repaymentRepository.save(repayment);

        // 잔고
        // balance : 500 -> 100 = 400
        BalanceDTO.Response updatedBalance = balanceService.repaymentUpdate(applicationId, BalanceDTO.RepaymentRequest.builder()
                .repaymentAmount(request.getRepaymentAmount())
                .type(RepaymentType.REMOVE)
                .build());

        Response response = modelMapper.map(repayment, Response.class);
        response.setBalance(updatedBalance.getBalance());

        return response;
    }

    @Override
    public List<ListResponse> get(Long applicationId) {
        List<Repayment> repayments = repaymentRepository.findAllByApplicationId(applicationId);

        return repayments.stream()
                .map(repayment -> modelMapper.map(repayment, ListResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UpdateResponse update(Long repaymentId, Request request) {
        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));

        Long applicationId = repayment.getApplicationId();
        BigDecimal beforeRepaymentAmount = repayment.getRepaymentAmount();

        // 500 - 100 = 400
        // 400 + 100 = 500
        // 500 - 200 = 300

        balanceService.repaymentUpdate(applicationId,
                BalanceDTO.RepaymentRequest.builder()
                        .repaymentAmount(beforeRepaymentAmount)
                        .type(RepaymentType.ADD)
                        .build());

        repayment.setRepaymentAmount(request.getRepaymentAmount());
        repaymentRepository.save(repayment);

        BalanceDTO.Response updatedBalance = balanceService.repaymentUpdate(applicationId, BalanceDTO.RepaymentRequest.builder()
                .repaymentAmount(request.getRepaymentAmount())
                .type(RepaymentType.REMOVE)
                .build());

        return UpdateResponse.builder()
                .applicationId(applicationId)
                .beforeRepaymentAmount(beforeRepaymentAmount)
                .afterRepaymentAmount(request.getRepaymentAmount())
                .balance(updatedBalance.getBalance())
                .createdAt(repayment.getCreatedAt())
                .updatedAt(repayment.getUpdatedAt())
                .build();
    }

    @Override
    public void delete(Long repaymentId) {
        Repayment repayment = repaymentRepository.findById(repaymentId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));

        Long applicationId = repayment.getApplicationId();
        BigDecimal removeRepaymentAmount = repayment.getRepaymentAmount();

        balanceService.repaymentUpdate(applicationId,
                BalanceDTO.RepaymentRequest.builder()
                        .repaymentAmount(removeRepaymentAmount)
                        .type(RepaymentType.ADD)
                        .build());

        repayment.setIsDeleted(true);
        repaymentRepository.save(repayment);
    }


    private boolean isRepayableApplication(Long applicationId) {
        Optional<Application> existedApplication = applicationRepository.findById(applicationId);
        if (existedApplication.isEmpty()) {
            return false;
        }

        if (existedApplication.get().getContractedAt() == null) {
            return false;
        }

        Optional<Entry> existedEntry = entryRepository.findByApplicationId(applicationId);
        return existedEntry.isPresent();
    }
}
