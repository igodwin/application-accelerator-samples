package com.java.example.tanzu.wherefordinner.config;

import org.springframework.cloud.bindings.Binding;
import org.springframework.cloud.bindings.Bindings;
import org.springframework.cloud.bindings.boot.BindingsPropertiesProcessor;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Map;

public class GemFireClusterBindingsPropertiesProcessor implements BindingsPropertiesProcessor {
  public static final String TYPE = "gemfire";

  @Override
  public void process(Environment environment, Bindings bindings, Map<String, Object> properties) {
    if (!environment.getProperty("org.springframework.cloud.bindings.boot.gemfire.enable", Boolean.class, true)) {
      return;
    }
    List<Binding> myBindings = bindings.filterBindings(TYPE);
    if (myBindings.size() == 0) {
      return;
    }
    properties.put("spring.data.gemfire.locator.host", myBindings.get(0).getSecret().get("host"));
    properties.put("spring.data.gemfire.locator.port", myBindings.get(0).getSecret().get("port"));
  }
}
