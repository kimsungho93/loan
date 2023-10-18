package com.ksh.loan.controller;

import com.ksh.loan.dto.ResponseDTO;
import com.ksh.loan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ksh.loan.dto.JudgementDTO.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/judgements")
public class JudgementController extends AbstractController{

    private final JudgementService judgementService;

    @PostMapping
    public ResponseDTO<Response> create(@RequestBody Request request) {
        return ok(judgementService.create(request));
    }
}
