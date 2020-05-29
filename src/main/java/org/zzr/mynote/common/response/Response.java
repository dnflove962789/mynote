package org.zzr.mynote.common.response;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author yangkaile
 * @date 2018-10-22 14:29:07
 * 规定Controller统一的消息返回格式
 */
public class Response {

    public static ResponseEntity ok(Object object){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(object);
    }

    /**
     * 返回OK
     * @param object
     * @return
     */
    public static ResponseEntity ok(ResultData object){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON).body(object);
    }

    /**
     * 返回OK
     * @return
     */
    public static ResponseEntity ok(){
        ResultData response = new ResultData().success();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * 异常请求
     * @return
     */
    public static ResponseEntity badRequest(){
        ResultData response = new ResultData().fail().message("请求参数异常");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * 没有登录
     * @return
     */
    public static ResponseEntity unauthorized(){
        ResultData response = new ResultData().fail().message("用户未登录");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * 没有访问权限
     * @return
     */
    public static ResponseEntity forbidden(){
        ResultData response = new ResultData().fail().message("没有权限");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }

    /**
     * 系统内部错误
     * @return
     */
    public static ResponseEntity error(){
        ResultData response = new ResultData().fail().message("系统错误");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON).body(response);
    }
}
