package com.yujie.service.client;

import java.util.HashMap;
import java.util.Set;

/**
 * @Author 余杰
 * @Date 2023/6/8 23:42
 * @PackageName: com.yujie.service.client
 * @ClassName: ManageClientThread
 * @Description: TODO
 * @Version 1.0
 * 该类用于管理和客户端通信的线程
 */
public class ManageClientThread {
  private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

  public static Set<String> getKeySet() {
    return hm.keySet();
  }

  // 添加线程对象到集合中
  public static void addClientThread(String userId, ServerConnectClientThread scct){
    hm.put(userId, scct);
  }

  public static void removeClientThread(String userId){
    hm.remove(userId);
  }

  public static ServerConnectClientThread getServerConnectClientThread(String userId){
    return hm.get(userId);
  }


  // 从这里编写方法，可以返回在线用户列表
  public static String getOnlineUsers(){
    // 集合便利，便利 hashMap 的 key
    Set<String> strings = hm.keySet();

    String onlineUsers = "";
    for (String key : strings){
      onlineUsers += key + ",";
    }
    return onlineUsers;
  }

}
