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
package org.deepinthink.groot.gcloud.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.StorageOptions;
import com.google.common.base.MoreObjects;
import java.io.FileInputStream;
import lombok.SneakyThrows;
import org.deepinthink.groot.gcloud.GCloudStorageHttpTransportOptionsProvider;
import org.deepinthink.groot.gcloud.GCloudStorageOptionsBuilderCustomizer;
import org.deepinthink.groot.gcloud.condition.ConditionalOnGCloudStorage;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration(proxyBeanMethods = false)
@ConditionalOnClass({StorageOptions.Builder.class, MoreObjects.class})
@ConditionalOnGCloudStorage
@EnableConfigurationProperties(GCloudStorageProperties.class)
public class GCloudStorageAutoConfiguration {

  @Autowired GCloudStorageProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public GCloudStorageHttpTransportOptionsProvider gCloudStorageHttpTransportOptionsProvider(
      @Autowired(required = false) HttpTransportOptions transportOptions) {
    return () -> MoreObjects.firstNonNull(transportOptions, gCloudStorageHttpTransportOptions());
  }

  private HttpTransportOptions gCloudStorageHttpTransportOptions() {
    HttpTransportOptions transportOptions =
        StorageOptions.getDefaultHttpTransportOptions().toBuilder().build();
    return transportOptions;
  }

  @SneakyThrows
  @Bean
  @ConditionalOnMissingBean
  public StorageOptions.Builder gCloudStorageBuilder(
      ObjectProvider<GCloudStorageHttpTransportOptionsProvider> providers,
      ObjectProvider<GCloudStorageOptionsBuilderCustomizer> customizers) {
    GCloudStorageHttpTransportOptionsProvider provider =
        providers.getIfAvailable(() -> this::gCloudStorageHttpTransportOptions);
    StorageOptions.Builder builder =
        StorageOptions.newBuilder()
            .setProjectId(properties.getProjectId())
            .setTransportOptions(provider.get())
            .setCredentials(
                GoogleCredentials.fromStream(new FileInputStream(properties.getCredentials())));
    customizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
    return builder;
  }
}
