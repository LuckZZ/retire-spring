package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.AdminDao;
import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AdminServiceImpl extends BaseCrudServiceImpl<Admin,Integer,AdminDao> implements AdminService{

    @Autowired
    private AdminDao adminDao;

    @Override
    public List<Admin> findAllByJobNumAndPassword(String jobNum, String password) {
        return adminDao.findAllByJobNumAndPassword(jobNum, password);
    }

    public  boolean existsByJobNum(String jobNum){
        return adminDao.existsByJobNum(jobNum);
    }

    @Transactional
    @Override
    public int updateNameAndCanLogin(String name, CanLogin canLogin, Integer adminId) {
        return adminDao.updateNameAndCanLogin(name,canLogin,adminId);
    }

    @Transactional
    @Override
    public int updatePassword(String password, Integer adminId) {
        return adminDao.updatePassword(password,adminId);
    }

    @Override
    public boolean canDelete(Integer[] adminIds) {
        for (int i = 0; i < adminIds.length; i ++){
            Admin admin = adminDao.findOne(adminIds[i]);
            if (admin.getCanLogin() == CanLogin.yes)
                return false;
        }
        return true;
    }

    @Transactional
    @Override
    public void delete(Integer[] adminIds) {
        for (int i = 0; i < adminIds.length; i ++){
            adminDao.delete(adminIds[i]);
        }
    }

    @Transactional
    @Override
    public boolean notCanLogin(Integer adminId) {
        Admin admin = adminDao.findOne(adminId);
        CanLogin oldCanLogin = admin.getCanLogin();
        if (oldCanLogin == CanLogin.no){
            adminDao.updateCanLogin(CanLogin.yes,adminId);
        }else if(oldCanLogin == CanLogin.yes){
            adminDao.updateCanLogin(CanLogin.no,adminId);
        }
        return true;
    }

    @Override
    public Page<Admin> findAllByJobNum(String jobNum, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return adminDao.findAllByJobNum(jobNum, pageable);
    }

    @Override
    public Page<Admin> findAllByName(String name, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return adminDao.findAllByName(name, pageable);
    }

    @Transactional
    @Override
    public int updateLastTime(String lastTime, Integer adminId) {
        return adminDao.updateLastTime(lastTime, adminId);
    }

    @Transactional
    @Override
    public Admin save(Admin admin) {
        //        设置canLogin的值
        if(admin.getCanLogin() == null){
            admin.setCanLogin(CanLogin.no);
        }
        return adminDao.save(admin);
    }
}
