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
package org.deepinthink.groot.aliyun.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.google.common.base.MoreObjects;
import org.deepinthink.groot.aliyun.AliyunOSSClientBuilder;
import org.deepinthink.groot.aliyun.AliyunOSSClientBuilderConfigurationProvider;
import org.deepinthink.groot.aliyun.condition.ConditionalOnAliyunOSS;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootConfiguration(proxyBeanMethods = false)
@ConditionalOnClass({OSS.class, MoreObjects.class})
@ConditionalOnAliyunOSS
@EnableConfigurationProperties(AliyunOSSProperties.class)
public class AliyunOSSAutoConfiguration {

  @Autowired AliyunOSSProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public AliyunOSSClientBuilderConfigurationProvider aliyunOSSClientBuilderConfigurationProvider(
      @Autowired(required = false) ClientBuilderConfiguration configuration) {
    return () -> MoreObjects.firstNonNull(configuration, aliyunOssClientBuilderConfiguration());
  }

  private ClientBuilderConfiguration aliyunOssClientBuilderConfiguration() {
    ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
    return configuration;
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public AliyunOSSClientBuilder aliyunOssClientBuilder(
      ObjectProvider<AliyunOSSClientBuilderConfigurationProvider> providers) {
    AliyunOSSClientBuilderConfigurationProvider provider =
        providers.getIfAvailable(() -> this::aliyunOssClientBuilderConfiguration);
    AliyunOSSProperties.Endpoint endpoint = properties.getEndpoint();
    AliyunOSSProperties.Credentials credentials = properties.getCredentials();
    AliyunOSSClientBuilder builder =
        new AliyunOSSClientBuilder()
            .endpoint(endpoint.getEndpoint())
            .accessKey(credentials.getAccessKey())
            .secretKey(credentials.getSecretKey())
            .secretToken(credentials.getSecretToken())
            .clientConfiguration(provider.get());
    return builder;
  }
}
