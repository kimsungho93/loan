package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Application;
import com.ksh.loan.dto.ApplicationDTO;
import com.ksh.loan.repository.ApplicationRepository;
import com.ksh.loan.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ksh.loan.dto.ApplicationDTO.*;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);

        return modelMapper.map(applied, Response.class);
    }
}
