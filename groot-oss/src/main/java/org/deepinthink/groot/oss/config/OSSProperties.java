package org.deepinthink.groot.oss.config;

import lombok.Data;
import org.deepinthink.groot.oss.OSSConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.deepinthink.groot.oss.OSSConstants.DEFAULT_OSS_DRIVER;

@Data
@ConfigurationProperties(prefix = OSSConstants.PREFIX)
public class OSSProperties {

  private OSSDriver driver = DEFAULT_OSS_DRIVER;

  public enum OSSDriver {
    AMAZON_S3,
    MINIO,
    QCLOUD_COS,
    UCLOUD_UFILE
  }
}
