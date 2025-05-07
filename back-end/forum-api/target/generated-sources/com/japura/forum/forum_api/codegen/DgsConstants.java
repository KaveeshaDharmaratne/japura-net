package com.japura.forum.forum_api.codegen;

import java.lang.String;

@jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@Generated
public class DgsConstants {
  public static final String QUERY_TYPE = "Query";

  public static final String MUTATION_TYPE = "Mutation";

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class AUTHPAYLOAD {
    public static final String TYPE_NAME = "AuthPayload";

    public static final String Token = "token";

    public static final String User = "user";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class USER {
    public static final String TYPE_NAME = "User";

    public static final String Id = "id";

    public static final String FirstName = "firstName";

    public static final String LastName = "lastName";

    public static final String Faculty = "faculty";

    public static final String Email = "email";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class QUERY {
    public static final String TYPE_NAME = "Query";

    public static final String Me = "me";
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class MUTATION {
    public static final String TYPE_NAME = "Mutation";

    public static final String Signup = "signup";

    @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
    @Generated
    public static class SIGNUP_INPUT_ARGUMENT {
      public static final String Input = "input";
    }
  }

  @jakarta.annotation.Generated("com.netflix.graphql.dgs.codegen.CodeGen")
  @Generated
  public static class SIGNUPINPUT {
    public static final String TYPE_NAME = "SignupInput";

    public static final String FirstName = "firstName";

    public static final String LastName = "lastName";

    public static final String Faculty = "faculty";

    public static final String Email = "email";

    public static final String Password = "password";

    public static final String ConfirmPassword = "confirmPassword";
  }
}
