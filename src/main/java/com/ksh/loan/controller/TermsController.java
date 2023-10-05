package com.ksh.loan.controller;

import com.ksh.loan.dto.ResponseDTO;
import com.ksh.loan.dto.TermsDTO;
import com.ksh.loan.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ksh.loan.dto.ResponseDTO.*;
import static com.ksh.loan.dto.TermsDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermsController {

    private final TermsService termsService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(termsService.create(request));
    }
}
