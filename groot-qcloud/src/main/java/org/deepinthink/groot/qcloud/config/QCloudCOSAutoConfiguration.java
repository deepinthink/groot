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

import com.google.common.base.MoreObjects;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import org.deepinthink.groot.qcloud.QCloudCOSClientBuilder;
import org.deepinthink.groot.qcloud.QCloudCOSClientConfigProvider;
import org.deepinthink.groot.qcloud.condition.ConditionOnQCloudCOS;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootConfiguration(proxyBeanMethods = false)
@ConditionalOnClass({COSClient.class, MoreObjects.class})
@ConditionOnQCloudCOS
@EnableConfigurationProperties(QCloudCOSProperties.class)
public class QCloudCOSAutoConfiguration {

  @Autowired QCloudCOSProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public QCloudCOSClientConfigProvider qCloudCOSClientConfigProvider(
      @Autowired(required = false) ClientConfig config) {
    return () -> MoreObjects.firstNonNull(config, qcloudCOSClientConfig());
  }

  private ClientConfig qcloudCOSClientConfig() {
    ClientConfig config = new ClientConfig();
    return config;
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public QCloudCOSClientBuilder qCloudCOSClientBuilder(
      ObjectProvider<QCloudCOSClientConfigProvider> providers) {
    QCloudCOSClientConfigProvider provider =
        providers.getIfAvailable(() -> this::qcloudCOSClientConfig);
    ClientConfig config = provider.get();
    config.setRegion(new Region(properties.getEndpoint().getRegion()));
    QCloudCOSProperties.Credentials credentials = properties.getCredentials();
    return new QCloudCOSClientBuilder()
        .credentials(
            new BasicCOSCredentials(credentials.getAccessKey(), credentials.getSecretKey()))
        .clientConfig(provider.get());
  }
}
