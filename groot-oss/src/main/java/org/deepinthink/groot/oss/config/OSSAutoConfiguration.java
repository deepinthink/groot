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

import org.deepinthink.groot.aliyun.config.AliyunOSSAutoConfiguration;
import org.deepinthink.groot.aws.config.AmazonS3AutoConfiguration;
import org.deepinthink.groot.gcloud.config.GCloudStorageAutoConfiguration;
import org.deepinthink.groot.minio.config.MinioAutoConfiguration;
import org.deepinthink.groot.oss.aliyun.AliyunOSSConfiguration;
import org.deepinthink.groot.oss.aws.AmazonS3Configuration;
import org.deepinthink.groot.oss.gcloud.GCloudStorageConfiguration;
import org.deepinthink.groot.oss.minio.MinioConfiguration;
import org.deepinthink.groot.oss.qcloud.QCloudCOSConfiguration;
import org.deepinthink.groot.oss.ucloud.UCloudUFileConfiguration;
import org.deepinthink.groot.qcloud.config.QCloudCOSAutoConfiguration;
import org.deepinthink.groot.ucloud.config.UCloudUFileAutoConfiguration;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties(OSSProperties.class)
@AutoConfigureAfter({
  AliyunOSSAutoConfiguration.class,
  AmazonS3AutoConfiguration.class,
  org.deepinthink.groot.aws.v2.config.AmazonS3AutoConfiguration.class,
  GCloudStorageAutoConfiguration.class,
  MinioAutoConfiguration.class,
  QCloudCOSAutoConfiguration.class,
  UCloudUFileAutoConfiguration.class
})
@Import({
  AliyunOSSConfiguration.class,
  AmazonS3Configuration.class,
  org.deepinthink.groot.oss.aws.v2.AmazonS3Configuration.class,
  GCloudStorageConfiguration.class,
  MinioConfiguration.class,
  QCloudCOSConfiguration.class,
  UCloudUFileConfiguration.class
})
public class OSSAutoConfiguration {}
