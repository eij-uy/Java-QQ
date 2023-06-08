package com.yujie.client.view;

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

  private UserClientService userClientService = new UserClientService();

  public static void main(String[] args) {
    View view = new View();
    view.mainMenu();
    System.out.println("客户端退出系统");
  }
  private void mainMenu(){
    while (loop){
      System.out.println("===========欢迎登录网络通信系统===========");
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
            System.out.println("===========欢迎 (用户 " + userId + " ) ===========");
            while (loop){
              System.out.println("\n===========网络通信系统二级菜单 (用户 " + userId + " ) ===========");
              System.out.println("\t\t 1 显示在线用户列表");
              System.out.println("\t\t 2 群发消息");
              System.out.println("\t\t 3 私聊消息");
              System.out.println("\t\t 4 发送文件");
              System.out.println("\t\t 9 退出系统");
              System.out.print("请输入您的选择: ");
              key = Utility.readString(1);
              switch (key) {
                case "1":
                  System.out.println("显示在线用户");
                  break;
                case "2":
                  System.out.println("群发消息");
                  break;
                case "3":
                  System.out.println("私聊消息");
                  break;
                case "4":
                  System.out.println("发送文件");
                  break;
                case "9":
                  loop = false;
                  break;
              }
            }
          } else {
            System.out.println("==========登陆失败==========");
          }
          break;
        case "9":
          loop = false;
          break;
      }

    }
  }
}
