/*
 * Copyright 2017 JessYan
 *
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
 */
package com.endless.study.baselibrary.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 处理数据
 * @author haosiyuan
 * @date 2019/4/1 2:38 PM
 */
public class UtilDataHelper {

    private UtilDataHelper() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 字节转字符串
     * @param in
     * @return
     * @throws IOException
     */
    public static String byteToString(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int num = 0;
        while ((num = in.read(buf)) != -1) {
            out.write(buf, 0, buf.length);
        }
        String result = out.toString();
        out.close();
        return result;
    }
}
