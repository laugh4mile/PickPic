package com.web.blog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.model.MemberDto;
import com.web.blog.model.service.ETCService;
import com.web.blog.model.service.JwtService;
import com.web.blog.model.service.MemberService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = { "*" })
public class LoginController {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ETCService etcService;

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@PostMapping("/confirm/login")
	public ResponseEntity<Map<String, Object>> login(@RequestParam String email, @RequestParam String pwd,
			HttpServletResponse response, HttpSession session) {
		MemberDto dto = new MemberDto();
		dto.setEmail(email);
		dto.setPwd(pwd);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.login(dto);

			if (loginUser != null) {
//				jwt.io에서 확인
//				로그인 성공했다면 토큰을 생성한다.
				String token = jwtService.create(loginUser);
				logger.trace("로그인 토큰정보 : {}", token);

//				토큰 정보는 response의 헤더로 보내고 나머지는 Map에 담는다.
//				response.setHeader("auth-token", token);
				resultMap.put("auth-token", token);
				resultMap.put("user-email", loginUser.getEmail());
				resultMap.put("user-name", loginUser.getName());
				resultMap.put("role", loginUser.getRole());
				System.out.println(resultMap);
//				resultMap.put("status", true);
//				resultMap.put("data", loginUser);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", "로그인 실패");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@GetMapping("/findpwd")
	public ResponseEntity<Map<String, Object>> findPwd(@RequestParam String email) {
		System.out.println("전달 받은 이메일 : " + email);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		try {
			etcService.findPwd(email);

			System.out.println(resultMap);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(status);
//		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
