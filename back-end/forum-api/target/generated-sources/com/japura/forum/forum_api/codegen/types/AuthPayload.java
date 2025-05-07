package com.japura.forum.forum_api.codegen.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@com.japura.forum.forum_api.codegen.Generated
public class AuthPayload {
  private String token;

  private User user;

  public AuthPayload() {
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "AuthPayload{token='" + token + "', user='" + user + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthPayload that = (AuthPayload) o;
    return Objects.equals(token, that.token) &&
        Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, user);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @com.japura.forum.forum_api.codegen.Generated
  public static class Builder {
    private String token;

    private User user;

    public AuthPayload build() {
      AuthPayload result = new AuthPayload();
      result.token = this.token;
      result.user = this.user;
      return result;
    }

    public Builder token(String token) {
      this.token = token;
      return this;
    }

    public Builder user(User user) {
      this.user = user;
      return this;
    }
  }
}
