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
package org.deepinthink.groot.aws.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.google.common.base.MoreObjects;
import org.deepinthink.groot.aws.AmazonS3ClientBuilderCustomizer;
import org.deepinthink.groot.aws.AmazonS3ClientConfigurationProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootConfiguration(proxyBeanMethods = false)
@ConditionalOnClass({AmazonS3.class, MoreObjects.class})
@EnableConfigurationProperties(AmazonS3Properties.class)
public class AmazonS3AutoConfiguration {

  @Autowired AmazonS3Properties properties;

  @Bean
  @ConditionalOnMissingBean
  public AmazonS3ClientConfigurationProvider amazonS3ClientConfigurationProvider(
      @Autowired(required = false) ClientConfiguration clientConfiguration) {
    return () -> MoreObjects.firstNonNull(clientConfiguration, amazonS3ClientConfiguration());
  }

  private ClientConfiguration amazonS3ClientConfiguration() {
    ClientConfiguration configuration = new ClientConfiguration();
    return configuration;
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public AmazonS3ClientBuilder amazonS3ClientBuilder(
      ObjectProvider<AmazonS3ClientConfigurationProvider> providers,
      ObjectProvider<AmazonS3ClientBuilderCustomizer> customizers) {
    AmazonS3ClientConfigurationProvider provider =
        providers.getIfAvailable(() -> this::amazonS3ClientConfiguration);
    AmazonS3Properties.Endpoint endpoint = properties.getEndpoint();
    AmazonS3Properties.Credentials credentials = properties.getCredentials();
    AmazonS3ClientBuilder builder =
        AmazonS3ClientBuilder.standard()
            .withClientConfiguration(provider.get())
            .withEndpointConfiguration(
                new EndpointConfiguration(endpoint.getEndpoint(), endpoint.getRegion()))
            .withCredentials(
                new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(
                        credentials.getAccessKey(), credentials.getSecretKey())));
    customizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
    return builder;
  }
}
