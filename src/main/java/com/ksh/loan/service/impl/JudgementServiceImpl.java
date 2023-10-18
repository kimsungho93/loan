package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Judgement;
import com.ksh.loan.exception.BaseException;
import com.ksh.loan.exception.ResultType;
import com.ksh.loan.repository.ApplicationRepository;
import com.ksh.loan.repository.JudgementRepository;
import com.ksh.loan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.ksh.loan.dto.JudgementDTO.*;

@Service
@RequiredArgsConstructor
public class JudgementServiceImpl implements JudgementService {

    private final JudgementRepository judgementRepository;
    private final ApplicationRepository applicationRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        // 신청 정보 검증
        Long applicationId = request.getApplicationId();
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // request dto -> entity -> save
        Judgement judgement = modelMapper.map(request, Judgement.class);
        Judgement saved = judgementRepository.save(judgement);

        // save -> response dto
        return modelMapper.map(saved, Response.class);
    }

    @Override
    public Response get(Long judgementId) {
        Judgement judgement = judgementRepository.findById(judgementId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));
        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public Response getJudgementByApplication(Long applicationId) {
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Judgement judgement = judgementRepository.findByApplicationId(applicationId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public Response update(Long judgementId, Request request) {
        Judgement judgement = judgementRepository.findById(judgementId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));

        judgement.setName(request.getName());
        judgement.setApprovalAmount(request.getApprovalAmount());

        judgementRepository.save(judgement);

        return modelMapper.map(judgement, Response.class);
    }

    @Override
    public void delete(Long judgementId) {
        Judgement judgement = judgementRepository.findById(judgementId).orElseThrow(
                () -> new BaseException(ResultType.SYSTEM_ERROR));
        judgement.setIsDeleted(true);

        judgementRepository.save(judgement);
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }
}
