package com.japura.forum.forum_api.codegen.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@com.japura.forum.forum_api.codegen.Generated
public class User {
  private String id;

  private String firstName;

  private String lastName;

  private String faculty;

  private String email;

  public User() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFaculty() {
    return faculty;
  }

  public void setFaculty(String faculty) {
    this.faculty = faculty;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    return "User{id='" + id + "', firstName='" + firstName + "', lastName='" + lastName + "', faculty='" + faculty + "', email='" + email + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User that = (User) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(firstName, that.firstName) &&
        Objects.equals(lastName, that.lastName) &&
        Objects.equals(faculty, that.faculty) &&
        Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, faculty, email);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @com.japura.forum.forum_api.codegen.Generated
  public static class Builder {
    private String id;

    private String firstName;

    private String lastName;

    private String faculty;

    private String email;

    public User build() {
      User result = new User();
      result.id = this.id;
      result.firstName = this.firstName;
      result.lastName = this.lastName;
      result.faculty = this.faculty;
      result.email = this.email;
      return result;
    }

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder faculty(String faculty) {
      this.faculty = faculty;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }
  }
}
