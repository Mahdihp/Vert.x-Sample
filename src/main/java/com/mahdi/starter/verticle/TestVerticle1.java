package com.mahdi.starter.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۸/۱۰/۲۰۲۰ & Time: 11:34
 * http://gitlab.com/mahdihp
 */

public class TestVerticle1 extends BaseVerticle {
  private static final Logger logger = LoggerFactory.getLogger(TestVerticle1.class);
  private static final String HOST = "0.0.0.0";
  private static final int PORT = 8001;

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route("/ping").handler(new Handler<RoutingContext>() {
      @Override
      public void handle(RoutingContext routingContext) {
        routingContext.response().end("TestVerticle1");
      }
    });
    enableCorsSupport(router);

    String host = config().getString("http.address", HOST);
    int port = config().getInteger("http.port", PORT);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, host)
      .onComplete(http -> {
        if (http.succeeded()) {
          System.out.println("TestVerticle1 HTTP Server Started On Port: " + port);
        } else {
          System.out.println("TestVerticle1 Fail HTTP Server: " + port);
        }
      });
  }


  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    super.stop(stopPromise);
    logger.info("TestVerticle1 stop...");

  }


  protected void enableCorsSupport(Router router) {
    Set<String> allowHeaders = new HashSet<>();
    allowHeaders.add("x-requested-with");
    allowHeaders.add("Access-Control-Allow-Origin");
    allowHeaders.add("origin");
    allowHeaders.add("Content-Type");
    allowHeaders.add("accept");
    // CORS support
    router.route().handler(CorsHandler.create("*")
      .allowedHeaders(allowHeaders)
      .allowedMethod(HttpMethod.GET)
      .allowedMethod(HttpMethod.POST)
      .allowedMethod(HttpMethod.DELETE)
      .allowedMethod(HttpMethod.PATCH)
      .allowedMethod(HttpMethod.PUT)
    );
  }
}
