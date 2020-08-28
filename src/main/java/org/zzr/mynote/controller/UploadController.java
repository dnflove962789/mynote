package org.zzr.mynote.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.zzr.mynote.common.configuration.RequestConfig;
import org.zzr.mynote.common.response.Response;
import org.zzr.mynote.common.response.ResultData;
import org.zzr.mynote.common.util.JwtUtils;
import org.zzr.mynote.service.IUserCardService;
import org.zzr.mynote.service.impl.UserCardServiceImpl;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class UploadController {

    @Autowired
    private IUserCardService userCardService;

    @PostMapping
    public ResponseEntity upload(MultipartFile file ,String token, HttpServletRequest request){
        if(file == null){
            return Response.badRequest();
        }
        //String token = request.getHeader(RequestConfig.TOKEN);
        Integer userId = JwtUtils.getUserIdForLogin(token);
        if(userId == null){
            return Response.unauthorized();
        }
        ResultData resultData = userCardService.setPortrait(file, userId);
        return Response.ok(resultData);
    }
}
