package me.tye.utils.instancesTests.parseYamlTest;

import me.tye.LangInstance;

public enum Lang_General implements LangInstance {
  lyrics_never("lyrics.never"),
  lyrics_give("lyrics.give"),
  lyrics_up("lyrics.up"),

  hehe("or.this.works"),

  basics("backToBasics");

Lang_General(String yamlPath) {
  init(yamlPath);
}
}
