package com.ssafy.happyhouse.controller;

import com.ssafy.happyhouse.model.MemberDto;
import com.ssafy.happyhouse.model.service.JwtServiceImpl;
import com.ssafy.happyhouse.model.service.MemberService;
import io.swagger.annotations.ApiParam;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//회원 처리용 controller
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;
	
	@Autowired
	private MemberService memberService;

	// 사용중
	@GetMapping("/idcheck")
	public @ResponseBody String idCheck(@RequestParam("ckid") String checkId) throws Exception {
		int idCount = memberService.idCheck(checkId);
		JSONObject json = new JSONObject();
		json.put("idcount", idCount);
		return json.toString();
	}
	
 	public ResponseEntity register(@RequestBody MemberDto memberDto) throws Exception {
		System.out.println(memberDto);
		memberService.registerMember(memberDto);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) Map<String, String> map) {
		MemberDto memberDto = new MemberDto();
		//map.forEach((k,v)->System.out.println(k + "," + v));
		memberDto.setUserId(map.get("userid"));
		memberDto.setUserPwd(map.get("userpwd"));
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
//			System.out.println(memberDto);
			MemberDto loginUser = memberService.login(memberDto);
//			System.out.println(loginUser);
			if (loginUser != null) {
				String token = jwtService.create("userid", loginUser.getUserId(), "access-token");// key, data, subject
				logger.debug("로그인 토큰정보 : {}", token);
				resultMap.put("access-token", token);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtService.isUsable(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@DeleteMapping(value = "/{userid}")
	public ResponseEntity<String> userDelete(@PathVariable("userid") String userid, HttpSession session) throws Exception {
		boolean flag = memberService.deleteMember(userid);
		if(flag) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		else
			return new ResponseEntity<String>(FAIL, HttpStatus.OK);

	}
	
//	@GetMapping("/register")
//	public String register() {
//		return "user/join";
//	}
	
//	@GetMapping("/login")
//	public String login() {
//		return "user/login";
//	}
//
//	@GetMapping("/loginmem")
//	public ResponseEntity<MemberDto> login(@RequestParam Map<String, String> map, HttpSession session,
//			HttpServletResponse response) throws Exception {
//		MemberDto memberDto = memberService.login(map);
//		if (memberDto != null) {
//			session.setAttribute("userinfo", memberDto);
//			Cookie cookie = new Cookie("ssafy_id", map.get("userId"));
//			cookie.setPath("/");
//			if ("saveok".equals(map.get("idsave"))) {
//				cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
//			} else {
//				cookie.setMaxAge(0);
//			}
//			response.addCookie(cookie);
//			return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<MemberDto>(memberDto, HttpStatus.NOT_FOUND);
//		}
//	}

//	@GetMapping("/lookup")
//	public String lookup(HttpSession session) throws Exception {
//		return "user/lookup";
//	}
//
	@PutMapping("/modify")
	public ResponseEntity<Map<String, Object>> modify(@RequestBody MemberDto memberDto, HttpSession session) throws Exception {
		System.out.println(memberDto);
		boolean flag=memberService.updateMember(memberDto);
		//session.setAttribute("userinfo", memberDto);
		Map<String, Object> resultMap = new HashMap<>();
		
		if(flag) {
			memberDto=memberService.userInfo(memberDto.getUserId());
			resultMap.put("userInfo", memberDto);
			resultMap.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
		else {
			resultMap.put("userInfo", null);
			resultMap.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		}
			
	}
//
//	@GetMapping("/modify")
//	public String modify(HttpSession session) throws Exception {
//		return "user/modify";
//	}
//
//	@GetMapping("/logout")
//	@ResponseBody
//	public ResponseEntity<MemberDto> logout(HttpSession session) {
//		MemberDto memberDto = new MemberDto();
//		session.invalidate();
//		return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
//	}
//
//	@GetMapping("/list")
//	public String list() {
//		return "user/list";
//	}
//
//	
//	@GetMapping("/interest")
//	ResponseEntity<MemberDto> getInterest(String userId, HttpSession session) throws Exception {
////		System.out.println(userId);
//		return new ResponseEntity<MemberDto>(memberService.getMemberInterest(userId), HttpStatus.OK);
//	}
	
	
	
	
}
