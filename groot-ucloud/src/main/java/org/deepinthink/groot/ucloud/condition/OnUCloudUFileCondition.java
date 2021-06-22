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
package org.deepinthink.groot.ucloud.condition;

import org.deepinthink.groot.ucloud.UCloudUFileConstants;
import org.deepinthink.groot.ucloud.config.UCloudUFileProperties.BucketLocalAuthorization;
import org.deepinthink.groot.ucloud.config.UCloudUFileProperties.ObjectLocalAuthorization;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

class OnUCloudUFileCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(
      ConditionContext context, AnnotatedTypeMetadata metadata) {
    ConditionMessage.Builder builder =
        ConditionMessage.forCondition(ConditionalOnUCloudUFile.class);
    try {
      BindResult<BucketLocalAuthorization> bucketRequired =
          Binder.get(context.getEnvironment())
              .bind(
                  UCloudUFileConstants.PREFIX + ".bucket-local-authorization",
                  BucketLocalAuthorization.class);
      BindResult<ObjectLocalAuthorization> objectRequired =
          Binder.get(context.getEnvironment())
              .bind(
                  UCloudUFileConstants.PREFIX + ".object-local-authorization",
                  ObjectLocalAuthorization.class);
      if (bucketRequired.isBound() && objectRequired.isBound()) {
        BucketLocalAuthorization bucketAuth = bucketRequired.get();
        ObjectLocalAuthorization objectAuth = objectRequired.get();
        if (StringUtils.hasLength(bucketAuth.getPublicKey())
            && StringUtils.hasLength(bucketAuth.getPrivateKey())
            && StringUtils.hasLength(objectAuth.getPublicKey())
            && StringUtils.hasLength(objectAuth.getPrivateKey())) {
          return ConditionOutcome.match();
        }
      }
    } catch (BindException ignored) {
    }
    return ConditionOutcome.noMatch(
        builder.because("Missing BucketLocalAuthorization or ObjectLocalAuthorization"));
  }
}
