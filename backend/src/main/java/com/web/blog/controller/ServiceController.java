package com.web.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.blog.model.BasicResponse;
import com.web.blog.model.service.ETCService;
import com.web.blog.model.service.ETCServiceImpl;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
@Controller
@RequestMapping("/service/*")
@CrossOrigin(origins = "*")
public class ServiceController {
	@Autowired
	ETCService service;

//	 private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@PostMapping("/mail")
	@ResponseBody
	public void emailConfirm(@RequestParam String email) throws Exception {
//			logger.info("post emailConfirm");
		System.out.println("전달 받은 이메일 : " + email);
		service.sendSimpleMessage(email);
	}

	@PostMapping("/verifyCode")
	@ResponseBody
	public int verifyCode(@RequestParam String code) {
//			logger.info("Post verifyCode");
		int result = 0;
		System.out.println("code : " + code);
		System.out.println("code match : " + ETCServiceImpl.ePw.equals(code));
		if (ETCServiceImpl.ePw.equals(code)) {
			result = 1;
		}

		return result;
	}
}