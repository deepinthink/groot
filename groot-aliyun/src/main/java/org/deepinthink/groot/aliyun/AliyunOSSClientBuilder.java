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

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public final class AliyunOSSClientBuilder {
  private String endpoint;
  private String accessKey;
  private String secretKey;
  private String secretToken;
  private ClientBuilderConfiguration config;

  public AliyunOSSClientBuilder() {}

  public AliyunOSSClientBuilder endpoint(String endpoint) {
    this.endpoint = endpoint;
    return this;
  }

  public AliyunOSSClientBuilder accessKey(String accessKey) {
    this.accessKey = accessKey;
    return this;
  }

  public AliyunOSSClientBuilder secretKey(String secretKey) {
    this.secretKey = secretKey;
    return this;
  }

  public AliyunOSSClientBuilder secretToken(String secretToken) {
    this.secretToken = secretToken;
    return this;
  }

  public AliyunOSSClientBuilder clientConfiguration(ClientBuilderConfiguration config) {
    this.config = config;
    return this;
  }

  public OSS build() {
    return new OSSClientBuilder()
        .build(this.endpoint, this.accessKey, this.secretKey, this.secretToken, this.config);
  }
}
