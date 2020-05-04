package org.zzr.mynote.userinfo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zzr.mynote.entity.EmailLog;
import org.zzr.mynote.entity.UserInfo;
import org.zzr.mynote.mapper.EmailLogMapper;
import org.zzr.mynote.mapper.UserInfoMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void insert(){
        UserInfo userInfo = new UserInfo();
        userInfo.setType(0);
        userInfo.setEmail("vc@qq.com");
        userInfo.setPassword("4444");
        int rows = userInfoMapper.insert(userInfo);
        System.out.println(rows);
    }

    @Test
    public void selectById(){
        UserInfo userInfo = userInfoMapper.selectById(1);
        System.out.println(userInfo);
    }

    @Test
    public void selectByWrapper(){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        //queryWrapper.like("email","gu").eq("type",0);
        //queryWrapper.like("email","gu").between("type",0, 1).isNotNull("type");
        //queryWrapper.likeRight("email", "abc").or().ge("type", 25).orderByDesc("type");
        /*queryWrapper.apply("date_format(createTime, '%Y-%m=%d')='2020-03-21' or true or true")
                .inSql("id", "select userid from user_card where email='guyexing@foxmail.com'");*/

        //queryWrapper.likeRight("email","gu").and(x -> x.lt("type",1).or().isNotNull("email"));
        //queryWrapper.nested(x -> x.lt("type",1).or().isNotNull("email")).likeRight("email","gu");
        //queryWrapper.in("type", Arrays.asList(1,2,3));

        //queryWrapper.select("id","email").like("email","gu").eq("type",0);
        queryWrapper.select(UserInfo.class, x->!x.getColumn().equals("createTime") && !x.getColumn().equals("status"));



        List<UserInfo> userInfoList = userInfoMapper.selectList(queryWrapper);
        userInfoList.forEach(System.out::println);
    }

    @Test
    public void selectByCondition(){
        String email = null;
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(email),"email","gu").eq("type",0);
        List<UserInfo> userInfoList = userInfoMapper.selectList(queryWrapper);
        userInfoList.forEach(System.out::println);
    }

    @Test
    public void selectByEntity(){
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("abc");
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>(userInfo);

        List<UserInfo> userInfoList = userInfoMapper.selectList(queryWrapper);
        userInfoList.forEach(System.out::println);
    }

    @Test
    public void selectByAllEq(){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("email", "a");
        map.put("type", null);
        queryWrapper.allEq(map);

        List<UserInfo> userInfoList = userInfoMapper.selectList(queryWrapper);
        userInfoList.forEach(System.out::println);
    }

    @Test
    public void selectByMaps(){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(type) avg", "min(type) min").groupBy("id").having("sum(avg)<{0}",20);


        List<Map<String, Object>> maps = userInfoMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    @Test
    public void selectByCount(){

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("email","gu").eq("type",0);
        Integer integer = userInfoMapper.selectCount(queryWrapper);
        System.out.println(integer);
    }

    @Test
    public void selectByOne(){
        //必须是返回一条，不然报错
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("type",0);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        System.out.println(userInfo);
    }

    @Test
    public void selectByLambda1(){
        LambdaQueryWrapper<UserInfo> lambdaQuery = Wrappers.lambdaQuery();
        //LambdaQueryWrapper<Object> queryWrapper = new QueryWrapper<>().lambda();
        //LambdaQueryWrapper<Object> queryWrapper = new LambdaQueryWrapper<>();
        lambdaQuery.like(UserInfo::getEmail, "abc");

        List<UserInfo> userInfos = userInfoMapper.selectList(lambdaQuery);
        userInfos.forEach(System.out::println);
    }

    @Test
    public void selectByLambda2(){
        LambdaQueryWrapper<UserInfo> lambdaQuery = Wrappers.lambdaQuery();
        //LambdaQueryWrapper<Object> queryWrapper = new QueryWrapper<>().lambda();
        //LambdaQueryWrapper<Object> queryWrapper = new LambdaQueryWrapper<>();
        lambdaQuery.likeRight(UserInfo::getEmail, "abc")
                .and(x -> {
                    x.lt(UserInfo::getType, 1)
                            .isNotNull(UserInfo::getType);
                });

        List<UserInfo> userInfos = userInfoMapper.selectList(lambdaQuery);
        userInfos.forEach(System.out::println);
    }

    @Test
    public void selectByLambda3(){
        List<UserInfo> list = new LambdaQueryChainWrapper<UserInfo>(userInfoMapper)
                .likeRight(UserInfo::getEmail, "abc")
                .and(x -> {
                    x.lt(UserInfo::getType, 1)
                            .isNotNull(UserInfo::getType);
                }).list();

        list.forEach(System.out::println);
    }

    @Test
    public void selectByPage(){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("type",0);
        Page<UserInfo> page = new Page<>(1,2, false);
        IPage<UserInfo> iPage = userInfoMapper.selectPage(page, queryWrapper);
        System.out.println(iPage.getPages());
        System.out.println(iPage.getTotal());

        iPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void selectByMyPage(){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("type",0);
        Page<UserInfo> page = new Page<>(1,2);
        IPage<UserInfo> iPage = userInfoMapper.selectUserInfoPage(page, queryWrapper);
        System.out.println(iPage.getPages());
        System.out.println(iPage.getTotal());

        iPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void updateByWrapper(){
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("email", "vc@qq.com");

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("ass@123.com");
        int rows = userInfoMapper.update(userInfo, updateWrapper);
        System.out.println(rows);

    }

    @Test
    public void updateByWrapper2(){
        UserInfo infoWhere = new UserInfo();
        infoWhere.setEmail("1111@123.com");
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>(infoWhere);

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail("last@123.com");
        int rows = userInfoMapper.update(userInfo, updateWrapper);
        System.out.println(rows);

    }

    @Test
    public void updateByWrapper3(){
        UserInfo infoWhere = new UserInfo();
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();

        updateWrapper.eq("email", "342@123.com").set("email", "1111@123.com");

        int rows = userInfoMapper.update(null, updateWrapper);
        System.out.println(rows);

    }

    @Test
    public void updateByLambda(){
        LambdaUpdateWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaUpdateWrapper<>();
        userInfoLambdaQueryWrapper.eq(UserInfo::getEmail,"111@.cp,").set(UserInfo::getStatus, 1);

        int rows = userInfoMapper.update(null, userInfoLambdaQueryWrapper);
        System.out.println(rows);

    }

    @Test
    public void updateByLambda2(){
        boolean rows = new LambdaUpdateChainWrapper<UserInfo>(userInfoMapper)
                .eq(UserInfo::getEmail, "111@.cp,")
                .set(UserInfo::getStatus, 0)
                .update();

        System.out.println(rows);

    }

    @Test
    public void deleteById(){
        int rows = userInfoMapper.deleteById(3);
        System.out.println(rows);

    }

    @Test
    public void deleteByMap(){
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("email","123");

        int rows = userInfoMapper.deleteByMap(hashMap);
        System.out.println(rows);

    }

    @Test
    public void deleteByBatchIds(){

        int rows = userInfoMapper.deleteBatchIds(Arrays.asList(1,2,3,4));
        System.out.println(rows);

    }

    @Test
    public void deleteByWrapper(){
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = Wrappers.<UserInfo>lambdaQuery();
        userInfoLambdaQueryWrapper.eq(UserInfo::getEmail,"123@");
        int rows = userInfoMapper.delete(userInfoLambdaQueryWrapper);
        System.out.println(rows);

    }
}
