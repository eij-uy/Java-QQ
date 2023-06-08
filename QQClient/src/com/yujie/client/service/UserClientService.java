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
      socket.shutdownOutput();

      ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
      Message msg = (Message)ois.readObject();
      Boolean isLogin = false;

      if(msg.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)){
        // 登录成功, 创建一个和服务器端保持通讯的线程,所以再去创建一个线程类 ClientConnectServerThread
        isLogin = true;
        ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
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

}
