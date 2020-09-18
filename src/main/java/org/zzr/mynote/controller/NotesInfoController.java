package org.zzr.mynote.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.zzr.mynote.common.annotation.UserLoginToken;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.common.util.TokenUtils;
import org.zzr.mynote.entity.NotesInfo;
import org.zzr.mynote.service.INotesInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 笔记本 前端控制器
 * </p>
 *
 * @author zzr
 * @since 2020-09-03
 */
@RestController
@RequestMapping("/notes-info")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class NotesInfoController {

    @Resource
    private INotesInfoService service;

    @Autowired
    private HttpServletRequest request;

    @UserLoginToken
    @PostMapping
    public ResponseEntity add(@RequestBody NotesInfo notesInfo, HttpServletRequest request){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(userId == null){
            return Response.badRequest();
        }
        notesInfo.setUserId(userId);
        service.save(notesInfo);
        System.out.println(notesInfo);
        return Response.ok(new ResultData().success().data(notesInfo));
    }

    @UserLoginToken
    @PostMapping("/update")
    public ResponseEntity update(@RequestBody NotesInfo notesInfo){
        // 至少要修改一个数据
        if(!StringUtils.hasValue(notesInfo.getTitle(), notesInfo.getSubTitle(), notesInfo.getSort())){
            return Response.badRequest();
        }
        boolean result = service.updateById(notesInfo);
        ResultData resultData = new ResultData();
        resultData = result == true?resultData.success():resultData.fail();
        return Response.ok(resultData);
    }

    @UserLoginToken
    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody NotesInfo notesInfo){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        //用户ID和笔记本ID是必填参数
        if(StringUtils.isEmpty(userId, notesInfo.getId())){
            return Response.badRequest();
        }
        ResultData resultData = new ResultData();
        boolean result = service.removeById(notesInfo.getId());
        resultData = result == true?resultData.success():resultData.fail();
        return Response.ok(resultData);
    }

    @UserLoginToken
    @GetMapping
    public ResponseEntity get(HttpServletRequest request){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(userId == null){
            return Response.badRequest();
        }
        List<NotesInfo> notesInfos = service.list(new QueryWrapper<NotesInfo>().eq("userId", userId));
        return Response.ok(new ResultData().success().data(notesInfos));
    }
}
