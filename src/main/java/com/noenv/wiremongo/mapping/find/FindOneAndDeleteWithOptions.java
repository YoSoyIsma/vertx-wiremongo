package com.noenv.wiremongo.mapping.find;

import com.noenv.wiremongo.mapping.Command;
import com.noenv.wiremongo.matching.Matcher;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.FindOptions;

import static com.noenv.wiremongo.matching.EqualsMatcher.equalTo;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class FindOneAndDeleteWithOptions extends FindOneAndDeleteBase<FindOneAndDeleteWithOptions> {

  public static class FindOneAndDeleteWithOptionsCommand extends FindOneAndDeleteBaseCommand {

    private FindOptions options;

    public FindOneAndDeleteWithOptionsCommand(String collection, JsonObject query, FindOptions options) {
      super("findOneAndDeleteWithOptions", collection, query);
      this.options = options;
    }

    @Override
    public String toString() {
      return super.toString() + ", options: " + (options == null ? "null" : options.toJson().encode());
    }
  }

  private Matcher<FindOptions> options;

  public FindOneAndDeleteWithOptions() {
    this("findOneAndDeleteWithOptions");
  }

  public FindOneAndDeleteWithOptions(String method) {
    super(method);
  }

  public FindOneAndDeleteWithOptions(JsonObject json) {
    super(json);
    this.options = Matcher.create(json.getJsonObject("options"), j -> new FindOptions((JsonObject) j), FindOptions::toJson);
  }

  @Override
  public boolean matches(Command cmd) {
    if (!super.matches(cmd)) {
      return false;
    }
    if (!(cmd instanceof FindOneAndDeleteWithOptionsCommand)) {
      return false;
    }
    FindOneAndDeleteWithOptionsCommand c = (FindOneAndDeleteWithOptionsCommand) cmd;
    return options == null || options.matches(c.options);
  }

  public FindOneAndDeleteWithOptions withOptions(FindOptions options) {
    return withOptions(equalTo(options));
  }

  public FindOneAndDeleteWithOptions withOptions(Matcher<FindOptions> options) {
    this.options = options;
    return self();
  }
}