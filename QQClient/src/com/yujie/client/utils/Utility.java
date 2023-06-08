package com.yujie.client.utils;


/*
 工具类的作用:
 处理各种情况的用户输入，并且能够按照程序员的需求，得到用户的控制台输入。
 */

import java.util.Scanner;

/**
 *
 */
@SuppressWarnings({"all"})
public class Utility {
  //静态属性。。。
  private static Scanner scanner = new Scanner(System.in);


  /**
   * 功能：读取键盘输入的一个菜单选项，值：1——5的范围
   *
   * @return 1——5
   */
  public static char readMenuSelection() {
    char c;
    for (; ; ) {
      String str = readKeyBoard(1, false);//包含一个字符的字符串
      c = str.charAt(0);//将字符串转换成字符char类型
      if (c != '1' && c != '2' &&
          c != '3' && c != '4' && c != '5') {
        System.out.print("💔选择错误!\n请在此处重新输入: ");
      } else break;
    }
    return c;
  }

  /**
   * 功能：读取键盘输入的一个字符
   *
   * @return 一个字符
   */
  public static char readChar() {
    String str = readKeyBoard(1, false);//就是一个字符
    return str.charAt(0);
  }

  /**
   * 功能：读取键盘输入的一个字符，如果直接按回车，则返回指定的默认值；否则返回输入的那个字符
   *
   * @param defaultValue 指定的默认值
   * @return 默认值或输入的字符
   */

  public static char readChar(char defaultValue) {
    String str = readKeyBoard(1, true);//要么是空字符串，要么是一个字符
    return (str.length() == 0) ? defaultValue : str.charAt(0);
  }

  /**
   * 功能：读取键盘输入的整型，长度小于2位
   *
   * @return 整数
   */
  public static int readInt() {
    int n;
    for (; ; ) {
      String str = readKeyBoard(10, false);//一个整数，长度<=10位
      try {
        n = Integer.parseInt(str);//将字符串转换成整数
        break;
      } catch (NumberFormatException e) {
        System.out.print("💔数字输入错误!\n请在此处重新输入: ");
      }
    }
    return n;
  }

  /**
   * 功能：读取键盘输入的 整数或默认值，如果直接回车，则返回默认值，否则返回输入的整数
   *
   * @param defaultValue 指定的默认值
   * @return 整数或默认值
   */
  public static int readInt(int defaultValue) {
    int n;
    for (; ; ) {
      String str = readKeyBoard(10, true);
      if (str.equals("")) {
        return defaultValue;
      }

      //异常处理...
      try {
        n = Integer.parseInt(str);
        break;
      } catch (NumberFormatException e) {
        System.out.print("💔数字输入错误!\n请在此处重新输入: ");
      }
    }
    return n;
  }

  public static double readDouble(double defaultValue) {
    double n;
    for (; ; ) {
      String str = readKeyBoard(10, true);
      if (str.equals("")) {
        return defaultValue;
      }

      //异常处理...
      try {
        n = Double.parseDouble(str);
        break;
      } catch (NumberFormatException e) {
        System.out.print("💔数字输入错误!\n请在此处重新输入: ");
      }
    }
    return n;
  }

  /**
   * 功能：读取键盘输入的指定长度的字符串
   *
   * @param limit 限制的长度
   * @return 指定长度的字符串
   */

  public static String readString(int limit) {
    return readKeyBoard(limit, false);
  }

  /**
   * 功能：读取键盘输入的指定长度的字符串或默认值，如果直接回车，返回默认值，否则返回字符串
   *
   * @param limit        限制的长度
   * @param defaultValue 指定的默认值
   * @return 指定长度的字符串
   */

  public static String readString(int limit, String defaultValue) {
    String str = readKeyBoard(limit, true);
    return str.equals("") ? defaultValue : str;
  }


  /**
   * 功能：读取键盘输入的确认选项，Y或N
   * 将小的功能，封装到一个方法中.
   *
   * @return Y或N
   */
  public static char readConfirmSelection() {
    System.out.print("(・∀・(・∀・(・∀・*)确认你要进行删除吗？(Y/N): ");
    char c;
    for (; ; ) {//无限循环
      //在这里，将接受到字符，转成了大写字母
      //y => Y n=>N
      String str = readKeyBoard(1, false).toUpperCase();
      c = str.charAt(0);
      if (c == 'Y' || c == 'N') {
        break;
      } else {
        System.out.print("💔选择错误!\n请在此处重新输入: ");
      }
    }
    return c;
  }

