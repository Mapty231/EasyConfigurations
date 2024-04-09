package io.github.tye.easyconfigs.logger;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 This enum contains the possible situations in which EasyConfigurations might output a log message &
 the severity of the output message. */
public enum LogType {
  /**
   This log is output when there are unused paths in the internal config files for the implementing
   program. */
  INTERNAL_UNUSED_PATH(Level.WARNING),

  /**
   This log is output if a none {@link org.yaml.snakeyaml.nodes.ScalarNode ScalarNode} key was
   encountered whilst attempting to parse a yaml file. */
  NOT_SCALAR_KEY_NODE(Level.WARNING);

/**
 The severity of the log message. */
private final @NotNull Level logLevel;

/**
 Constructs a new enum that represents a situation in which EasyConfigurations might output a log
 message & the severity of the output message.
 @param logLevel The severity of the log message. */
LogType(@NotNull Level logLevel) {
  this.logLevel = logLevel;
}

/**
 Gets the severity for this log message.
 @return The severity of this log message. */
public @NotNull Level getLogLevel() {
  return logLevel;
}

}