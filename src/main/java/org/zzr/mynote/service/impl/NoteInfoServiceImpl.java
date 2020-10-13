package org.zzr.mynote.service.impl;

import org.zzr.mynote.entity.NoteInfo;
import org.zzr.mynote.mapper.NoteInfoMapper;
import org.zzr.mynote.service.INoteInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 笔记 服务实现类
 * </p>
 *
 * @author zzr
 * @since 2020-09-21
 */
@Service
public class NoteInfoServiceImpl extends ServiceImpl<NoteInfoMapper, NoteInfo> implements INoteInfoService {

}
