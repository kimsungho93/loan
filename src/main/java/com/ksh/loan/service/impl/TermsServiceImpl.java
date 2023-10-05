package com.ksh.loan.service.impl;

import com.ksh.loan.domain.Terms;
import com.ksh.loan.repository.TermsRepository;
import com.ksh.loan.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.ksh.loan.dto.TermsDTO.*;

@Service
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response create(Request request) {
        Terms terms = modelMapper.map(request, Terms.class);
        Terms created = termsRepository.save(terms);

        return modelMapper.map(created, Response.class);
    }
}
