package com.yosua.homie.dao;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class UserBuilder
    implements Cloneable {
  protected UserBuilder self;
  protected String value$name$java$lang$String;
  protected boolean isSet$name$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;
  protected String value$password$java$lang$String;
  protected boolean isSet$password$java$lang$String;
  protected String value$phoneNumber$java$lang$String;
  protected boolean isSet$phoneNumber$java$lang$String;
  protected String[] value$hubsID$java$lang$String$L;
  protected boolean isSet$hubsID$java$lang$String$L;

  /**
   * Creates a new {@link UserBuilder}.
   */
  public UserBuilder() {
    self = (UserBuilder)this;
  }

  /**
   * Sets the default value for the {@link User#name} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserBuilder withName(String value) {
    this.value$name$java$lang$String = value;
    this.isSet$name$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link User#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link User#password} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserBuilder withPassword(String value) {
    this.value$password$java$lang$String = value;
    this.isSet$password$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link User#phoneNumber} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserBuilder withPhoneNumber(String value) {
    this.value$phoneNumber$java$lang$String = value;
    this.isSet$phoneNumber$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link User#hubsID} property.
   *
   * @param value the default value
   * @return this builder
   */
  public UserBuilder withHubsID(String[] value) {
    this.value$hubsID$java$lang$String$L = value;
    this.isSet$hubsID$java$lang$String$L = true;
    return self;
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  @Override
  public Object clone() {
    try {
      UserBuilder result = (UserBuilder)super.clone();
      result.self = result;
      return result;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.getMessage());
    }
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  public UserBuilder but() {
    return (UserBuilder)clone();
  }

  /**
   * Creates a new {@link User} based on this builder's settings.
   *
   * @return the created User
   */
  public User build() {
    try {
      User result = new User();
      if (isSet$name$java$lang$String) {
        result.setName(value$name$java$lang$String);
      }
      if (isSet$email$java$lang$String) {
        result.setEmail(value$email$java$lang$String);
      }
      if (isSet$password$java$lang$String) {
        result.setPassword(value$password$java$lang$String);
      }
      if (isSet$phoneNumber$java$lang$String) {
        result.setPhoneNumber(value$phoneNumber$java$lang$String);
      }
      if (isSet$hubsID$java$lang$String$L) {
        result.setHubsID(value$hubsID$java$lang$String$L);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
