package com.walmartlabs.concord.server.org.secret;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2018 Walmart Inc.
 * -----
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =====
 */

import com.google.common.io.ByteStreams;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.walmartlabs.concord.common.secret.KeyPair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public final class KeyPairUtils {

    private static final int DEFAULT_KEY_SIZE = 2048;
    private static final int DEFAULT_KEY_TYPE = com.jcraft.jsch.KeyPair.RSA;
    private static final String DEFAULT_KEY_COMMENT = "concord-server";

    private static final JSch jsch = new JSch();

    public static KeyPair create() {
        com.jcraft.jsch.KeyPair k;
        synchronized (jsch) {
            try {
                k = com.jcraft.jsch.KeyPair.genKeyPair(jsch, DEFAULT_KEY_TYPE, DEFAULT_KEY_SIZE);
            } catch (JSchException e) {
                throw new SecurityException(e);
            }
        }

        byte[] publicKey = array(out -> k.writePublicKey(out, DEFAULT_KEY_COMMENT));
        byte[] privateKey = array(k::writePrivateKey);

        return new KeyPair(publicKey, privateKey);
    }

    public static KeyPair create(InputStream publicIn, InputStream privateIn) throws IOException {
        byte[] publicKey = ByteStreams.toByteArray(publicIn);
        byte[] privateKey = ByteStreams.toByteArray(privateIn);

        return new KeyPair(publicKey, privateKey);
    }

    private static byte[] array(Consumer<OutputStream> c) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        c.accept(out);
        return out.toByteArray();
    }

    public static void validateKeyPair(byte[] pub, byte[] priv) throws Exception {
        JSch j = new JSch();
        com.jcraft.jsch.KeyPair.load(j, priv, pub);
    }

    private KeyPairUtils() {
    }
}
