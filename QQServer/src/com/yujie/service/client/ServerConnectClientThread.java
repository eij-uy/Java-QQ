package com.yujie.service.client;

import com.yujie.common.Message;
import com.yujie.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author 余杰
 * @date 2023/6/8 23:36
 * @PackageName: com.yujie.service.client
 * @ClassName: ServerConnectClientThread
 * @Description: TODO
 * @Version 1.0
 * 该类的一个对象和某个客户端保持通信
 */
public class ServerConnectClientThread extends Thread {
  private Socket socket;
  private String userId; // 连接到服务端的用户 Id

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public ServerConnectClientThread(Socket socket, String userId) {
    this.socket = socket;
    this.userId = userId;
  }

  // 这里线程处于 run 的状态，可以发送 / 接收消息
  @Override
  public void run() {
    while (true){
      try {
        System.out.println("服务器和客户端 " + userId + " 保持通信，读取数据...");
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message msg = (Message) ois.readObject();
        // 后面会使用 msg，根据 Message 的类型，做相应的业务处理
        if(msg.getMessageType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
          // 客户端发过来的如果是 获取用户列表 的消息类型
          System.out.println(msg.getSender() + " 要获取在线用户列表");
          String onlineUsers = ManageClientThread.getOnlineUsers();
          // 准备返回
          // 构建一个 Message 对象，返回给客户端
          Message msg2 = new Message();
          msg2.setMessageType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
          msg2.setContent(onlineUsers);
          msg2.setGetter(msg.getSender());
          // 写入到 socket 数据通道，返回给客户端
          ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
          oos.writeObject(msg2);

        } else if (msg.getMessageType().equals(MessageType.MESSAGE_COMM_MES)) {
          // 根据 message 获取 getter，然后再得到对应线程
          ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getServerConnectClientThread(msg.getGetter()).getSocket().getOutputStream());
          oos.writeObject(msg); // 转发，提示如果客户不在线，可以保存到数据库，这样就可以实现离线留言
        } else if(msg.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
          System.out.println(msg.getSender() + "想要退出");
          // 将这个客户端对应线程，从集合删除
          ManageClientThread.removeClientThread(msg.getSender());
          // 关闭连接
          socket.close();
          System.out.println(msg.getSender() + "退出了");
          // 关闭线程
          break;
        } else {
          System.out.println("其他的命令，暂不做处理");
        }
      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
