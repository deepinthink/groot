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
package org.deepinthink.groot.oss.condition;

import static org.deepinthink.groot.oss.OSSConstants.PREFIX;

import java.util.Map;
import java.util.Objects;
import org.deepinthink.groot.oss.config.OSSProperties;
import org.deepinthink.groot.oss.config.OSSProperties.OSSDriver;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

class OnOSSDriverCondition extends SpringBootCondition {

  @Override
  public ConditionOutcome getMatchOutcome(
      ConditionContext context, AnnotatedTypeMetadata metadata) {
    Map<String, Object> attributes =
        Objects.requireNonNull(
            metadata.getAnnotationAttributes(ConditionalOnOSSDriver.class.getName()));
    OSSDriver driver = (OSSDriver) attributes.get("value");
    return getMatchOutcome(context.getEnvironment(), driver);
  }

  private ConditionOutcome getMatchOutcome(Environment environment, OSSDriver driver) {
    String name = driver.name();
    ConditionMessage.Builder builder = ConditionMessage.forCondition(ConditionalOnOSSDriver.class);
    try {
      BindResult<OSSProperties> required =
          Binder.get(environment).bind(PREFIX, OSSProperties.class);
      if (required.isBound()) {
        if (required.get().getDriver() == driver) {
          return ConditionOutcome.match(builder.foundExactly(name));
        }
      }
    } catch (BindException ignored) {
    }
    return ConditionOutcome.noMatch(builder.didNotFind(name).atAll());
  }
}
