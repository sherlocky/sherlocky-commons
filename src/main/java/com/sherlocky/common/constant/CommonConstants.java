/*
* Copyright 2019 Sherlocky
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
package com.sherlocky.common.constant;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 常用的一些常量
 * @author: zhangcx
 * @date: 2019/8/6 10:33
 */
public abstract class CommonConstants {
    /** 默认字符集，utf-8 */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    public static final String DEFAULT_CHARSET_NAME = DEFAULT_CHARSET.name();
    /** 默认的http协议 */
    public static final String DEFAULT_SCHEMA = "http";
    /** http协议分隔符 */
    public static final String SCHEMA_SEPARATOR = "://";

    // http 协议头前缀
    public static final String HTTP_SCHEMA_PREFIX = "http://";

    // https 协议头前缀
    public static final String HTTPS_SCHEMA_PREFIX = "https://";

    /**
     * 成功时返回 1
     */
    public static final String SUCCESS_CODE = "1";
    /**
     * 失败返回 -1
     */
    public static final String ERROR_CODE = "-1";

    public static final String DEFAULT_ERROR_MESSAGE = "未知错误";
}
