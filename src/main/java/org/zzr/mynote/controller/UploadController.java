package org.zzr.mynote.controller;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zzr.mynote.common.annotation.UserLoginToken;
import org.zzr.mynote.common.configuration.RequestConfig;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.FileUtils;
import org.zzr.mynote.common.util.ImageUtils;
import org.zzr.mynote.common.util.JwtUtils;
import org.zzr.mynote.common.util.TokenUtils;
import org.zzr.mynote.service.IUserCardService;
import org.zzr.mynote.service.impl.UserCardServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class UploadController {

    @Autowired
    private IUserCardService userCardService;

    /*@UserLoginToken*/
    @PostMapping
    public ResponseEntity upload(MultipartFile file ,String token, HttpServletRequest request){
        if(file == null){
            return Response.badRequest();
        }
        //String token = request.getHeader(RequestConfig.TOKEN);
        //Integer userId = TokenUtils.getUserIdFromRequest(request);
        Integer userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
        if(userId == null){
            return Response.unauthorized();
        }
        ResultData resultData = userCardService.setPortrait(file, userId);
        return Response.ok(resultData);
    }

    @UserLoginToken
    @PostMapping("/img")
    public ResponseEntity uploadImg(MultipartFile file,HttpServletRequest request,Integer noteId){
        Integer userId = TokenUtils.getUserIdFromRequest(request);
        if(file == null){
            return Response.badRequest();
        }
        String formatTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String fileName = null;
        try {
            fileName = ImageUtils.saveImg(file, noteId ,userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        String noteUrl = FileUtils.getNoteUrl(fileName, userId);
        return Response.ok(new ResultData().success().data(noteUrl));
    }
}
