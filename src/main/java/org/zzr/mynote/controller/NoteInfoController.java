package org.zzr.mynote.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.zzr.mynote.common.annotation.UserLoginToken;
import org.zzr.mynote.common.configuration.PublicConstant;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.StringUtils;
import org.zzr.mynote.common.util.TokenUtils;
import org.zzr.mynote.entity.NoteInfo;
import org.zzr.mynote.entity.NotesInfo;
import org.zzr.mynote.service.INoteInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 笔记 前端控制器
 * </p>
 *
 * @author zzr
 * @since 2020-09-21
 */
@RestController
@RequestMapping("/note-info")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class NoteInfoController {
    @Resource
    private INoteInfoService noteInfoService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取笔记本中的笔记列表
     * @param notesId
     * @return
     */
    @UserLoginToken
    @GetMapping("/list")
    public ResponseEntity getList(@RequestParam String notesId){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(StringUtils.isEmpty(userId,notesId)){
            return Response.badRequest();
        }
        List<NoteInfo> list = noteInfoService.lambdaQuery().eq(NoteInfo::getNotesId, notesId)
                .eq(NoteInfo::getUserId, userId)
                .orderByAsc(NoteInfo::getSort)
                .list();
        return Response.ok(new ResultData().success().data(list));
    }

    /**
     * 创建笔记
     * @param noteInfo
     * @return
     */
    @UserLoginToken
    @PostMapping
    public ResponseEntity create(@RequestBody NoteInfo noteInfo){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(StringUtils.isEmpty(userId, noteInfo.getNotesId(), noteInfo.getTitle())){
            return Response.badRequest();
        }
        Integer type = noteInfo.getType();
        if(type == null){
            type = PublicConstant.NOTE_TYPE_RICH_TEXT;
        }else{
            if(type != PublicConstant.NOTE_TYPE_RICH_TEXT && type != PublicConstant.NOTE_TYPE_MARK_DOWN){
                return Response.badRequest();
            }
        }
        noteInfo.setUserId(userId);
        boolean saveFlag = noteInfoService.save(noteInfo);
        if(saveFlag){
            return Response.ok(new ResultData().success());
        }else{
            return Response.ok(new ResultData().fail());
        }
    }

    /**
     * 更新排序
     * @param noteInfo
     * @return
     */
    @UserLoginToken
    @PostMapping("/reSort")
    public ResponseEntity reSort(@RequestBody NoteInfo noteInfo){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(StringUtils.isEmpty(userId, noteInfo.getId(), noteInfo.getSort())){
            return Response.badRequest();
        }
        boolean updateFlag = noteInfoService.update(noteInfo,
                new LambdaUpdateWrapper<NoteInfo>().eq(NoteInfo::getId, noteInfo.getId())
                .eq(NoteInfo::getUserId, userId));
        if(updateFlag){
            return Response.ok(new ResultData().success());
        }else{
            return Response.ok(new ResultData().fail());
        }
    }

    /**
     * 保存笔记内容
     * @param noteInfo
     * @return
     */
    @UserLoginToken
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody NoteInfo noteInfo){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(StringUtils.isEmpty(userId, noteInfo.getId())){
            return Response.badRequest();
        }
        noteInfo.setUserId(userId);
        boolean updateFlag = noteInfoService.updateById(noteInfo);
        if(updateFlag){
            return Response.ok(new ResultData().success());
        }else{
            return Response.ok(new ResultData().fail());
        }
    }

    /**
     * 获取笔记
     * @param id
     * @return
     */
    @UserLoginToken
    @GetMapping
    public ResponseEntity get(@RequestParam String id){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(StringUtils.isEmpty(userId, id)){
            return Response.badRequest();
        }
        NoteInfo noteInfo = noteInfoService.lambdaQuery().eq(NoteInfo::getId, id)
                .eq(NoteInfo::getUserId, userId).one();
        return Response.ok(new ResultData().success().data(noteInfo));
    }

    @PostMapping("/del")
    public ResponseEntity del(@RequestBody NoteInfo noteInfo){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(StringUtils.isEmpty(userId,noteInfo.getId())){
            return Response.badRequest();
        }
        boolean removeFlag = noteInfoService.remove(new LambdaQueryWrapper<NoteInfo>()
                .eq(NoteInfo::getId, noteInfo.getId()).eq(NoteInfo::getUserId, userId));
        if(removeFlag){
            return Response.ok(new ResultData().success());
        }else{
            return Response.ok(new ResultData().fail());
        }
    }
}
