//package com.shudailaoshi.web.quartz;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.shudailaoshi.service.eb.BespeakService;
//
//@Service("businessQuartz")
//public class BusinessQuartz {
//
//	@Autowired
//	private BespeakService bespeakService;
//	
//	/**
//	 * 日常任务： 过滤未支付的服务订单
//	 * 执行时间：见 spring-quartz.xml 
//	 * 
//	 */
//	public void bespeakUnpaidRollBack() {
//		
//		bespeakService.updateBespeakUnpaidRollBack();
//	}
//}
