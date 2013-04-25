/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.mina.coap;

import java.util.Arrays;
import java.util.Comparator;

public class CoapMessage {
    private final int version;
    private final MessageType type;
    private final int code;
    private final int id;
    private final byte[] token;
    private final byte[] payload;
    private final CoapOption[] options;

    public CoapMessage(int version, MessageType type, int code, int id, byte[] token, byte[] payload,
            CoapOption[] options) {
        super();
        this.version = version;
        this.type = type;
        this.code = code;
        this.id = id;
        this.token = token;
        this.payload = payload;
        this.options = options;

        // sort options by code (easier for delta encoding)
        Arrays.<CoapOption> sort(this.options, new Comparator<CoapOption>() {
            @Override
            public int compare(CoapOption o1, CoapOption o2) {
                return o1.getType().getCode() < o2.getType().getCode() ? -1 : (o1.getType().getCode() == o2.getType()
                        .getCode() ? 0 : 1);
            };
        });
    }

    public int getVersion() {
        return version;
    }

    public int getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public byte[] getToken() {
        return token;
    }

    public byte[] getPayload() {
        return payload;

    }

    public CoapOption[] getOptions() {
        return options;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CoapMessage [version=").append(version).append(", type=").append(type).append(", code=")
                .append(code).append(", id=").append(id).append(", token=").append(Arrays.toString(token))
                .append(", payload=").append(Arrays.toString(payload)).append(", options=")
                .append(Arrays.toString(options)).append("]");
        return builder.toString();
    }
}
