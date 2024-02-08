package me.tye.utils.instancesTests.parseYamlTest;

import me.tye.ConfigInstance;

public enum Config_HasNull implements ConfigInstance {
  empty(String.class, "empties"), // Will fail due to null value
  evenMoreEmpty(String[].class, "evenMoreEmpty");

Config_HasNull(Class<?> markedClazz, String yamlPath) {
  init(markedClazz, yamlPath);
}
}
