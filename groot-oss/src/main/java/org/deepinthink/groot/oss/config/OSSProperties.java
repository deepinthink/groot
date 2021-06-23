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
package org.deepinthink.groot.oss.config;

import static org.deepinthink.groot.oss.OSSConstants.DEFAULT_OSS_DRIVER;

import lombok.Data;
import org.deepinthink.groot.oss.OSSConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = OSSConstants.PREFIX)
public class OSSProperties {

  private OSSDriver driver = DEFAULT_OSS_DRIVER;

  public enum OSSDriver {
    ALIYUN_OSS,
    AMAZON_S3,
    GCLOUD_STORAGE,
    MINIO,
    QCLOUD_COS,
    UCLOUD_UFILE
  }
}
