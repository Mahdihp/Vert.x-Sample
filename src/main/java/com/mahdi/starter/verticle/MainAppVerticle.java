package com.mahdi.starter.verticle;

import com.mahdi.starter.models.systemdocument.SystemInfoController;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۴/۱۰/۲۰۲۰ & Time: 11:05
 * http://gitlab.com/mahdihp
 */

public class MainAppVerticle extends RestfulApiVerticle {

  private static final String HOST = "0.0.0.0";
  private static final int PORT = 8000;
  private SystemInfoController systemInfoController;
  private static final Logger logger = LoggerFactory.getLogger(MainAppVerticle.class);
  private Router router;

  @Override
  public void init(Vertx vertx, Context context) {
    super.init(vertx, context);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);

    router = Router.router(vertx);
//     Enable HTTP Body parse.
//    router.route().handler(BodyHandler.create());
    // Enable CORS.
    enableCorsSupport(router);

    String host = config().getString("http.address", HOST);
    int port = config().getInteger("http.port", PORT);


    initializeServices().onComplete(new Handler<AsyncResult>() {
      @Override
      public void handle(AsyncResult asyncResult) {
        createHttpServer(router, host, port);
      }
    });
  }

  private Future initializeServices() {
    logger.debug("initializeServices...");
    JsonObject config = initMongoDb();
    systemInfoController = new SystemInfoController(vertx, config);
    initRouter();
    return systemInfoController.initializeService();
  }

  private JsonObject initMongoDb() {
    JsonObject config = new JsonObject();
    config.put("address", "192.168.200.91");
    config.put("connection_string", "mongodb://192.168.200.91:27017");
    config.put("db_name", "RS_Test");
    return config;
  }

  private void initRouter() {
    router.get("/ping").handler(systemInfoController::ping);

  }

}
