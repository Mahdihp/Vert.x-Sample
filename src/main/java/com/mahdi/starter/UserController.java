package com.mahdi.starter;

import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۲/۱۰/۲۰۲۰ & Time: 13:32
 * http://gitlab.com/mahdihp
 */

public class UserController {

  private final MongoClient mongoClient;

  public UserController(MongoClient mongoClient) {
    this.mongoClient = mongoClient;
  }

  public void home(RoutingContext ctx) {
    ctx.end("UserController......... "+ctx.request().host());
  }
}
