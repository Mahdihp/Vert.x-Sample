package com.mahdi.starter.models.systemdocument;

import com.mahdi.starter.common.ResponseBody;
import com.mahdi.starter.verticle.MainAppVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۵/۱۰/۲۰۲۰ & Time: 10:16
 * http://gitlab.com/mahdihp
 */

public class SystemInfoController {

  private final Vertx vertx;
  private final JsonObject config;
  private SystemInfoServiceImpl systemInfoService;
  private static final Logger logger = LoggerFactory.getLogger(SystemInfoController.class);

  public SystemInfoController(Vertx vertx, JsonObject config) {
    this.vertx = vertx;
    this.config = config;
    systemInfoService = new SystemInfoServiceImpl(vertx, config);
  }

  public void ping(RoutingContext ctx) {
    ResponseBody.sendResponse(ctx, "ping: " + LocalDateTime.now());
  }

  public void insert(RoutingContext ctx) {
    JsonObject rawEntity = ctx.getBodyAsJson();
    final SystemInfo todo = new SystemInfo(rawEntity);
    ResponseBody.sendResponse(ctx, systemInfoService.insert(todo));
  }

  public Future initializeService() {
    logger.info("initializeService...");
    return systemInfoService.initializeService();
  }
}
