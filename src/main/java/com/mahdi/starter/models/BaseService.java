package com.mahdi.starter.models;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.vertx.core.Future;

import java.util.List;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۳/۱۰/۲۰۲۰ & Time: 14:50
 * http://gitlab.com/mahdihp
 */

public interface BaseService<T, E> {

  Future initializeService();
  Future<E> insert(E e);
  Future<List<E>> getAll();
  Maybe<E> getById(String id);
  Maybe<E> update(String id);
  Completable delete(String id);

//  Completable deleteAll();
}
