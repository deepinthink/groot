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
package org.deepinthink.groot.ucloud.config;

import static org.deepinthink.groot.ucloud.UCloudUFileConstants.DEFAULT_UCLOUD_UFILE_OBJECT_CONFIG_PROXY_SUFFIX;
import static org.deepinthink.groot.ucloud.UCloudUFileConstants.DEFAULT_UCLOUD_UFILE_OBJECT_CONFIG_REGION;

import lombok.Data;
import org.deepinthink.groot.ucloud.UCloudUFileConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = UCloudUFileConstants.PREFIX)
public class UCloudUFileProperties {

  private final BucketLocalAuthorization bucketLocalAuthorization = new BucketLocalAuthorization();
  private final ObjectLocalAuthorization objectLocalAuthorization = new ObjectLocalAuthorization();
  private final ObjectConfig objectConfig = new ObjectConfig();

  @Data
  public static class BucketLocalAuthorization {
    private String publicKey;
    private String privateKey;
  }

  @Data
  public static class ObjectLocalAuthorization {
    private String publicKey;
    private String privateKey;
  }

  @Data
  public static class ObjectConfig {
    private String region = DEFAULT_UCLOUD_UFILE_OBJECT_CONFIG_REGION;
    private String proxySuffix = DEFAULT_UCLOUD_UFILE_OBJECT_CONFIG_PROXY_SUFFIX;
  }
}
