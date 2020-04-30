package org.zzr.mynote.common.response;

import java.util.HashMap;

/**
 * 相应返回实体
 */
public class ResponseEntity extends HashMap<String, Object> {

    private final static int SUCCESS_CODE = 0;
    private final static int FAIL_CODE = 1;

    public ResponseEntity success(){
        this.put("code", SUCCESS_CODE);
        return this;
    }

    public ResponseEntity fail(){
        this.put("code", FAIL_CODE);
        return this;
    }

    public ResponseEntity message(String message){
        this.put("message", message);
        return this;
    }

    public ResponseEntity data(Object data){
        this.put("data", data);
        return this;
    }

    @Override
    public ResponseEntity put(String key, Object data){
        super.put(key, data);
        return this;
    }

}
