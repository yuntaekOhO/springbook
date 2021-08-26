package com.springbook.view.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springbook.biz.user.UserVO;
import com.springbook.biz.user.impl.UserDAO;
@Controller
public class LoginController {
	//@RequestMapping(value="", method='') get,post Ŭ���̾�Ʈ ��û ������ ó���ϰ��� �� �� ���
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginView(UserVO vo) { // Get ��� ��û�� �� ���� (���� url �Է� or index.jsp �����۸�ũ Ŭ��)
		System.out.println("�α��� ȭ������ �̵�");
		vo.setId("test");
		vo.setPassword("test123"); //�Է»��ڿ� id, pw �ڵ����� ����������
		return "login.jsp";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(UserVO vo, UserDAO userDAO, HttpSession session) { // form�� submit ���� ��� Post ��� ��û
		System.out.println("�α��� ���� ó��...");
		UserVO user = userDAO.getUser(vo);
		if(userDAO.getUser(vo) != null) {
			session.setAttribute("userName", user.getName()); //����� �̸� session�� ���� getBoardList.jsp�� �̸� ǥ������
			return "getBoardList.do";
		}
		else return"login.jsp";
	}

}
