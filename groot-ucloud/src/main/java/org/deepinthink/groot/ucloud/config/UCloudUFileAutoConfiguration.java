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

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.bucket.BucketApiBuilder;
import cn.ucloud.ufile.api.object.ObjectApiBuilder;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import com.google.common.base.MoreObjects;
import org.deepinthink.groot.ucloud.UCloudUFileClientConfigProvider;
import org.deepinthink.groot.ucloud.UCloudUFileObjectConfigProvider;
import org.deepinthink.groot.ucloud.condition.ConditionalOnUCloudUFile;
import org.deepinthink.groot.ucloud.config.UCloudUFileProperties.BucketLocalAuthorization;
import org.deepinthink.groot.ucloud.config.UCloudUFileProperties.ObjectLocalAuthorization;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration(proxyBeanMethods = false)
@ConditionalOnClass({UfileClient.class, MoreObjects.class})
@ConditionalOnUCloudUFile
@EnableConfigurationProperties(UCloudUFileProperties.class)
public class UCloudUFileAutoConfiguration {
  private final UCloudUFileProperties properties;
  public final BucketAuthorization bucketAuthorization;
  public final ObjectAuthorization objectAuthorization;

  @Autowired
  public UCloudUFileAutoConfiguration(
      UCloudUFileProperties properties, ObjectProvider<UCloudUFileClientConfigProvider> providers) {
    this.properties = properties;
    BucketLocalAuthorization bucketLocalAuthorization = properties.getBucketLocalAuthorization();
    ObjectLocalAuthorization objectLocalAuthorization = properties.getObjectLocalAuthorization();
    this.bucketAuthorization =
        new UfileBucketLocalAuthorization(
            bucketLocalAuthorization.getPublicKey(), bucketLocalAuthorization.getPrivateKey());
    this.objectAuthorization =
        new UfileObjectLocalAuthorization(
            objectLocalAuthorization.getPublicKey(), objectLocalAuthorization.getPrivateKey());
    UCloudUFileClientConfigProvider provider =
        providers.getIfAvailable(() -> UfileClient.Config::new);
    UfileClient.configure(provider.get());
  }

  @Bean
  @ConditionalOnMissingBean
  public UCloudUFileObjectConfigProvider uCloudUFileObjectConfigProvider(
      @Autowired(required = false) ObjectConfig objectConfig) {
    return () -> MoreObjects.firstNonNull(objectConfig, uFileObjectConfig());
  }

  private ObjectConfig uFileObjectConfig() {
    return new ObjectConfig(
        this.properties.getObjectConfig().getRegion(),
        this.properties.getObjectConfig().getProxySuffix());
  }

  @Bean
  @ConditionalOnMissingBean
  public BucketApiBuilder uFileBucketApiBuilder() {
    return UfileClient.bucket(this.bucketAuthorization);
  }

  @Bean
  @ConditionalOnMissingBean
  public ObjectApiBuilder uFileObjectApiBuilder(
      ObjectProvider<UCloudUFileObjectConfigProvider> providers) {
    UCloudUFileObjectConfigProvider provider =
        providers.getIfAvailable(() -> this::uFileObjectConfig);
    return UfileClient.object(this.objectAuthorization, provider.get());
  }
}
