package com.springbook.biz.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;
@Service
@Aspect // Aspect = Pointcut + Advice
public class LogAdvice {
	
	@Before("PointcutCommon.allPointcut()")
	public void printLog(JoinPoint jp) {
		System.out.println("[���� �α�] ����Ͻ� ���� ���� �� ����");
	}
}
