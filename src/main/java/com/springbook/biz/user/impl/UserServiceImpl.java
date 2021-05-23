package com.springbook.biz.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.biz.user.UserVO;
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired //UserDAO 클래스에 @Repository 어노테이션으로 생성된 UserDAO객체에 아래의 변수 userDAO를 할당
	private UserDAO userDAO;
	
	//setter 인젝션 처리를 위한 setter 메소드
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	@Override
	public UserVO getUser(UserVO vo) {
		return userDAO.getUser(vo);
	}

}
