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
package org.deepinthink.groot.qcloud;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.COSCredentials;

public final class QCloudCOSClientBuilder {
  private COSCredentials credentials;
  private ClientConfig config;

  public QCloudCOSClientBuilder() {}

  public QCloudCOSClientBuilder credentials(COSCredentials credentials) {
    this.credentials = credentials;
    return this;
  }

  public QCloudCOSClientBuilder clientConfig(ClientConfig config) {
    this.config = config;
    return this;
  }

  public COSClient build() {
    return new COSClient(this.credentials, this.config);
  }
}