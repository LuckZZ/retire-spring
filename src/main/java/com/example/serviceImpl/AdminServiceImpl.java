package com.example.serviceImpl;

import com.example.comm.Constant;
import com.example.dao.AdminDao;
import com.example.domain.entity.Admin;
import com.example.domain.enums.CanLogin;
import com.example.domain.enums.Verify;
import com.example.service.AdminService;
import com.example.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

/**
 * Create by : Zhangxuemeng
 * csdn：https://blog.csdn.net/Luck_ZZ
 */
@Service
public class AdminServiceImpl extends BaseCrudServiceImpl<Admin,Integer,AdminDao> implements AdminService{

    @Autowired
    private AdminDao adminDao;

    @Value("${file.pictures.url}")
    private String filePicturesUrl;

    @Transactional
    @Override
    public int updateImg(String imgUrl, Integer adminId) {
        return adminDao.updateImg(imgUrl, adminId);
    }

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
        List<Integer> list = Arrays.asList(adminIds);
//      取出第一个为CanLogin.yes的对象
        CanLogin canLogin = list.stream().map(i -> adminDao.findOne(i).getCanLogin()).
                filter( c -> c.equals(CanLogin.yes)).
                findFirst().orElse(null);
        if (canLogin == null){
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void delete(Integer[] adminIds) {
        List<Integer> list = Arrays.asList(adminIds);
//        删除图片
        list.forEach(adminId -> {
            Admin admin = adminDao.getOne(adminId);
            FileUtils.deleteFile(filePicturesUrl+admin.getImgUrl());
        });
//        删除管理员
        list.forEach(adminId -> adminDao.delete(adminId));
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
    public List<Admin> findAllByJobNum(String jobNum) {
        return adminDao.findAllByJobNum(jobNum);
    }

    @Override
    public Page<Admin> findAllByNameContaining(String name, Integer page) {
        Pageable pageable = new PageRequest(page, Constant.PAGESIZE);
        return adminDao.findAllByNameContaining(name, pageable);
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

    @Transactional
    @Override
    public int updateEmail(String email, Verify verify, Integer adminId) {
        return adminDao.updateEmail(email, verify, adminId);
    }

    @Transactional
    @Override
    public int updateVerifyCode(String verifyCode, String verifyTime, Integer adminId) {
        return adminDao.updateVerifyCode(verifyCode, verifyTime, adminId);
    }

    @Transactional
    @Override
    public int updateVerify(Verify verify, Integer adminId) {
        return adminDao.updateVerify(verify, adminId);
    }

}
