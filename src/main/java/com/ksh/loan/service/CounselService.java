package com.ksh.loan.service;


import static com.ksh.loan.dto.CounselDto.*;

public interface CounselService {

    // 상담 신청
    Response create(Request request);

    // 상담 조회
    Response get(Long counselId);

    // 상담 수정
    Response update(Long counselId, Request request);

    // 상담 삭제
    void delete(Long counselId);
}
