package com.mahdi.starter.common;

import java.util.List;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۴/۱۰/۲۰۲۰ & Time: 14:23
 * http://gitlab.com/mahdihp
 */

public class UtilData {

  public static boolean findCollections(String item, List<String> currentList) {
    for (String s : currentList) {
      if (item.equalsIgnoreCase(s))
        return true;
    }
    return false;
  }
}
