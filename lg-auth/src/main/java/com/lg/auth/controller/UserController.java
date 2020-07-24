package com.lg.auth.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.auth.common.AuthConstants;
import com.lg.auth.entity.UserRole;
import com.lg.auth.feign.UserClient;
import com.lg.auth.service.IUserRoleService;
import com.lg.auth.utils.JwtUtil;
import com.lg.common.pojo.ResponseResult;
import com.lg.common.utils.MD5;
import com.lg.user.api.pojo.UserVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhanggm
 * @since 2020-06-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserClient userClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录接口
     * @param userVo
     * @return
     */
    @PostMapping("login")
    public ResponseResult login(@RequestBody UserVo userVo){
        //判断验证码是否正确
        if(StringUtils.isBlank(userVo.getVcode())){
            return ResponseResult.error(10000,"验证码为空");
        }
        String cacheKey = AuthConstants.LOGIN_VCODE_REIDS_KEY+userVo.getUserKey();
        Object vcode = redisTemplate.opsForValue().get(cacheKey);
        if(!userVo.getVcode().equals(vcode)){
            return ResponseResult.error(10000,"验证码错误");
        }
        //验证用户名和密码是否为空
        if(StringUtils.isBlank(userVo.getPassword()) || StringUtils.isBlank(userVo.getPassword())){
            return ResponseResult.error(10000,"用户名或密码不能为空");
        }
        //查询一条用户信息
        UserVo userInfo = userClient.getUserVoByUsername(userVo.getUsername());
        //用户是否停用
        if(!userInfo.getEnabled()){
            return ResponseResult.error(10000,"用户名或密码不能为空");
        }
        //验证密码
        if(userInfo==null || !userInfo.getPassword().equals(userVo.getPassword())){
            return ResponseResult.error(10000,"用户名或密码不能为空");
        }
        //生成token信息 Jwt
        String token = jwtUtil.createJWT(userInfo.getId() + "", userInfo.getUsername(), null);
        userInfo.setToken(token);
        //设置密码为空
        userInfo.setPassword(null);
        userInfo.setUsername(null);
        return ResponseResult.success(userInfo);
    }

    @GetMapping("getVcode")
    public void getVcode(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //客户端唯一标识
        String userKey = request.getParameter("userKey");
        //生成随机验证码
        String vcode = RandomUtil.randomNumbers(4);
        //生成图片验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100);
        Image image = circleCaptcha.createImage(vcode);
        //输出验证码
        ImageIO.write((RenderedImage)image, "png", response.getOutputStream());
        //在服务端保存验证码，登录时验证,过期时间10分钟
        String cacheKey = AuthConstants.LOGIN_VCODE_REIDS_KEY+userKey;
        redisTemplate.opsForValue().set(cacheKey,vcode,600,TimeUnit.SECONDS);
    }

    /**
     * 保存用户角色
     * @param userVo
     * @return
     */
    @PostMapping("bindRoles")
    public ResponseResult bindRoles(@RequestBody UserVo userVo){
        //删除角色
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",userVo.getId());
        iUserRoleService.remove(queryWrapper);
        //添加用户角色
        userVo.getRoleIdList().forEach(roleId->{
            UserRole userRole = new UserRole();
            userRole.setUid(userVo.getId());
            userRole.setRid(roleId);
            iUserRoleService.save(userRole);
        });
        return ResponseResult.success();
    }



    /**
     *  查询用户关联的角色Id
     * @return
     */
    @GetMapping("getRoleIdListByUserId")
    public ResponseResult getRoleIdListByUserId(String userId){
        //查询用户角色
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid",userId);
        List<UserRole> list = iUserRoleService.list(queryWrapper);
        //返回角色Id
        List<Integer> roleIdList = list.stream().map(ur->ur.getRid()).collect(Collectors.toList());
        return ResponseResult.success(roleIdList);
    }

    /**
     * 用户登录初始化
     * 返回唯一标识
     * @return
     */
    @GetMapping("loginInit")
    public ResponseResult loginInit(){
        String userKey = UUID.randomUUID().toString();
        return ResponseResult.success(userKey);
    }

}
