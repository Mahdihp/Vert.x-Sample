package com.mahdi.starter.models.systemdocument;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mahdi.starter.common.TodoConverter;
import com.mahdi.starter.models.AbstractAuditingEntity;
import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۳/۱۰/۲۰۲۰ & Time: 14:37
 * http://gitlab.com/mahdihp
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@DataObject(generateConverter = true)
public class SystemInfo extends AbstractAuditingEntity implements Serializable {

  public static final String KEY_ID = "id";
  public static final String KEY_SYSTEMNAME = "systemName";

  private String id;
  private String systemName;


  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    TodoConverter.toJson(this, json);
    return json;
  }

  private <T> T getOrElse(T value, T defaultValue) {
    return value == null ? defaultValue : value;
  }

  public SystemInfo merge(SystemInfo todo) {
    return new SystemInfo(id, getOrElse(todo.systemName, systemName));
  }

  public SystemInfo(JsonObject obj) {
    TodoConverter.fromJson(obj, this);
  }

  public SystemInfo(String jsonStr) {
    TodoConverter.fromJson(new JsonObject(jsonStr), this);
  }
}
