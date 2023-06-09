package com.yujie.client.service;

import com.yujie.common.Message;
import com.yujie.common.MessageType;

import java.io.*;

/**
 * @author 余杰
 * @version 1.0
 * 该类 / 对象完成文件传输服务
 */
public class FileClientService {
  public void sendFileToOne(String userId, String getter, String src, String dest){
    // 读取 src 文件,并封装到 msg 对象中
    Message msg = new Message();
    msg.setMessageType(MessageType.MESSAGE_FILE_MES);
    msg.setSender(userId);
    msg.setGetter(getter);
    msg.setSendTime(new java.util.Date().toString());
    msg.setSrc(src);
    msg.setDest(dest);

    // 需要将文件进行读取
    BufferedInputStream bis = null;
    byte[] bytes = new byte[(int) new File(src).length()];
    try {
      bis = new BufferedInputStream(new FileInputStream(src));
      bis.read(bytes); //将 src 文件读到程序对应字节数组
      // 将字节数组封装到 msg 对象中
      msg.setFileBytes(bytes);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if(bis != null){
        try {
          bis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println("\n" + userId + " 给 " + getter + " 发送文件 " + src + " 到目录 " + dest);
    try {
      ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(userId).getSocket().getOutputStream());
      oos.writeObject(msg);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
