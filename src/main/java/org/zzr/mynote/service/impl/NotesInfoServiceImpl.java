package org.zzr.mynote.service.impl;

import org.zzr.mynote.entity.NotesInfo;
import org.zzr.mynote.mapper.NotesInfoMapper;
import org.zzr.mynote.service.INotesInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 笔记本 服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-09-03
 */
@Service
public class NotesInfoServiceImpl extends ServiceImpl<NotesInfoMapper, NotesInfo> implements INotesInfoService {

}
