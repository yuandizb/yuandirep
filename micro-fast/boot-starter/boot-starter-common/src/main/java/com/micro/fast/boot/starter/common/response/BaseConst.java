package com.micro.fast.boot.starter.common.response;

import com.micro.fast.boot.starter.common.util.ExceptionUtil;

/**
 * 数据字典
 *
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
public class BaseConst {
  /**
   * 参数校验发出的异常
   */
  public static final String DEFAULT_VALIDATE_CODE = "888888";

  public static final String BASEMSG_PREFIX = DEFAULT_VALIDATE_CODE+ ExceptionUtil.split;

  /**
   * 分页排序的分隔符
   */
  public static final String ORDER_SPLIT = "_";
  /**
   * 分页排序的连接符号
   */
  public static final String ORDER_CONTACT  = " ";
  /**
   * 服务器响应数据字典
   */
  public enum ServerResponseCode {
    /**
     * 获取数据成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 获取数据失败
     */
    ERROR(1, "ERROR");
    private Integer code;
    private String msg;

    ServerResponseCode(Integer code, String msg) {
      this.code = code;
      this.msg = msg;
    }

    public Integer getCode() {
      return code;
    }

    public String getMsg() {
      return msg;
    }
  }

  /**
   * 系统响应码
   */
  public enum SystemResponse {
    DATABASE_INSERT_FAILURE(00000, "系统错误,请稍后重试");
    private Integer code;
    private String msg;

    SystemResponse(Integer code, String msg) {
      this.code = code;
      this.msg = msg;
    }

    public Integer getCode() {
      return code;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }
  }

  /**
   * 用户操作响应码
   */
  public enum UserResponse {
    USERNAME_IS_HAVE(10000, "用户名被占用"),
    EMAIL_IS_HAVE(10001, "邮箱被占用"),
    TWO_PASSWORD_NOT_SAME(10002,"两次密码不一致"),
    USER_IS_NOT_FOUND(10003,"用户未找到"),
    UN_HAVE_PERMISSION(10004,"您没有该操作权限");
    public Integer code;
    private String msg;

    UserResponse(Integer code, String msg) {
      this.code = code;
      this.msg = msg;
    }

    public Integer getCode() {
      return code;
    }

    public void setCode(Integer code) {
      this.code = code;
    }

    public String getMsg() {
      return msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }
  }
}
