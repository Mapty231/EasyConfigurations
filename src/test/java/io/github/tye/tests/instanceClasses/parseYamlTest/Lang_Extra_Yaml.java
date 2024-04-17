package io.github.tye.tests.instanceClasses.parseYamlTest;

import io.github.tye.easyconfigs.instances.LangInstance;

public enum Lang_Extra_Yaml implements LangInstance {
  lang0("lang0"),
  lang1("lang1"),
  lang2("lang2"),
  // Should be warned for extra lang3 in lang.
  ;

Lang_Extra_Yaml(String yamlPath) {
  init(yamlPath);
}
}