package org.zzr.mynote.common.response;

import java.util.HashMap;

/**
 * 相应返回实体
 */
public class ResultData extends HashMap<String, Object> {

    private final static int SUCCESS_CODE = 0;
    private final static int FAIL_CODE = 1;

    public ResultData success(){
        this.put("code", SUCCESS_CODE);
        return this;
    }

    public ResultData fail(){
        this.put("code", FAIL_CODE);
        return this;
    }

    public ResultData message(String message){
        this.put("message", message);
        return this;
    }

    public ResultData data(Object data){
        this.put("data", data);
        return this;
    }

    @Override
    public ResultData put(String key, Object data){
        super.put(key, data);
        return this;
    }

}
