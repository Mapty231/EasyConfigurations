package me.tye.utils.instancesTests.parseYamlTest;

import me.tye.ConfigInstance;

import java.util.List;

public enum Config_NotSupported implements ConfigInstance {

EEEEEE(String.class, "e"),
AAAAAAAAAAAAAAAAAAAAAAAA(List.class, "errors"); // Should throw a not supported exception.


Config_NotSupported(Class<?> markedClass, String yamlPath) {
  init(markedClass, yamlPath);
}
}
