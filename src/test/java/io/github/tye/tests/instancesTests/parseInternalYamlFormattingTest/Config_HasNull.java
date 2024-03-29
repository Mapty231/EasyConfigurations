package io.github.tye.tests.instancesTests.parseInternalYamlFormattingTest;

import io.github.tye.easyconfigs.instances.ConfigInstance;

public enum Config_HasNull implements ConfigInstance {
  empty(String.class, "empties"), // Will fail due to null value
  evenMoreEmpty(String[].class, "evenMoreEmpty");

Config_HasNull(Class<?> markedClazz, String yamlPath) {
  init(markedClazz, yamlPath);
}
}
