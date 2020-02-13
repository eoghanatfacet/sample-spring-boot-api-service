/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.commons.spring.login;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zowe.commons.spring.token.TokenService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping
public class LoginController {
    @Autowired
    TokenService tokenService;

    @PostMapping(value = "/api/v1/auth/login", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "/login", hidden = true)
    public ResponseEntity login(@Validated(LoginRequest.class) @RequestBody(required = false) LoginRequest loginRequest,
                                HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        return tokenService.login(loginRequest, request, response);
    }
}
