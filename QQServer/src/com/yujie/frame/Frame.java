package com.yujie.frame;

import com.yujie.service.client.Server;

/**
 * @Author 余杰
 * @Date 2023/6/8 23:50
 * @PackageName: com.yujie.frame
 * @ClassName: Fram
 * @Description: TODO
 * @Version 1.0
 * 该类创建 Server 对象，启动后台的服务
 */
public class Frame {
  public static void main(String[] args) {
    Server server = new Server();
  }
}
