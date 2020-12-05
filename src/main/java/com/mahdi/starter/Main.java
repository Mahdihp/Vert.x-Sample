package com.mahdi.starter;

import com.mahdi.starter.verticle.MainAppVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۸/۱۰/۲۰۲۰ & Time: 11:40
 * http://gitlab.com/mahdihp
 */

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    int instances = 1;
    JsonObject myconfig = new JsonObject();
    DeploymentOptions options = new DeploymentOptions().setConfig(myconfig).setInstances(instances).setHa(true);
    vertx.deployVerticle(new MainAppVerticle(), options, stringAsyncResult -> {
      if (stringAsyncResult.succeeded()) {
        logger.info("MainAppVerticle.class DeploymentID: " + stringAsyncResult.result());
      }
    });
  }
}
