package com.ksh.loan.controller;

import com.ksh.loan.dto.EntryDTO;
import com.ksh.loan.dto.EntryDTO.Request;
import com.ksh.loan.dto.EntryDTO.Response;
import com.ksh.loan.dto.EntryDTO.UpdateResponse;
import com.ksh.loan.dto.ResponseDTO;
import com.ksh.loan.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/internal/applications")
public class InternalController extends AbstractController{

    private final EntryService entryService;

    @PostMapping("/{applicationId}/entries")
    public ResponseDTO<Response> create(@PathVariable Long applicationId, @RequestBody Request request) {
        return ok(entryService.create(applicationId, request));
    }

    @GetMapping("/{applicationId}/entries")
    public ResponseDTO<Response> get(@PathVariable Long applicationId) {
        return ok(entryService.get(applicationId));
    }

    @PutMapping("/entries/{entryId}")
    public ResponseDTO<UpdateResponse> update(@PathVariable Long entryId, @RequestBody Request request) {
        return ok(entryService.update(entryId, request));
    }
}
