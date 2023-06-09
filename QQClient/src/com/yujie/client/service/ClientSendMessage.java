package com.yujie.client.service;

import com.yujie.common.Message;
import com.yujie.common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Author 余杰
 * @Date 2023/6/9 1:26
 * @PackageName: com.yujie.client.service
 * @ClassName: ClientSendMessage
 * @Description: TODO
 * @Version 1.0
 * 该类 / 对象提供和消息相关的服务方法
 */
public class ClientSendMessage {
  public void sendMessageToOne(String userId, String getter, String content){
    // 构建 message
    Message msg = new Message();
    msg.setMessageType(MessageType.MESSAGE_COMM_MES);
    msg.setSender(userId);
    msg.setGetter(getter);
    msg.setContent(content);
    msg.setSendTime(new java.util.Date().toString()); // 发送时间
    System.out.println(userId + " 对 " + getter + "说: " + content);

    try {
      ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(userId).getSocket().getOutputStream());
      oos.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public void sendMessageToAll(String userId, String content){
    Message msg = new Message();
    msg.setMessageType(MessageType.MESSAGE_TO_ALL_MES);
    msg.setSender(userId);
    msg.setSendTime(new java.util.Date().toString());
    msg.setContent(content);
    System.out.println(userId + " 对大家说: " + content);

    try {
      ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(userId).getSocket().getOutputStream());
      oos.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
