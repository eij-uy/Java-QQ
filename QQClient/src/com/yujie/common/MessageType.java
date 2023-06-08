package com.yujie.common;

/**
 * @author 余杰
 * @version 1.0
 */
public interface MessageType {
  // 1. 在接口中定义了一些常量
  // 2. 不同的常亮的值,表示不同的消息类型
  String MESSAGE_LOGIN_SUCCESS = "1"; // 表示登录成功
  String MESSAGE_LOGIN_FAIL = "2"; // 表示登陆失败
}
