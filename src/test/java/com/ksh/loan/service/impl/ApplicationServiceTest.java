package com.ksh.loan.service.impl;

import static org.mockito.Mockito.when;

import com.ksh.loan.domain.Application;
import com.ksh.loan.repository.ApplicationRepository;
import org.assertj.core.api.Assertions;
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

import static com.ksh.loan.dto.ApplicationDTO.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication() {
        Application entity = Application.builder()
                .name("Kim")
                .cellPhone("010-1111-2222")
                .email("test@gmail.com")
                .hopeAmount(BigDecimal.valueOf(50000000)) // 5천만원
                .build();

        Request request = Request.builder()
                .name("Kim")
                .cellPhone("010-1111-2222")
                .email("test@gmail.com")
                .hopeAmount(BigDecimal.valueOf(50000000)) // 5천만원
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);

        Response actual = applicationService.create(request);

        Assertions.assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
        Assertions.assertThat(actual.getName()).isSameAs(entity.getName());
    }

    @Test
    void Should_ReturnResponseOfExistApplicationEntity_When_RequestExistApplicationId() {
        Long findId = 1L;

        Application entity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.findById(findId)).thenReturn(Optional.ofNullable(entity));

        Response actual = applicationService.get(findId);

        Assertions.assertThat(actual.getApplicationId()).isSameAs(findId);
    }
}
