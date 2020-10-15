package com.mahdi.starter.models.systemdocument;

import com.mahdi.starter.common.Constants;
import com.mahdi.starter.common.UtilData;
import com.mahdi.starter.models.BaseService;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۳/۱۰/۲۰۲۰ & Time: 14:51
 * http://gitlab.com/mahdihp
 */

public class SystemInfoServiceImpl implements BaseService<SystemInfoServiceImpl, SystemInfo> {

  private final Vertx vertx;
  private final JsonObject config;
  private MongoClient mongoClient;
  private static final Logger logger = LoggerFactory.getLogger(SystemInfoServiceImpl.class);

  public SystemInfoServiceImpl(Vertx vertx, JsonObject config) {
    this.vertx = vertx;
    this.config = config;
    this.mongoClient = MongoClient.createShared(vertx, config);
  }

  @Override
  public Future initializeService() {
    return this.mongoClient.getCollections().onComplete(new Handler<AsyncResult<List<String>>>() {
      @Override
      public void handle(AsyncResult<List<String>> listAsyncResult) {
        logger.info("Count Collections: " + listAsyncResult.result().size());
        for (String listCollection : Constants.getListCollections()) {
          if (UtilData.findCollections(listCollection, listAsyncResult.result()) == false) {
            mongoClient.createCollection(listCollection);
            logger.info("Collection Created: " + listCollection);
          }
        }
      }
    });
  }

  @Override
  public Future<SystemInfo> insert(SystemInfo systemInfo) {
    JsonObject document = new JsonObject()
      .put(SystemInfo.KEY_SYSTEMNAME, systemInfo.getSystemName());
    return this.mongoClient.insert(Constants.KEY_SYSTEMINFO, document)
      .map(s -> {
        systemInfo.setId(s);
        return systemInfo;
      });

  }

  @Override
  public Future<List<SystemInfo>> getAll() {
    this.mongoClient.getCollections().map(new Function<List<String>, Object>() {
      @Override
      public Object apply(List<String> strings) {
        return strings.size();
      }
    });
    return null;
  }

  @Override
  public Maybe<SystemInfo> getById(String id) {
    return null;
  }

  @Override
  public Maybe<SystemInfo> update(String id) {
    return null;
  }

  @Override
  public Completable delete(String id) {
    return null;
  }

//  @Override
//  public Completable deleteAll() {
//    return null;
//  }

}
