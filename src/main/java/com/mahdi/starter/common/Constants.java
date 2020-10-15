package com.mahdi.starter.common;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mahdihp
 * User: mahdihp
 * Date: ۱۴/۱۰/۲۰۲۰ & Time: 13:33
 * http://gitlab.com/mahdihp
 */

public class Constants {

  public static final String KEY_SYSTEMINFO = "SystemInfo";
  public static final String KEY_SYSTEMDOCUMENT = "SystemDocument";
  public static final String KEY_USERS = "Users";
  public static final String KEY_HISTORYCHANGES = "HistoryChanges";

  public static List<String> getListCollections() {
    return Arrays.asList(KEY_SYSTEMINFO, KEY_SYSTEMDOCUMENT, KEY_USERS, KEY_HISTORYCHANGES);
  }
}
