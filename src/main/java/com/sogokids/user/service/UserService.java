package com.sogokids.user.service;


import com.sogokids.user.model.Func;
import com.sogokids.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hoze on 15/8/25.
 */
public interface UserService {
    public User get(int id);
    public List<User> getEntityList(String where);
    public int insert(User entity);
    public int update(User entity);
    public int delete(int id);

    public User users_exist(String username,String password);
    public User formEntity(HttpServletRequest req,int id);

    public List<Func> getMenuFunc(int adminId);

    public String getMenus(List<Func> func_ls,String menu_code);
}
