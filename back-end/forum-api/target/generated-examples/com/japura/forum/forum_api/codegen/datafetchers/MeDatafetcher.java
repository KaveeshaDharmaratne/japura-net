package com.japura.forum.forum_api.codegen.datafetchers;

import com.japura.forum.forum_api.codegen.types.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import jakarta.annotation.Generated;

@Generated("com.netflix.graphql.dgs.codegen.CodeGen")
@com.japura.forum.forum_api.codegen.Generated
@DgsComponent
public class MeDatafetcher {
  @DgsData(
      parentType = "Query",
      field = "me"
  )
  public User getMe(DataFetchingEnvironment dataFetchingEnvironment) {
    return null;
  }
}
