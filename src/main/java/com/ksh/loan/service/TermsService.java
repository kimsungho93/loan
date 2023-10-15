package com.ksh.loan.service;


import java.util.List;

import static com.ksh.loan.dto.TermsDTO.*;

public interface TermsService {

    Response create(Request request);

    List<Response> getAll();
}
