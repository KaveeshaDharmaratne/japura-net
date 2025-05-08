package com.japura.forum.forum_api.codegen.types;

import jakarta.annotation.Generated;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@com.japura.forum.forum_api.codegen.Generated
public class SignupInput {
  private String firstName;

  private String lastName;

  private String faculty;

  private String email;

  private String password;

  private String confirmPassword;

  public SignupInput() {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "SignupInput{firstName='" + firstName + "', lastName='" + lastName + "', faculty='" + faculty + "', email='" + email + "', password='" + password + "', confirmPassword='" + confirmPassword + "'}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SignupInput that = (SignupInput) o;
    return Objects.equals(firstName, that.firstName) &&
        Objects.equals(lastName, that.lastName) &&
        Objects.equals(faculty, that.faculty) &&
        Objects.equals(email, that.email) &&
        Objects.equals(password, that.password) &&
        Objects.equals(confirmPassword, that.confirmPassword);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, faculty, email, password, confirmPassword);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  @Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @com.japura.forum.forum_api.codegen.Generated
  public static class Builder {
    private String firstName;

    private String lastName;

    private String faculty;

    private String email;

    private String password;

    private String confirmPassword;

    public SignupInput build() {
      SignupInput result = new SignupInput();
      result.firstName = this.firstName;
      result.lastName = this.lastName;
      result.faculty = this.faculty;
      result.email = this.email;
      result.password = this.password;
      result.confirmPassword = this.confirmPassword;
      return result;
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

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder confirmPassword(String confirmPassword) {
      this.confirmPassword = confirmPassword;
      return this;
    }
  }
}
