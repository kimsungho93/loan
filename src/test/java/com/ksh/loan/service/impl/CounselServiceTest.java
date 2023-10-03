package com.ksh.loan.service.impl;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.ksh.loan.domain.Counsel;
import com.ksh.loan.repository.CounselRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static com.ksh.loan.dto.CounselDto.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
        Counsel entity = Counsel.builder()
                .name("Kim")
                .cellPhone("010-1111-2222")
                .email("test@gmail.com")
                .memo("저는 대출을 받고 싶어요. 연락을 주세요.")
                .zipCode("12345")
                .address("서울특별시 강남구 논현동")
                .addressDetail("101동 101호")
                .build();

        Request request = Request.builder()
                .name("Kim")
                .cellPhone("010-1111-2222")
                .email("test@gmail.com")
                .memo("저는 대출을 받고 싶어요. 연락을 주세요.")
                .zipCode("12345")
                .address("서울특별시 강남구 논현동")
                .addressDetail("101동 101호")
                .build();

        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);
        Response actual = counselService.create(request);

        assertThat(actual.getName()).isEqualTo(entity.getName());
    }

}