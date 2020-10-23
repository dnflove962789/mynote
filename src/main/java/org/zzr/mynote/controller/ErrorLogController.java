package org.zzr.mynote.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.util.ErrorLogUtils;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzr
 * @since 2020-10-14
 */
@ControllerAdvice
@RestController
@RequestMapping("/error-log")
public class ErrorLogController {
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity errorHandler(Exception ex) {
        ErrorLogUtils.errorLog(ex);
        return Response.error();
    }
}
