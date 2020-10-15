package com.mahdi.starter.verticle;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract base reactive verticle that provides several helper methods for RESTful API.
 *
 * @author <a href="http://www.sczyh30.com">Eric Zhao</a>
 */
public abstract class RestfulApiVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(RestfulApiVerticle.class);

  /**
   * Create an HTTP server for the REST service.
   *
   * @param router router instance
   * @param host   server host
   * @param port   server port
   * @return asynchronous result
   */
  protected Future createHttpServer(Router router, String host, int port) {
//    return vertx.createHttpServer()
//      .requestHandler(router::accept)
//      .rxListen(port, host)
//      .toCompletable();

    return vertx.createHttpServer()
      .requestHandler(router)
      .listen(port, host)
      .onComplete(http -> {
        if (http.succeeded()) {
          System.out.println("HTTP Server Started On Port: " + port);
        } else {
          System.out.println("Fail HTTP Server: " + port);
        }
      });


  }

  /**
   * Enable CORS support for web router.
   *
   * @param router router instance
   */
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
