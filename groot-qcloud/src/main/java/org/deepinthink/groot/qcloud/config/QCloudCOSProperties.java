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
package org.deepinthink.groot.qcloud.config;

import static org.deepinthink.groot.qcloud.QCloudCOSConstants.DEFAULT_QCLOUD_COS_CREDENTIALS_ACCESS_KEY;
import static org.deepinthink.groot.qcloud.QCloudCOSConstants.DEFAULT_QCLOUD_COS_CREDENTIALS_SECRET_KEY;
import static org.deepinthink.groot.qcloud.QCloudCOSConstants.DEFAULT_QCLOUD_COS_ENDPOINT_REGION;

import lombok.Data;
import org.deepinthink.groot.qcloud.QCloudCOSConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = QCloudCOSConstants.PREFIX)
public class QCloudCOSProperties {

  private final Endpoint endpoint = new Endpoint();
  private final Credentials credentials = new Credentials();

  @Data
  public static class Endpoint {
    private String region = DEFAULT_QCLOUD_COS_ENDPOINT_REGION;
  }

  @Data
  public static class Credentials {
    private String accessKey = DEFAULT_QCLOUD_COS_CREDENTIALS_ACCESS_KEY;
    private String secretKey = DEFAULT_QCLOUD_COS_CREDENTIALS_SECRET_KEY;
  }
}
