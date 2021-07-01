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
package org.deepinthink.groot.oss.minio;

import org.deepinthink.groot.minio.config.MinioProperties;
import org.deepinthink.groot.oss.condition.ConditionalOnOSSDriver;
import org.deepinthink.groot.oss.config.OSSProperties.OSSDriver;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@SpringBootConfiguration(proxyBeanMethods = false)
@ConditionalOnBean(MinioProperties.class)
@ConditionalOnOSSDriver(OSSDriver.MINIO)
public class MinioConfiguration {}
