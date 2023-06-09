package com.yujie.client.service;

import com.yujie.common.Message;
import com.yujie.common.MessageType;
import com.yujie.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author 余杰
 * @version 1.0
 * 该类完成用户登录和用户注册等功能
 */
public class UserClientService {
  private User user = new User(); // 因为我们可能在其他地方要使用 user 信息
  // 因为 socket 在其他地方也有使用,因此作为属性
  private Socket socket = null;
  // 根据 userId 和 pws 到服务器验证该用户是否合法
  public boolean checkUser(String userId, String pwd) {
    user.setUserId(userId);
    user.setPwd(pwd);

    try {
      socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
      // 得到 OjbectOutStream 对象
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(user); //发送 Object 对象
//      socket.shutdownOutput();

      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      Message msg = (Message)ois.readObject();
      Boolean isLogin = false;

      if(msg.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)){
        // 登录成功, 创建一个和服务器端保持通讯的线程,所以再去创建一个线程类 ClientConnectServerThread
        isLogin = true;
        ClientConnectServerThread ccst = new ClientConnectServerThread(socket, user.getUserId());
        ccst.start();
        // 为了以后客户端的扩展, 将线程保存在集合中
        ManageClientConnectServerThread.addClientConnectServerThread(userId, ccst);
      } else {
        // 如果登录失败, 就不能启动和服务器通信的线程, 就要关闭 socket
        socket.close();
      }
      return isLogin;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // 向服务端请求在线用户列表
  public void sendUserList(){
    // 创建一个 Message 对象，消息类型是 MESSAGE_GET_ONLINE_FRIEND
    Message msg = new Message();
    msg.setMessageType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
    msg.setSender(user.getUserId());

    // 发送给服务器
    // 应该得到当前线程的 Socket 对应的 ObjectOutputStream
    try {
      // 首先从管理线程的集合中，通过 userId 得到这个线程
      ClientConnectServerThread clientConnectServerThread = ManageClientConnectServerThread.getClientConnectServerThread(user.getUserId());
      // 拿到这个线程的 socket
      Socket socket1 = clientConnectServerThread.getSocket();
      // 通过 socket 得到与之关联的输出流
      ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
      oos.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  // 编写方法，退出客户端，并给服务端发送一个退出系统的 message 对象
  public void logout(){
    Message msg = new Message();
    msg.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
    msg.setSender(user.getUserId()); // 带上当前的 用户 id

    try {
      ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
      oos.writeObject(msg);
      System.out.println(user.getUserId() + " 退出系统");
      System.exit(0); // 结束进程
    } catch (IOException e) {
      e.printStackTrace();
    }


  }
}
