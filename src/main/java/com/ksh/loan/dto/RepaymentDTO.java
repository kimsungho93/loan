package com.ksh.loan.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepaymentDTO implements Serializable {
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request {
        private BigDecimal repaymentAmount;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long applicationId;
        private BigDecimal repaymentAmount;
        private BigDecimal balance;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}