package com.houpu.crowd.util;

/**
 * ajax请求返回的消息对象
 * @param <T>
 */
public class ResultEntity<T> {

    private final static String SUCCESS="SUCCESS";
    private final static String FAILED="FAILED";

    //返回信息
    private String message;
    //返回的数据
    private T data;
    //返回当前结果是成功还是失败
    private String result;

    /**
     * 请求成功时返回的不带数据的对象
     * @param <Type>
     * @return
     */
    public static  <Type> ResultEntity<Type> successWihtOutData(){
        return new ResultEntity<Type>(SUCCESS,null,null);
    }

    /**
     * 请求成功时带数据的返回对象
     * @param data
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> successWhitData(Type data){
        return new ResultEntity<Type>(SUCCESS,data,null);
    }

    /**
     * 请求失败返回的对象
     * @param message
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String message){
        return  new ResultEntity<Type>(FAILED,null,message);
    }

    public ResultEntity() {
    }

    public ResultEntity(String result, T data, String message) {
        this.message = message;
        this.data = data;
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResltEntity{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", result='" + result + '\'' +
                '}';
    }
}
