/*
 * Copyright (c) 2021-present deepinthink. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deepinthink.groot.aliyun;

public final class AliyunOSSConstants {
  public static final String PREFIX = "groot.aliyun.oss";

  public static final String DEFAULT_ALIYUN_OSS_ENDPOINT_ENDPOINT =
      System.getProperty(PREFIX + ".endpoint.endpoint", "http://oss-cn-hangzhou.aliyuncs.com");

  private AliyunOSSConstants() {}
}
