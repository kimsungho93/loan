package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Counsel;
import com.ksh.loan.dto.CounselDto;
import com.ksh.loan.repository.CounselRepository;
import com.ksh.loan.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.ksh.loan.dto.CounselDto.*;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService {

    private final CounselRepository counselRepository;

    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());

        Counsel created = counselRepository.save(counsel);

        return modelMapper.map(created, Response.class);
    }
}