  public static char readUpdateSelection() {
    System.out.print("(・∀・(・∀・(・∀・*)确认你要进行修改吗？(Y/N): ");
    char c;
    for (; ; ) {//无限循环
      //在这里，将接受到字符，转成了大写字母
      //y => Y n=>N
      String str = readKeyBoard(1, false).toUpperCase();
      c = str.charAt(0);
      if (c == 'Y' || c == 'N') {
        break;
      } else {
        System.out.print("💔选择错误!\n请在此处重新输入: ");
      }
    }
    return c;
  }

  /**
   * 功能： 读取一个字符串
   *
   * @param limit       读取的长度
   * @param blankReturn 如果为true ,表示 可以读空字符串。
   *                    如果为false表示 不能读空字符串。
   *                    <p>
   *                    如果输入为空，或者输入大于limit的长度，就会提示重新输入。
   */
  private static String readKeyBoard(int limit, boolean blankReturn) {

    //定义了字符串
    String line = "";

    //scanner.hasNextLine() 判断有没有下一行
    while (scanner.hasNextLine()) {
      line = scanner.nextLine();//读取这一行

      //如果line.length=0, 即用户没有输入任何内容，直接回车
      if (line.length() == 0) {
        if (blankReturn) return line;//如果blankReturn=true,可以返回空串
        else {
          //如果blankReturn=false,不接受空串，必须输入内容
          System.out.print("💔输入内容为空!\n请在此处重新输入: ");
          continue;
        }
      }

      //如果用户输入的内容大于了 limit，就提示重写输入
      //如果用户如的内容 >0 <= limit ,我就接受
      if (line.length() > limit) {
        System.out.print("💔输入长度（不能大于" + limit + "）错误，请重新输入：");
        continue;
      }
      break;
    }

    line = fourToFour(line);
    return line;
  }

  // -- 统一将大写字母改为小写字母
  public static String upperToDowner(String line) {
    for (int i = 0; i < line.length(); i++) {
      if (line.charAt(i) >= 97 && line.charAt(i) <= 123) {
        char X = line.charAt(i);
        char x = (char) (line.charAt(i) - 32);
        line = line.replace(X, x);
      }
    }
    return line;
  }

  // -- 将四个字母的Exit或者其他新加入的字符转为全小写
  public static String fourToFour(String line) {
    if (line.length() == 4) {
      if (line.charAt(0) == 'E') {
        line = line.replace('E', 'e');
      }
      if (line.charAt(1) == 'X') {
        line = line.replace('X', 'x');
      }
      if (line.charAt(2) == 'I') {
        line = line.replace('I', 'i');
      }
      if (line.charAt(3) == 'T') {
        line = line.replace('T', 't');
      }
    }

    return line;
  }

  public static boolean readExitConfirm(boolean loop) {
    System.out.print("~~>_<~~您确定要退出该系统吗？(Y/N): ");
    char c;
    for (; ; ) {//无限循环
      //在这里，将接受到字符，转成了大写字母
      //y => Y n=>N
      String str = readKeyBoard(1, false).toUpperCase();
      c = str.charAt(0);
      if (c == 'Y') {
        System.out.println("♥退出成功，欢迎下次使用!");
        loop = false;
        break;
      }
      if (c != 'N') {
        System.out.print("💔选择错误，请重新输入：");
      } else {
        break;
      }
    }

    return loop;
  }

  // -- 退出当前登录账号                  ->>> 二级系统使用服务
  public static boolean readOutLookConfirm(boolean outlook) {
    System.out.print("~~>_<~~您确定要退出该账号吗？(Y/N): ");
    char c;
    for (; ; ) {//无限循环
      //在这里，将接受到字符，转成了大写字母
      //y => Y n=>N
      String str = readKeyBoard(1, false).toUpperCase();
      c = str.charAt(0);
      if (c == 'Y') {
        System.out.println("♥退出账号成功，欢迎下次登录!");
        outlook = true;
        break;
      }
      if (c != 'N') {
        System.out.print("💔选择错误，请重新输入：");
      } else {
        break;
      }
    }

    return outlook;
  }
  // -- TODO 工具输入类，有需要请自行添加!
}

