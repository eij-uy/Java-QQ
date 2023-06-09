package com.yujie.service.client;

import com.yujie.common.Message;
import com.yujie.common.MessageType;
import com.yujie.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.Set;

/**
 * @author 余杰
 * @version 1.0
 */
public class SendMessageToAllService implements Runnable {
  @Override
  public void run() {
    // 为了多次发送消息,使用 while 循环
    while (true){
      System.out.println("请输入服务器要推送的消息/新闻: ");
      String content = Utility.readString(100);
      if(content.equals("exit")){
        break;
      }
      // 构建一个消息,群发消息
      Message msg = new Message();
      msg.setSender("服务器");
      msg.setMessageType(MessageType.MESSAGE_TO_ALL_MES);
      msg.setContent(content);
      msg.setSendTime(new java.util.Date().toString());
      System.out.println("服务器推送消息给所有人, 说的是 " + content);

      // 遍历当前酥油的通信线程,得到socket,并发送 message
      Set<String> keySet = ManageClientThread.getKeySet();
      for (String userId : keySet){
        try {
          ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getServerConnectClientThread(userId).getSocket().getOutputStream());
          oos.writeObject(msg);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
