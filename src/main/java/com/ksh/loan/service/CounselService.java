package com.ksh.loan.service;


import static com.ksh.loan.dto.CounselDto.*;

public interface CounselService {

    // 상담 신청
    Response create(Request request);

    // 상담 조회
    Response get(Long counselId);
}
