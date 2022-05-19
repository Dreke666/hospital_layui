package com.zhang.hospital.dao;

import com.zhang.hospital.entity.Admin;
import com.zhang.hospital.entity.AdminLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface AdminDao {
    Admin login(@Param("username") String username, @Param("password") String password);

    void insAdminLog(@Param("adminUsername") String username, @Param("loginIp") String loginIp,
                     @Param("loginTime") Date loginTime, @Param("logoutTime") Date logoutTime,
                     @Param("isSafeExit") Integer isSafeExit);

    Admin getAdminById(int id);

    Admin getAdminByUsername(String username);

    List<Admin> getAdminsList();

    int updAdmin(Admin admin);

    void delAdminById(Long id);

    void insAdmin(Admin admin);

    AdminLog getAdminLogByUsername(String username);

    void updateAdminLog(AdminLog adminLog);

    AdminLog getAdminLogByLoginTime(Date loginTime);

    List<AdminLog> getAdminLogsList();
}
