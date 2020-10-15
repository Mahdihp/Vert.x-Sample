package com.mahdi.starter.common;

import com.mahdi.starter.models.systemdocument.SystemInfo;
import io.vertx.core.json.JsonObject;

/**
 * Converter for {@link SystemInfo}.
 * <p>
 * NOTE: This class has been automatically generated from the {@link SystemInfo} original class using Vert.x codegen.
 */
public class TodoConverter {

  public static void fromJson(JsonObject json, SystemInfo obj) {
    if (json.getValue("id") instanceof String) {
      obj.setId((String) json.getValue("id"));
    }
    if (json.getValue("systemName") instanceof String) {
      obj.setSystemName((String) json.getValue("systemName"));
    }
  }

  public static void toJson(SystemInfo obj, JsonObject json) {
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getSystemName() != null) {
      json.put("systemName", obj.getSystemName());
    }
  }
}
