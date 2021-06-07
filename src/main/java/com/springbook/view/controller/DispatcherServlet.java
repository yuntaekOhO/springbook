package com.springbook.view.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HandlerMapping handlerMapping;
    private ViewResolver viewResolver;
    
    public void init() throws ServletException {
    	handlerMapping = new HandlerMapping();
    	viewResolver = new ViewResolver();
    	viewResolver.setPrefix("./");
    	viewResolver.setSuffix(".jsp");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("EUC-KR");
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//1.클라이언트의 요청 path 정보를 추출한다.
		String uri = request.getRequestURI(); // 이때 추출된 path는 URI문자열에서 마지막 "/xxx.do" 문자열
		String path = uri.substring(uri.lastIndexOf("/")); // path 문자열에 따라서 분기처리 로직 실행
		
		//2.handlerMapping을 통해 path에 해당하는 Controller를 검색한다.
		Controller ctrl = handlerMapping.getController(path);
		
		//3.검색된 Controller를 실행한다.
		String viewName = ctrl.handleRequest(request, response);
		
		//4.ViewResolver를 통해 viewName에 해당하는 화면을 검색한다.
		String view = null;
		if(!viewName.contains(".do")) { //contains() = 문자열 포함 여부
			view = viewResolver.getView(viewName);
		} else {
			view = viewName;
		}
		
		//5.검색된 화면으로 이동한다.
		response.sendRedirect(view);
	}
}
