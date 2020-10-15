package com.mahdi.starter;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.EncodeException;
import io.vertx.core.json.Json;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۱/۱۰/۲۰۲۰ & Time: 12:47
 * http://gitlab.com/mahdihp
 */


public class SystemController  {

  private MongoClient mongoClient;
  private Vertx vertx;
  private Router router;

  @Inject
  public SystemController(MongoClient mongoClient, Vertx vertx, Router router) {
    this.mongoClient = mongoClient;
    this.vertx = vertx;
    this.router = router;
  }

  public Router getRouter() {
    if (router == null) {
      router = Router.router(vertx);
      router.get("/system").handler(this::getListAllSystem);
    }

    return router;
  }

  private void getListAllSystem(RoutingContext ctx) {
//    ctx.end("SystemController");
    vertx.executeBlocking(
      fut -> {
        fut.complete("getListAllSystem");
      },
      false, // IMPORTANT TO MAKE THIS FALSE
      res -> {
        handleAsyncResponse(res, ctx);
      }
    );
  }

  private void handleAsyncResponse(AsyncResult<Object> res, RoutingContext ctx) {
    // Handler for the future. If successful, encode result and send
    if (res.succeeded()) {
      try {
        ctx.response().end(Json.encode(res.result()));
      } catch (EncodeException e) {
        ctx.fail(new RuntimeException("Failed to encode results."));
      }

    } else {
      ctx.fail(res.cause());
    }
  }

}
