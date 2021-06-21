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
package org.deepinthink.groot.minio;

public final class MinioConstants {
  public static final String PREFIX = "magoko.boot.minio";

  public static final String DEFAULT_MINIO_ENDPOINT_ENDPOINT =
      System.getProperty(PREFIX + ".endpoint.endpoint", "http://localhost:9000");
  public static final String DEFAULT_MINIO_ENDPOINT_REGION =
      System.getProperty(PREFIX + ".endpoint.region", "cn-north-1");

  public static final String DEFAULT_MINIO_CREDENTIALS_ACCESS_KEY =
      System.getProperty(PREFIX + ".credentials.access-key", "minio-admin");
  public static final String DEFAULT_MINIO_CREDENTIALS_SECRET_KEY =
      System.getProperty(PREFIX + ".credentials.access-key", "minio-admin");

  private MinioConstants() {}
}
