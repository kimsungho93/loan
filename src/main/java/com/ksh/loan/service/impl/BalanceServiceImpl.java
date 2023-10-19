package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Balance;
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

        Balance saved = balanceRepository.save(balance);

        return modelMapper.map(saved, Response.class);
    }
}
