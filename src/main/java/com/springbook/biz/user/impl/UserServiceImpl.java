package com.springbook.biz.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.biz.user.UserVO;
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired //UserDAO Ŭ������ @Repository ������̼����� ������ UserDAO��ü�� �Ʒ��� ���� userDAO�� �Ҵ�
	private UserDAO userDAO;
	
	//setter ������ ó���� ���� setter �޼ҵ�
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	@Override
	public UserVO getUser(UserVO vo) {
		return userDAO.getUser(vo);
	}

}
