package com.example.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.security.domain.SysUser;
import com.example.security.mapper.SysUserMapper;
import com.example.security.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

}
