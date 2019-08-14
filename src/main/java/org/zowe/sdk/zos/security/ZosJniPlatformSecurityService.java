/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.sdk.zos.security;

import org.zowe.sdk.zos.security.PlatformSecurityService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("zos")
@Service("platformSecurityService")
/**
 * Implements low-level security function using pthread_security_np that is
 * called via JIN in module libSecuur.so.
 */
public class ZosJniPlatformSecurityService implements PlatformSecurityService {
    private static final int CREATE_THREAD_SECURITY_CONTEXT = 0;
    private static final int REMOVE_THREAD_SECURITY_CONTEXT = 1;

    private final Secur secur = new Secur();

    @Override
    public void createThreadSecurityContext(String userId, String password, String applId) {
        checkRc(secur.createSecurityEnvironment(userId, password, applId), CREATE_THREAD_SECURITY_CONTEXT);
    }

    private void checkRc(int rc, int function) {
        if (rc != 0) {
            throw new SecurityRequestFailed(secur.getLibraryName(), function, rc, 0, 0, null);
        }
    }

    @Override
    public void createThreadSecurityContextByDaemon(String userId, String applId) {
        checkRc(secur.createSecurityEnvironmentByDaemon(userId, applId), CREATE_THREAD_SECURITY_CONTEXT);
    }

    @Override
    public void removeThreadSecurityContext() {
        checkRc(secur.removeSecurityEnvironment(), REMOVE_THREAD_SECURITY_CONTEXT);
    }
}
