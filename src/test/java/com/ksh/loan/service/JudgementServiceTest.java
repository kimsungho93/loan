package com.ksh.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.ksh.loan.domain.Application;
import com.ksh.loan.domain.Judgement;
import com.ksh.loan.repository.ApplicationRepository;
import com.ksh.loan.repository.JudgementRepository;
import com.ksh.loan.service.impl.JudgementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static com.ksh.loan.dto.JudgementDTO.*;

@ExtendWith(MockitoExtension.class)
public class JudgementServiceTest {

    @InjectMocks
    private JudgementServiceImpl judgementService;

    @Mock
    private JudgementRepository judgementRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewJudgementEntity_When_RequestNewJudgement() {
        Judgement judgement = Judgement.builder()
                .applicationId(1L)
                .name("홍길동")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        Request request = Request.builder()
                .applicationId(1L)
                .name("홍길동")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        // application find
        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));
        // judgement save
        when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(judgement);

        Response actual = judgementService.create(request);

        assertThat(actual.getName()).isSameAs(judgement.getName());
        assertThat(actual.getApplicationId()).isSameAs(judgement.getApplicationId());
        assertThat(actual.getApprovalAmount()).isSameAs(judgement.getApprovalAmount());
    }

    @Test
    void Should_ReturnResponseOfExistJudgementEntity_When_RequestExistJudgementId() {
        Judgement entity = Judgement.builder()
                .judgementId(1L)
                .build();

        when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));

        Response actual = judgementService.get(1L);

        assertThat(actual.getJudgementId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnResponseOfExistJudgementEntity_When_RequestExistApplicationId() {
        Judgement judgementEntity = Judgement.builder()
                .judgementId(1L)
                .build();

        Application applicationEntity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(applicationEntity));
        when(judgementRepository.findByApplicationId(1L)).thenReturn(Optional.ofNullable(judgementEntity));

        Response actual = judgementService.getJudgementByApplication(1L);

        assertThat(actual.getJudgementId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistJudgementEntity_When_RequestUpdateExistJudgementInfo() {
        Judgement entity = Judgement.builder()
                .judgementId(1L)
                .name("홍길동")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        Request request = Request.builder()
                .name("이순신")
                .approvalAmount(BigDecimal.valueOf(4000000))
                .build();

        when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
        when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(entity);

        Response actual = judgementService.update(1L, request);

        assertThat(actual.getJudgementId()).isSameAs(1L);
        assertThat(actual.getName()).isSameAs(request.getName());
        assertThat(actual.getApprovalAmount()).isSameAs(request.getApprovalAmount());
    }

    @Test
    void Should_DeletedJudgementEntity_When_RequestDeleteExistJudgementInfo() {
        Judgement entity = Judgement.builder()
                .judgementId(1L)
                .build();

        when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
        when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(entity);

        judgementService.delete(1L);

        assertThat(entity.getIsDeleted()).isTrue();
    }

}
