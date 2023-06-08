package com.yujie.client.service;

import com.yujie.common.Message;

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
        
      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }


    }
  }

  // 为了更方便的得到 Socket
  public Socket getSocket() {
    return socket;
  }
}
