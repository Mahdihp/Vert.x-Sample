package com.mahdi.starter.common;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۴/۱۰/۲۰۲۰ & Time: 15:05
 * http://gitlab.com/mahdihp
 */

public class ResponseBody {

  public static void sendResponse(RoutingContext context, Completable asyncResult) {
    HttpServerResponse response = context.response();
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(response::end, ex -> internalError(context, ex));
    }
  }

  /**
   * Resolve an asynchronous status and send back the response.
   * By default, the successful status code is 200 OK.
   *
   * @param context     routing context
   * @param asyncResult asynchronous status with no result
   */
  public static void sendResponse(RoutingContext context, String asyncResult) {
    HttpServerResponse response = context.response();
    response.end(asyncResult);
  }
  public static void sendResponse(RoutingContext context, Future asyncResult) {
    HttpServerResponse response = context.response();
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.onComplete(new Handler<AsyncResult>() {
        @Override
        public void handle(AsyncResult tAsyncResult) {
          response.end(tAsyncResult.result().toString());
        }
      });
    }
  }

  /**
   * Resolve an asynchronous status and send back the response.
   * The successful status code depends on processor {@code f}.
   *
   * @param context     routing context
   * @param asyncResult asynchronous status with no result
   */
  public static void sendResponse(RoutingContext context, Completable asyncResult, Consumer<RoutingContext> f) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(() -> f.accept(context), ex -> internalError(context, ex));
    }
  }

  public static <T> void sendResponse(RoutingContext context, Single<T> asyncResult,
                                      Function<T, String> converter, BiConsumer<RoutingContext, String> f) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(r -> f.accept(context, converter.apply(r)), ex -> internalError(context, ex));
    }
  }

  /**
   * Resolve an asynchronous result and send back the response.
   *
   * @param context     routing context
   * @param asyncResult asynchronous result
   * @param converter   result content converter
   * @param <T>         the type of result
   */
  public static <T> void sendResponse(RoutingContext context, Single<T> asyncResult, Function<T, String> converter) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(r -> ok(context, converter.apply(r)),
        ex -> internalError(context, ex));
    }
  }

  public static <T> void sendResponse(RoutingContext context, Maybe<T> asyncResult, Function<T, String> converter) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      Single<Optional<T>> single = asyncResult.map(Optional::of)
        .switchIfEmpty(Maybe.just(Optional.empty()))
        .toSingle();
      sendResponseOpt(context, single, converter);
    }
  }

  public static <T> void sendResponseOpt(RoutingContext
                                           context, Single<Optional<T>> asyncResult, Function<T, String> converter) {
    if (asyncResult == null) {
      internalError(context, "invalid_status");
    } else {
      asyncResult.subscribe(r -> {
          if (r.isPresent()) {
            ok(context, converter.apply(r.get()));
          } else {
            notFound(context);
          }
        },
        ex -> internalError(context, ex));
    }
  }

  /**
   * Send back a response with status 200 Ok.
   *
   * @param context routing context
   */
  public static void ok(RoutingContext context) {
    context.response().end();
  }

  /**
   * Send back a response with status 200 OK.
   *
   * @param context routing context
   * @param content body content in JSON format
   */
  public static void ok(RoutingContext context, String content) {
    context.response().setStatusCode(200)
      .putHeader("content-type", "application/json")
      .end(content);
  }

  /**
   * Send back a response with status 201 Created.
   *
   * @param context routing context
   */
  public static void created(RoutingContext context) {
    context.response().setStatusCode(201).end();
  }

  /**
   * Send back a response with status 201 Created.
   *
   * @param context routing context
   * @param content body content in JSON format
   */
  public static void created(RoutingContext context, String content) {
    context.response().setStatusCode(201)
      .putHeader("content-type", "application/json")
      .end(content);
  }

  /**
   * Send back a response with status 204 No Content.
   *
   * @param context routing context
   */
  public static void noContent(RoutingContext context) {
    context.response().setStatusCode(204).end();
  }

  /**
   * Send back a response with status 400 Bad Request.
   *
   * @param context routing context
   * @param ex      exception
   */
  public static void badRequest(RoutingContext context, Throwable ex) {
    context.response().setStatusCode(400)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
  }

  /**
   * Send back a response with status 400 Bad Request.
   *
   * @param context routing context
   */
  public static void badRequest(RoutingContext context) {
    context.response().setStatusCode(400)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", "bad_request").encodePrettily());
  }

  /**
   * Send back a response with status 404 Not Found.
   *
   * @param context routing context
   */
  public static void notFound(RoutingContext context) {
    context.response().setStatusCode(404)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("message", "not_found").encodePrettily());
  }

  /**
   * Send back a response with status 500 Internal Error.
   *
   * @param context routing context
   * @param ex      exception
   */
  public static void internalError(RoutingContext context, Throwable ex) {
    context.response().setStatusCode(500)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
  }

  /**
   * Send back a response with status 500 Internal Error.
   *
   * @param context routing context
   * @param cause   error message
   */
  protected static void internalError(RoutingContext context, String cause) {
    context.response().setStatusCode(500)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", cause).encodePrettily());
  }

  /**
   * Send back a response with status 503 Service Unavailable.
   *
   * @param context routing context
   */
  public static void serviceUnavailable(RoutingContext context) {
    context.fail(503);
  }

  /**
   * Send back a response with status 503 Service Unavailable.
   *
   * @param context routing context
   * @param ex      exception
   */
  public static void serviceUnavailable(RoutingContext context, Throwable ex) {
    context.response().setStatusCode(503)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", ex.getMessage()).encodePrettily());
  }

  /**
   * Send back a response with status 503 Service Unavailable.
   *
   * @param context routing context
   * @param cause   error message
   */
  public static void serviceUnavailable(RoutingContext context, String cause) {
    context.response().setStatusCode(503)
      .putHeader("content-type", "application/json")
      .end(new JsonObject().put("error", cause).encodePrettily());
  }
}
