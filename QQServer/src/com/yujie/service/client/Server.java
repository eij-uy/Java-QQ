package com.yujie.service.client;

import com.yujie.common.Message;
import com.yujie.common.MessageType;
import com.yujie.common.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
  private ServerSocket ss = null;
  // 创建一个集合，存放多个用户，如果是这些用户，那么就认为是合法的
  // 这里可以使用 ConcurrentHashMap，可以处理并发的集合，没有线程安全问题
  // HashMap 是没有处理线程安全的，因此在多线程情况下是不安全的
  // ConcurrentHashMap 处理了线程安全，即线程同步处理，在多线程情况下是安全的
  private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();
  static {
    // 在静态代码块中初始化 validUsers
    validUsers.put("100", new User("100", "123456"));
    validUsers.put("200", new User("200", "123456"));
    validUsers.put("300", new User("300", "123456"));
    validUsers.put("七夜", new User("七夜", "123456"));
    validUsers.put("青灵", new User("青灵", "123456"));
    validUsers.put("初雪", new User("初雪", "123456"));
  }
  private boolean checkUser(String userId, String pwd){
    if(validUsers.containsKey(userId)){
      User user = validUsers.get(userId);
      return user.getPwd().equals(pwd);
    }
    return false;
  }
  public Server (){
    // 注意：端口可以写在配置文件中
    System.out.println("服务端在 9999 端口监听...");
    try {
      Thread thread = new Thread(new SendMessageToAllService());
      thread.start();
      ss = new ServerSocket(9999);
      while (true){ //当和某个客户端连接之后，会继续监听，因此使用 while 循环
        Socket socket = ss.accept();
        // 得到 socket 关联的对象输入流
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        // 得到 socket 关联的对象输出流，为了把 msg 对象返给 客户端
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        User u = (User) ois.readObject();
        // 创建一个 Message 对象，准备回复客户端
        Message msg = new Message();
        // 验证
        if(checkUser(u.getUserId(), u.getPwd())){
          // 登录成功
          msg.setMessageType(MessageType.MESSAGE_LOGIN_SUCCESS);
          // 将 message 对象回复客户端
          oos.writeObject(msg);
          // 创建一个线程，和客户端保持通信，该线程需要持有 socket 对象
          ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
          // 启动线程
          scct.start();
          // 将该线程对象放入集合当中
          ManageClientThread.addClientThread(u.getUserId(), scct);
        } else {
          // 登录失败
          System.out.println("用户 id=" + u.getUserId() + " 密码=" + u.getPwd() + " 验证失败");
          msg.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
          oos.writeObject(msg);
          socket.close();
        }
      }

    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    } finally {
      try {
        ss.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
