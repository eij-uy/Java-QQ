package com.yujie.common;

import java.io.Serializable;

/**
 * @author 余杰
 * @version 1.0
 */
public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  private String userId; // 用户名 or 用户 ID
  private String pwd; // 用户密码

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
}
