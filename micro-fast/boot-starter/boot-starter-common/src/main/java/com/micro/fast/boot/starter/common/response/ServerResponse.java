package com.micro.fast.boot.starter.common.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 统一服务响应类,方法返回值
 * 序列化中不包含为空值的字段
 * @author lishouyu 18332763730@163.com 2017/10/02
 * @version 1.0
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ServerResponse<T> {
  /**
   * 获取数据返回的提示数据字典码
   */
  private Integer code;
  /**
   * 获取数据成功或者失败的时候提示的消息
   */
  private String msg;
  /**
   * 返回的数据
   */
  T data;

  /**
   * 所有的构造方法均为私有，不允许外部直接调用构造方法
   */
  public ServerResponse() {

  }

  private ServerResponse(Integer code) {
    this(code, null, null);
  }

  private ServerResponse(Integer code, String msg) {
    this(code, msg, null);
  }

  /**
   * 为了避免T 为string的时候调用的是 上面的构造方法，需要对构造方法进行包装。构造方法设置为私有，不允许外部调用
   *
   * @param code 　响应代码
   * @param data 　相应数据
   */
  private ServerResponse(Integer code, T data) {
    this(code, null, data);
  }

  private ServerResponse(Integer code, String msg, T data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  /**
   * 返回代码是小于等于0的时候表示请求数据失败，或者进行验证时失败
   *
   * @return　是否成功
   */
  @JsonIgnore //使其不再json序列化结果之中
  public boolean isSuccess() {
    return BaseConst.ServerResponseCode.SUCCESS.getCode().intValue() == this.code;
  }

  //请求成功
  public static <T> ServerResponse<T> success() {

    return new ServerResponse<T>(BaseConst.ServerResponseCode.SUCCESS.getCode());
  }

  public static <T> ServerResponse<T> successMsg(String msg) {

    return new ServerResponse<T>(
        BaseConst.ServerResponseCode.SUCCESS.getCode(),
        msg);
  }

  public static <T> ServerResponse<T> successData(T data) {

    return new ServerResponse<T>(BaseConst.ServerResponseCode.SUCCESS.getCode(),
        data);
  }

  public static <T> ServerResponse<T> successMsgData(String msg, T data) {

    return new ServerResponse<T>(BaseConst.ServerResponseCode.SUCCESS.getCode(),
        msg, data);
  }

  //请求失败
  public static <T> ServerResponse<T> error() {
    return new ServerResponse<T>(BaseConst.ServerResponseCode.ERROR.getCode());
  }

  public static <T> ServerResponse<T> errorMsg(String msg) {
    return new ServerResponse<T>(BaseConst.ServerResponseCode.ERROR.getCode(), msg);
  }

  public static <T> ServerResponse<T> errorCodeMsg(Integer code, String msg) {
    return new ServerResponse<T>(code, msg);
  }
  public static <T> ServerResponse<T> errorMsgData(String msg,T data){
    return  new ServerResponse<>(BaseConst.ServerResponseCode.ERROR.getCode(),msg,data);
  }
}
