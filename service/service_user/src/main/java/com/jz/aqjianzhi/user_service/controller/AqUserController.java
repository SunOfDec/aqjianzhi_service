package com.jz.aqjianzhi.user_service.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jz.aqjianzhi.user_service.entity.AqUser;
import com.jz.aqjianzhi.user_service.entity.vo.LoginVo;
import com.jz.aqjianzhi.user_service.entity.vo.QueryUserInfoByTokenVo;
import com.jz.aqjianzhi.user_service.entity.vo.RegisterVo;
import com.jz.aqjianzhi.user_service.entity.vo.UserQuery;
import com.jz.aqjianzhi.user_service.service.impl.AqUserServiceImpl;
import com.jz.aqjianzhi.utils.JwtUtils;
import com.jz.aqjianzhi.utils.MD5;
import com.jz.aqjianzhi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xyk
 * @since 2021-03-28
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class AqUserController {

    @Autowired
    private AqUserServiceImpl userService;

    /**
     * 查询所有用户
     */
    @ApiOperation("查询所有用户")
    @GetMapping("/findAllUser")
    public R findAllUser() {
        List<AqUser> userList = userService.list(null);
        return R.ok().data("userList", userList);
    }

    /**
     * 根据id查询用户
     */
    @ApiOperation("根据id查询用户")
    @GetMapping("/findUserById/{id}")
    public R findUserById(@ApiParam(name = "id", value = "用户id", required = true) @PathVariable Long id) {
        AqUser user = userService.getById(id);
        return R.ok().data("user", user);
    }

    /**
     * 添加一条数据
     */
    @PostMapping("/addUser")
    public R addUser(@RequestBody AqUser user) {
        user.setUPassword(MD5.encrypt(user.getUPassword()));
        boolean flag = userService.save(user);
        return flag ? R.ok() : R.error();
    }

    /**
     * 修改一条数据
     */
    @PutMapping("/updateUser")
    public R updateUser(@RequestBody AqUser user) {
        boolean flag = userService.updateById(user);
        return flag ? R.ok() : R.error();
    }

    /**
     * 逻辑删除
     */
    @DeleteMapping("/delete/{id}")
    public R removeUser(@PathVariable Long id) {
        boolean flag = userService.removeById(id);
        return flag ? R.ok() : R.error();
    }

    /**
     * 分页查询
     */
    @GetMapping("/pageUser/{current}/{limit}")
    public R pageListUser(@PathVariable Long current,
                          @PathVariable Long limit) {
        // 1.创建page对象
        Page<AqUser> userPage = new Page<>(current, limit);
        // 2.调用方法进行分页
        userService.page(userPage, null);
        // 3.得到总记录数
        long total = userPage.getTotal();
        // 4.数据集合
        List<AqUser> records = userPage.getRecords();
        //        return R.ok().data("total", total).data("records", records);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("records", records);
        return R.ok().data(map);
    }

    /**
     * VO条件分页查询
     */
    @PostMapping("/pageUserCondition/{current}/{limit}")
    public R pageUserCondition(@PathVariable Long current,
                               @PathVariable Long limit,
                               @RequestBody(required = false) UserQuery userQuery) {
        // 创建page对象
        Page<AqUser> userPage = new Page<>(current, limit);
        // 构建条件
        QueryWrapper<AqUser> wrapper = new QueryWrapper<>();
        String uName = userQuery.getUName();
        int gender = userQuery.getGender();
        String begin = userQuery.getBegin();
        String end = userQuery.getEnd();
        if (StringUtils.hasLength(uName)) {
            wrapper.like("u_name", uName);
        }
        if (gender != 0) {
            wrapper.eq("gender", gender);
        }
        if (StringUtils.hasLength(begin)) {
            wrapper.ge("create_time", begin);  // 表中字段名称
        }
        if (StringUtils.hasLength(end)) {
            wrapper.le("update_time", end);
        }
        // 调用方法进行分页
        userService.page(userPage, wrapper);

        long total = userPage.getTotal();
        List<AqUser> records = userPage.getRecords();
        return R.ok().data("total", total).data("records", records);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVo userVo) {
        System.out.println(userVo);
        // 返回token值
        String token = userService.login(userVo);
        return R.ok().data("token", token);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public  R registerUser(@RequestBody RegisterVo registerVo) {
        userService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        // 根据request对象获取头信息，从token中获取用户id
        String userID = JwtUtils.getMemberIdByJwtToken(request);
        QueryUserInfoByTokenVo userInfo = userService.queryUserInfoByToken(Long.valueOf(userID));
        return R.ok().data("userInfo", userInfo);
    }
}

