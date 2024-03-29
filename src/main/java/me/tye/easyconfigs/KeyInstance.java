package me.tye.easyconfigs;

import me.tye.easyconfigs.utils.Consts;
import me.tye.easyconfigs.utils.annotations.ExternalUse;
import me.tye.easyconfigs.utils.annotations.InternalUse;
import org.jetbrains.annotations.NotNull;

import static me.tye.easyconfigs.utils.Utils.notNull;
import static me.tye.easyconfigs.utils.Utils.usesDefaultToString;

@ExternalUse
public interface KeyInstance {

/**Stores the string that will get replaced.*/
@InternalUse
String[] toReplace = new String[1];

/**Stores the string to replace {@link #toReplace} with.*/
@InternalUse
String[] replaceWith = new String[1];

/**
 Initiates a KeyInstance.
 * @param toReplace The value that should be replaced. The {@link EasyConfigurations#setKeyCharacters(String, String) values set} that define a key should <b>not</b> be included here.*/
@ExternalUse
default void init(@NotNull String toReplace) {
 this.toReplace[0] = toReplace;
}

/**
 Runs #toString() on the object passed into this method & sets this key to be replaced with with the value returned from the #toString() method.<br>
 In the case that the object pass into this method doesn't override {@link Object#toString()} then the name of the object class is used instead.
 * @param object The object to get the string value of.
 * @return The modified key object.
 */
@ExternalUse
default @NotNull KeyInstance replaceWith(@NotNull Object object) {
  notNull(object, "Replacement object");

  if (usesDefaultToString(object.getClass())) {
    this.replaceWith[0] = object.toString();
  }
  else {
    this.replaceWith[0] = object.getClass().getName();
  }

  return this;
}

/**
 * @return The key value (with starting & ending markers included) that should be replaced.
 */
@InternalUse
default @NotNull String getReplacementValue() {
  return Consts.keyStart + this.replaceWith[0] + Consts.keyEnd;
}

}
