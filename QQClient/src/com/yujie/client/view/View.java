package com.yujie.client.view;

import com.yujie.client.service.ClientSendMessage;
import com.yujie.client.service.FileClientService;
import com.yujie.client.service.UserClientService;
import com.yujie.client.utils.Utility;

/**
 * @author 余杰
 * @version 1.0
 * 客户端的菜单界面
 */
public class View {
  private boolean loop = true; // 控制是否显示菜单
  private String key = ""; // 接受用户输入

  // 用于登录 / 注册用户
  private UserClientService userClientService = new UserClientService();
  // 用于用户私聊群聊
  private ClientSendMessage clientSendMessage = new ClientSendMessage();
  // 用于发送文件
  private FileClientService fileClientService = new FileClientService();

  public static void main(String[] args) {
    View view = new View();
    view.mainMenu();
    System.out.println("客户端退出系统");
  }
  private void mainMenu(){
    while (loop){
      System.out.println("=========== 欢迎登录网络通信系统 ===========");
      System.out.println("\t\t 1 登录系统");
      System.out.println("\t\t 9 退出系统");
      System.out.print("请输入你的选择:");

      key = Utility.readString(1);
      // 根据用户的数据,来处理不同的逻辑
      switch (key) {
        case "1":
          System.out.println("登录系统");
          System.out.print("请输入用户名:");
          String userId = Utility.readString(50);
          System.out.print("请输入密 码:");
          String pwd = Utility.readString(50);
          // TODO:需要到服务端验证该用户是否合法
          // 这里代码很多, 所以编写一个类 UserClientService[用户登录/用户注册]
          if(userClientService.checkUser(userId, pwd)){
            System.out.println("=========== 欢迎 ( 用户 " + userId + " 登录成功 ) ===========");
            while (loop){
              System.out.println("\n=========== 网络通信系统二级菜单 (用户 " + userId + " ) ===========");
              System.out.println("\t\t 1 显示在线用户列表");
              System.out.println("\t\t 2 群发消息");
              System.out.println("\t\t 3 私聊消息");
              System.out.println("\t\t 4 发送文件");
              System.out.println("\t\t 9 退出系统");
              System.out.print("请输入您的选择: ");
              key = Utility.readString(1);
              switch (key) {
                case "1":
                  // 写一个方法来获取在线用户列表
                  userClientService.sendUserList();
                  break;
                case "2":{
                  System.out.print("请输入想说的话: ");
                  String content = Utility.readString(100);
                  clientSendMessage.sendMessageToAll(userId, content);
                  break;
                }
                case "3": {
                  System.out.print("请输入想聊天的用户名(在线): ");
                  String getter = Utility.readString(10);
                  System.out.print("请输入想说的话: ");
                  String content = Utility.readString(100);
                  clientSendMessage.sendMessageToOne(userId, getter, content);
                  break;
                }
                case "4":
                  System.out.println("请输入想要发送文件的用户号(在线): ");
                  String getter = Utility.readString(10);
                  System.out.println("请输入发送的文件完整路径(形式 d:\\xxx.jpg): ");
                  String src = Utility.readString(100);
                  System.out.println("请输入发送文件到对方的路径(形式 d:\\xxx.jpg): ");
                  String dest = Utility.readString(100);
                  fileClientService.sendFileToOne(userId, getter, src, dest);
                  break;
                case "9":
                  loop = false;
                  // 调用方法，给服务器发送一个退出系统的 message
                  userClientService.logout();
                  break;
              }
            }
          } else {
            System.out.println("========== 登陆失败 ==========");
          }
          break;
        case "9":
          loop = false;
          break;
      }

    }
  }
}
