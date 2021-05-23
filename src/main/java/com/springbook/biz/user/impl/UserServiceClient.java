package com.springbook.biz.user.impl;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.springbook.biz.user.UserVO;

public class UserServiceClient {

	public static void main(String[] args) {
		//1. Spring 컨테이너를 구동한다.
		AbstractApplicationContext container = new GenericXmlApplicationContext("applicationContext.xml");
		
		//2. Spring 컨테이너로부터 UserServiceImpl 객체를 lookup 한다. getBean("?") -> UserServiceImpl클래스에 Service 어노테이션으로 등록된 id
		UserService userService = (UserService)container.getBean("userService");
		
		//3. 로그인 기능 테스트
		UserVO vo = new UserVO();
		vo.setId("test");
		vo.setPassword("test123");
		
		UserVO user = userService.getUser(vo);
		if(user != null) {
			System.out.println(user.getName() + "님 환영합니다.");
			System.out.println(vo.toString());
		} else {
			System.out.println("로그인 실패");
		}
		
		//4. Spring 컨테이너를 종료한다.
		container.close();
	}

}
