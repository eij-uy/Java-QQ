package com.yujie.client.service;

import java.util.HashMap;

/**
 * @author 余杰
 * @version 1.0
 * 管理客户端连接到服务器端线程的类
 */
public class ManageClientConnectServerThread {
  // 我们吧多个线程放到 HashMap 集合中, key 就是用户Id, value 就是线程
  private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

  // 将某个线程加入到集合
  public static void addClientConnectServerThread(String userId, ClientConnectServerThread ccst){
    hm.put(userId, ccst);
  }

  // 通过 userId 获取独享的线程
  public static ClientConnectServerThread getClientConnectServerThread(String userId){
    return hm.get(userId);
  }
}
