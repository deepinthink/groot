package org.deepinthink.groot.oss;

import org.deepinthink.groot.oss.config.OSSProperties.OSSDriver;

public final class OSSConstants {
  public static final String PREFIX = "groot.oss";

  public static final OSSDriver DEFAULT_OSS_DRIVER =
      OSSDriver.valueOf(System.getProperty(PREFIX + "driver", "MINIO"));

  private OSSConstants() {}
}
