package com.codecool.masonrysystem.helper;/*
 * Copyright 2005 Joe Walker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Revised from dwr

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


/**
 * Code to generate page ids.
 * IdGenerators are expensive to setup so it is suggested that you share
 * instances wherever possible. This action will also enhance security.
 * Much of this code is adapted from org.apache.catalina.session.ManagerBase.
 * Specifically Revision 1.37 which has been unchanged in the past 18 months.
 * I have taken out the /dev/urandom stuff and simplified things to the point
 * where we can audit it to work out what might be broken.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class IdGenerator
{
    public IdGenerator()
    {
        long seed = System.currentTimeMillis();
        char[] entropy = toString().toCharArray();
        for (int i = 0; i < entropy.length; i++)
        {
            long update = ((byte) entropy[i]) << ((i % 8) * 8);
            seed ^= update;
        }

        random.setSeed(seed);
    }

    public synchronized String generateId(int length)
    {
        byte[] buffer = new byte[length];

        StringBuffer reply = new StringBuffer();

        int resultLenBytes = 0;
        while (resultLenBytes < length)
        {
            random.nextBytes(buffer);
            buffer = getDigest().digest(buffer);

            for (int j = 0; j < buffer.length && resultLenBytes < length; j++)
            {
                byte b1 = (byte) ((buffer[j] & 0xf0) >> 4);
                if (b1 < 10)
                {
                    reply.append((char) ('0' + b1));
                }
                else
                {
                    reply.append((char) ('A' + (b1 - 10)));
                }

                byte b2 = (byte) (buffer[j] & 0x0f);
                if (b2 < 10)
                {
                    reply.append((char) ('0' + b2));
                }
                else
                {
                    reply.append((char) ('A' + (b2 - 10)));
                }

                resultLenBytes++;
            }
        }

        return reply.toString();
    }

    public synchronized String getAlgorithm()
    {
        return algorithm;
    }

    public synchronized void setAlgorithm(String algorithm)
    {
        this.algorithm = algorithm;
        digest = null;
    }

    private MessageDigest getDigest()
    {
        if (digest == null)
        {
            try
            {
                digest = MessageDigest.getInstance(algorithm);
            }
            catch (NoSuchAlgorithmException ex)
            {
                try
                {
                    digest = MessageDigest.getInstance(DEFAULT_ALGORITHM);
                }
                catch (NoSuchAlgorithmException ex2)
                {
                    digest = null;
                    throw new IllegalStateException("No algorithms for IdGenerator");
                }
            }

            System.out.println("Using MessageDigest: " + digest.getAlgorithm());
        }

        return digest;
    }

    @SuppressWarnings({"EmptyMethod"})
    @Override
    public final String toString()
    {
        return super.toString();
    }

    protected static final String DEFAULT_ALGORITHM = "MD5";

    private String algorithm = DEFAULT_ALGORITHM;

    private Random random = new SecureRandom();

    private MessageDigest digest = null;

}