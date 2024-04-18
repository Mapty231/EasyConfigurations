package io.github.tye.easyconfigs.instances;

import io.github.tye.easyconfigs.annotations.InternalUse;
import io.github.tye.easyconfigs.exceptions.ConfigurationException;
import io.github.tye.easyconfigs.exceptions.DefaultConfigurationException;
import io.github.tye.easyconfigs.exceptions.NotInitiatedException;
import io.github.tye.easyconfigs.internalConfigs.Lang;
import io.github.tye.easyconfigs.logger.LogType;
import io.github.tye.easyconfigs.yamls.ReadYaml;
import io.github.tye.easyconfigs.yamls.WriteYaml;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static io.github.tye.easyconfigs.logger.EasyConfigurationsDefaultLogger.logger;

/**
 Contains information about a parsed yaml file. */
@InternalUse
public final class InstanceHandler {

/**
 The class the object stored in a Yaml should be parsed as. */
@InternalUse
@NotNull
public static final HashMap<Instance, Class<?>> assignedClass = new HashMap<>();

/**
 The path to parse the object from in a Yaml. */
@InternalUse
@NotNull
public static final HashMap<Instance, String> yamlPath = new HashMap<>();


/**
 The string to the location of the internal configuration file. */
@InternalUse
private @NotNull String path = "";

/**
 The yaml parsed from a default file. */
@InternalUse
private final @Nullable ReadYaml yaml;


@InternalUse
public InstanceHandler() {
  this.yaml = null;
}

@InternalUse
private InstanceHandler(@NotNull ReadYaml yaml, @NotNull String path) {
  this.yaml = yaml;
  this.path = path;
}


/**
 Creates a new instance of the given yaml. This method also performs checks for unused keys, missing
 keys, &amp; other incorrect configurations.
 @param path  The path to the default yaml file.
 @param clazz The class of the enum that represents the default yaml file.
 @return An {@link InstanceHandler} that contains necessary data about the initialized instance.
 @throws IOException                   If there was an error reading the input stream, or if the
 given path doesn't lead to any files.
 @throws DefaultConfigurationException If:
 <p>
 - There was an error parsing the given inputStream as a yaml.
 <p>
 - There is a key in the yaml enum that isn't in the parsed
 yaml.
 <p>
 - The yaml enum has marked a value as a class
 EasyConfigurations doesn't support.
 <p>
 - A value can't be parsed as the class it is marked as in the
 yaml enum. */
@InternalUse
public static @NotNull InstanceHandler defaultYaml(@NotNull String path, @NotNull Class<? extends Instance> clazz) throws IOException, DefaultConfigurationException {
  try (InputStream inputStream = clazz.getResourceAsStream(path)) {
    if (inputStream == null) throw new IOException(Lang.configNotReadable(path));

    // Initializes the yaml
    ReadYaml yaml = new ReadYaml(inputStream);
    warnUnusedKeys(yaml, clazz, path);
    yaml.parseValues(clazz, path);

    return new InstanceHandler(yaml, path);
  }
  catch (ConfigurationException exception) {
    if (exception instanceof DefaultConfigurationException) {
      throw (DefaultConfigurationException) exception;
    }

    throw new DefaultConfigurationException(exception.getMessage(), exception.getCause());
  }
}

public static InstanceHandler externalYaml(@NotNull String externalPath, @NotNull String path, @NotNull Class<? extends Instance> clazz) throws IOException, ConfigurationException {
  InstanceHandler instanceHandler = defaultYaml(path, clazz);

  WriteYaml yaml;
  try (InputStream inputStream = clazz.getResourceAsStream(path)) {
    if (inputStream == null) throw new IOException(Lang.configNotReadable(path));

    // Initializes the yaml
    yaml = new WriteYaml(inputStream);
    warnUnusedKeys(yaml, clazz, path);
  }

  //TODO: placeholder
  return instanceHandler;
}


/**
 Outputs a warning message to the logger if the given map contains keys that aren't used by the given
 internal instance. */
@InternalUse
private static void warnUnusedKeys(ReadYaml yaml, Class<? extends Instance> clazz, String path) {
  // Checks if any default values in the file are missing from the enum.
  for (String yamlPath : yaml.getKeys()) {

    // Checks if the enum contains the key outlined in the file.
    boolean contains = false;

    for (Instance instanceEnum : clazz.getEnumConstants()) {
      if (!yamlPath.equals(instanceEnum.getYamlPath())) continue;

      contains = true;
      break;
    }

    // Logs a warning if there's an unused path.
    if (contains) continue;

    logger.log(LogType.INTERNAL_UNUSED_PATH, Lang.unusedYamlPath(yamlPath, path));
  }
}


/**
 Gets the value at the given key from the parsed yaml.
 @param key The key to get the value at.
 @return The value at the given key.
 @throws NotInitiatedException If the value hasn't been initiated. */
@InternalUse
public @NotNull Object getValue(String key) throws NotInitiatedException {
  if (yaml == null) throw new NotInitiatedException(path);

  Object value = yaml.getValue(key);
  // Shouldn't get thrown as this method is only called from instances.
  if (value == null) throw new NotInitiatedException(key);

  return value;
}


}
