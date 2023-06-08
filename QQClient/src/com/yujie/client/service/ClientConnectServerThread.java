package com.yujie.client.service;

import com.yujie.common.Message;
import com.yujie.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * @author 余杰
 * @version 1.0
 */
public class ClientConnectServerThread extends Thread {
  // 该线程需要持有 Socket
  private Socket socket;

  // 接收一个 socket 对象
  public ClientConnectServerThread(Socket socket){
    this.socket = socket;
  }

  @Override
  public void run() {
    // 因为线程需要在后台和服务器通信,因此使用 while 循环
    while (true){
      System.out.println("客户端线程,等待读取从服务端发送的消息");
      try {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        // 如果服务器没有发送 Message 对象,线程会阻塞在这里
        Message msg = (Message)ois.readObject();
        // TODO:拿到服务器发送的 msg 对象后,
        // 判断这个 message 的类型，然后做相应的业务处理
        // 如果读取到的是，服务端返回的在线用户列表
        if(msg.getMessageType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){
          // 取出在线列表信息，并显示
          String[] onlineUsers = msg.getContent().split(",");
          System.out.println("\n========== 当前在线用户列表 ==========");
          for (int i = 0; i < onlineUsers.length; i++) {
            System.out.println("用户: " + onlineUsers[i]);
          }
        } else if (msg.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
          // 把服务器端转发的消息，显示到控制台即可
          System.out.println("\n" + msg.getSender() + " 在 " + msg.getSendTime() + " 对 " + msg.getGetter() + " 说 " + msg.getContent());
        } else {
          System.out.println("是其他类型的 msg，暂时不做处理");
        }
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }


    }
  }

  // 为了更方便的得到 Socket
  public Socket getSocket() {
    return socket;
  }
}
