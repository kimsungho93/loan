package com.ksh.loan.controller;

import com.ksh.loan.dto.ResponseDTO;
import com.ksh.loan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{judgementId}")
    public ResponseDTO<Response> get(@PathVariable Long judgementId) {
        return ok(judgementService.get(judgementId));
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseDTO<Response> getJudgementByApplication(@PathVariable Long applicationId) {
        return ok(judgementService.getJudgementByApplication(applicationId));
    }

    @PutMapping("/{judgementId}")
    public ResponseDTO<Response> update(@PathVariable Long judgementId, @RequestBody Request request) {
        return ok(judgementService.update(judgementId, request));
    }

    @DeleteMapping("/{judgementId}")
    public ResponseDTO<Void> delete(@PathVariable Long judgementId) {
        judgementService.delete(judgementId);
        return ok();
    }
}
