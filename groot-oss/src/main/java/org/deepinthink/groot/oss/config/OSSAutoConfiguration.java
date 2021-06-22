package org.deepinthink.groot.oss.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties(OSSProperties.class)
public class OSSAutoConfiguration {}
