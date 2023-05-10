package com.demo.wms.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.wms.common.QueryPageParam;
import com.demo.wms.common.Result;
import com.demo.wms.entity.Menu;
import com.demo.wms.entity.User;
import com.demo.wms.service.MenuService;
import com.demo.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-03-29
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }
    //检查账号是否已经存在
    @GetMapping("/findByNo")
    public Result findByNo( @RequestParam String no){
        List list= userService.lambdaQuery().eq(User::getNo,no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody  User user) {

        return userService.save(user)?Result.suc():Result.fail();//判断是否保存保存的话返回suc,否则返回fail
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody User user){

        return userService.updateById(user)?Result.suc():Result.fail();
    }

    //登录
    //更新
    @PostMapping("/login")
    public Result login(@RequestBody User user){
         //让账号和密码匹配，
           List list=userService.lambdaQuery().eq(User::getNo,user.getNo())
                   .eq(User::getPassword,user.getPassword()).list();


        if(list.size()>0){
            User user1=(User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap res =new HashMap<>();
            res.put("user",user1);
            res.put("menu",menuList);
            return Result.suc(res);

        }
        return Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){

        return userService.removeById(id)?Result.suc():Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody  User user) {
        return userService.updateById(user);
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody  User user) {
        return userService.saveOrUpdate(user);
    }
    //删除
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }
    //查询(模糊、匹配)
    @PostMapping("/listP")
    public Result listP(@RequestBody  User user) { //这是原来的返回的是list类型,但要返回结果所以改用Result类的result类型 p17改
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(user.getName())){ //如果传进来的name字段为空则直接返回所以数据,否则通过like关键字匹配
            lambdaQueryWrapper.like(User::getName,user.getName());//like是模糊匹配关键字

        }
//        lambdaQueryWrapper.like(User::getName,user.getName());//like是模糊匹配关键字
        return Result.suc(userService.list(lambdaQueryWrapper));
    }


   //通过mybatis-plus内部的Ipage对象进行的分页查询
    @PostMapping("/listPage")
//    public List<User> listP(@RequestBody HashMap map ) {  //这是用hashmap的方式获取数据
    public List<User> listPage(@RequestBody QueryPageParam query ) { //这是通过自用定义的QueryPageParam 的方式进行调用

//        System.out.println(query);//打印获取到的东西
//
//       // System.out.println("num=="+(String)map.get("pageSize"));//如果用hashmao的方式获取的话是通过这样的方式


//        System.out.println("num==="+query.getPageNum());//打印获取到的页码
//        System.out.println("size==="+query.getPageSize());//打印当前页面大小
        HashMap param=query.getParam();
        String name=(String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
//        System.out.println("no==="+(String)param.get("no"));

        //这是通过mybatis-plus自带的page分页方法进行分页
        Page<User> page=new Page<>();
        page.setCurrent(query.getPageNum());//这是设置当前页维第几页
        page.setSize(query.getPageSize());//这是设置当前页的大小

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


        IPage result= userService.page(page,lambdaQueryWrapper);//返回模糊查询结果

        System.out.println("total==="+result.getTotal());//getTotal代表总记录数

        return result.getRecords();
    }

    //自定义的分页查询
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query ) { //这是通过自用定义的QueryPageParam 的方式进行调用
        HashMap param=query.getParam();
        String name=(String)param.get("name");
        System.out.println("name==="+(String)param.get("name"));
        System.out.println("sex==="+(String)param.get("sex"));


        Page<User> page=new Page<>();
        page.setCurrent(query.getPageNum());//这是设置当前页维第几页
        page.setSize(query.getPageSize());//这是设置当前页的大小

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName,name);


//        IPage result= userService.pageC(page);//返回模糊查询结果
        IPage result= userService.pageCC(page,lambdaQueryWrapper);//返回模糊查询结果

        System.out.println("total==="+result.getTotal());//getTotal代表总记录数

        return result.getRecords();
    }

    //测试封装返回结果代码
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query ) { //这是通过自用定义的QueryPageParam 的方式进行调用
        HashMap param=query.getParam();
        String name=(String)param.get("name");
        String sex=(String)param.get("sex");
        String roleId=(String)param.get("roleId");

        System.out.println("name==="+(String)param.get("name"));

        Page<User> page=new Page<>();
        page.setCurrent(query.getPageNum());//这是设置当前页维第几页
        page.setSize(query.getPageSize());//这是设置当前页的大小

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.like(User::getName,name);
        }

        if(StringUtils.isNotBlank(sex)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.eq(User::getSex,sex);
        }

        if(StringUtils.isNotBlank(roleId)){ //如果传过来的数据不等null 才返回结果
            lambdaQueryWrapper.eq(User::getRoleId,roleId);
        }


//        IPage result= userService.pageC(page);//返回模糊查询结果
        IPage result= userService.pageCC(page,lambdaQueryWrapper);//返回模糊查询结果

        System.out.println("total==="+result.getTotal());//getTotal代表总记录数

        return Result.success(result.getRecords(),result.getTotal());
    }
}
