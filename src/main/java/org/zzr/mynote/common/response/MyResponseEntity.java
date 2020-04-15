package org.zzr.mynote.common.response;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Data
public class MyResponseEntity {

    private final static int SUCCESS_CODE = 0;
    private final static int FAIL_CODE = 1;

    /**
     * 是否成功
     */
    private int isSuccess;

    /**
     * 返回的数据
     */
    private Object data;

    /**
     * 提示信息
     */
    private String message;

    public static MyResponseEntity success(){
        MyResponseEntity myResponseEntity = new MyResponseEntity();
        myResponseEntity.isSuccess = SUCCESS_CODE;
        return myResponseEntity;
    }

    public static MyResponseEntity success(Object data){
        MyResponseEntity myResponseEntity = new MyResponseEntity();
        myResponseEntity.isSuccess = SUCCESS_CODE;
        myResponseEntity.data = data;
        return myResponseEntity;
    }

    public static MyResponseEntity fail(String message){
        MyResponseEntity myResponseEntity = new MyResponseEntity();
        myResponseEntity.isSuccess = FAIL_CODE;
        myResponseEntity.message = message;
        return myResponseEntity;
    }
}
