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
package org.apache.mina.core;

import java.util.Arrays;
import java.util.Collection;

import org.apache.mina.core.BenchmarkFactory.Type;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class NettyClientVsMinaServerBenchmarkBinaryTest
    extends BenchmarkBinaryTest {

    /**
     * @param numberOfMessages
     * @param messageSize
     */
    public NettyClientVsMinaServerBenchmarkBinaryTest( int numberOfMessages, int messageSize, int timeout ) {
        super( numberOfMessages, messageSize, timeout );
    }

    /** {@inheritDoc}
     */
    @Override
    public Type getClientType() {
        return Type.Netty;
    }

    /** {@inheritDoc}
     */
    @Override
    public Type getServerType() {
        return Type.Mina;
    }
    
    //TODO: analyze with Netty is so slow on large message: last test lower to 100 messages
    @Parameters
    public static Collection<Object[]> getParameters() {
        Object[][] parameters = new Object[][] { 
            { 200000, 10, 2 * 60 } //, 
            //{ 1000000, 1 * 1024, 2 * 60 }, 
            //{ 1000000, 10 * 1024, 2 * 60 },
            //{ 100, 64 * 1024 * 1024, 10 * 60 }
    };
        /*
        Object[][] parameters = new Object[][] { 
                { 1000000, 10, 2 * 60 }, 
                { 1000000, 1 * 1024, 2 * 60 }, 
                { 1000000, 10 * 1024, 2 * 60 },
                { 100, 64 * 1024 * 1024, 10 * 60 }
        };*/
        return Arrays.asList(parameters);
    }
}
