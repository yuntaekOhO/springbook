package com.springbook.view.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springbook.biz.user.UserVO;
import com.springbook.biz.user.impl.UserDAO;
@Controller
public class LoginController {
	//@RequestMapping(value="", method='') get,post 클라이언트 요청 적절히 처리하고자 할 때 사용
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginView(UserVO vo) { // Get 방식 요청일 때 실행 (직접 url 입력 or index.jsp 하이퍼링크 클릭)
		System.out.println("로그인 화면으로 이동");
		vo.setId("test");
		vo.setPassword("test123"); //입력상자에 id, pw 자동으로 설정되있음
		return "login.jsp";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(UserVO vo, UserDAO userDAO, HttpSession session) { // form의 submit 누른 경우 Post 방식 요청
		System.out.println("로그인 인증 처리...");
		UserVO user = userDAO.getUser(vo);
		if(userDAO.getUser(vo) != null) {
			session.setAttribute("userName", user.getName()); //사용자 이름 session에 저장 getBoardList.jsp에 이름 표시위해
			return "getBoardList.do";
		}
		else return"login.jsp";
	}

}
