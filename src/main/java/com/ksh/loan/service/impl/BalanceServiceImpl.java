package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Balance;
import com.ksh.loan.dto.BalanceDTO;
import com.ksh.loan.dto.BalanceDTO.Request;
import com.ksh.loan.dto.BalanceDTO.Response;
import com.ksh.loan.exception.BaseException;
import com.ksh.loan.exception.ResultType;
import com.ksh.loan.repository.BalanceRepository;
import com.ksh.loan.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Long applicationId, Request request) {
        if (balanceRepository.findByApplicationId(applicationId).isPresent()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Balance balance = modelMapper.map(request, Balance.class);

        BigDecimal entryAmount = request.getEntryAmount();
        balance.setApplicationId(applicationId);
        balance.setBalance(entryAmount);

        balanceRepository.findByApplicationId(applicationId).ifPresent(balance1 -> {
            balance.setBalanceId(balance1.getBalanceId());
            balance.setIsDeleted(balance1.getIsDeleted());
            balance.setCreatedAt(balance1.getCreatedAt());
            balance.setUpdatedAt(balance1.getUpdatedAt());
        });

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response update(Long applicationId, BalanceDTO.UpdateRequest request) {
        // balance
        Balance balance = balanceRepository.findByApplicationId(applicationId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));

        BigDecimal beforeEntryAmount = request.getBeforeEntryAmount();
        BigDecimal afterEntryAmount = request.getBeforeEntryAmount();
        BigDecimal updatedBalance = balance.getBalance();

        // as-is -> to-be
        updatedBalance = updatedBalance.subtract(beforeEntryAmount).add(afterEntryAmount);
        balance.setBalance(updatedBalance);

        Balance updated = balanceRepository.save(balance);

        return modelMapper.map(updated, Response.class);
    }
}
