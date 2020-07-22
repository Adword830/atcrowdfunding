package com.houpu.crowd.util;

import com.houpu.crowd.util.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
    /**
     * 判断是ajax请求还是普通请求
     * @param request
     * @return
     */
    public static Boolean judgeRequestType(HttpServletRequest request){
        String ContentType = request.getHeader("Content-Type");
        String XRequestedWith = request.getHeader("X-Requested-With");

        if(ContentType !=null && XRequestedWith != null){
            return true;
        }

        return false;
    }

    /**
     * 进行Md5加密的工具方法
     * @param source 传入的明文字符串
     * @return 加密之后的结果
     */
    public static String md5(String source){
        // 1.判断字符串是否为有效字符串
        if(source==null||source.length()==0){
           // 2.抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            // 3.获取MessageDigest对象
            String algorithm="md5";
            MessageDigest instance = MessageDigest.getInstance(algorithm);
            // 4.获取明文字节数组
            byte[] bytes = source.getBytes();
            // 5.进行md5加密
            byte[] output = instance.digest(bytes);
            // 6.将加密之后的字符节转换为字符串
            int signum=1;
            BigInteger bigDecimal=new BigInteger(signum,output);
            // 7.使用16进制
            int radix=16;
            String encoded = bigDecimal.toString(radix);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
            /* 遍历加密之后的字节码（-3110-365773-7089-85-6686-3287-1415-12062）
            String  num="";
            for (int i=0;i<output.length;i++){
                num+=output[i];
            }
            System.out.println(num);*/
}
