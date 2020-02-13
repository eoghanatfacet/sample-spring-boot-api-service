/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.commons.spring.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.zowe.commons.spring.token.AbstractTokenHandler;
import org.zowe.commons.spring.token.TokenAuthentication;
import org.zowe.commons.spring.token.TokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Filter to validate JWT token in all the rest API's.
 */
public class JWTAuthorizationFilter extends AbstractTokenHandler {

    private final ZoweAuthenticationUtility authConfigurationProperties;

    public JWTAuthorizationFilter(ZoweAuthenticationFailureHandler failureHandler,
                                  ZoweAuthenticationUtility authConfigurationProperties,
                                  TokenService tokenService) {
        super(failureHandler, authConfigurationProperties, tokenService);
        this.authConfigurationProperties = authConfigurationProperties;
    }

    @Override
    protected Optional<AbstractAuthenticationToken> extractContent(HttpServletRequest request) {
        return Optional.of(
            new TokenAuthentication(request.getHeader(authConfigurationProperties.getAuthorizationHeader())));
    }
}
