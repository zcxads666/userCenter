package com.example.useradmin.common;

/**
 * 返回工具类
 */


/**
 * 成功返回
 */
public class ResutUtils {

    public static <T> BaseResponse<T> success(T data)
    {
        return new BaseResponse<>(0,data,"ok","");
    }


    /**
     * 失败返回1
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode)
    {
        return new BaseResponse<>(errorCode);
    }



    /**
     * 失败返回2
     * @param
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String description)
    {
        return new BaseResponse<>(errorCode.getCode(),null,errorCode.getMessage(),description);
    }

    /**
     * 失败返回3
     * @param
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode,String message,String description)
    {
        return new BaseResponse<>(errorCode.getCode(),null,message,description);
    }

    /**
     * 失败返回4
     * @param
     * @return
     */
    public static BaseResponse error(int code,String message,String description)
    {
        return new BaseResponse<>(code,null,message,description);
    }
}
