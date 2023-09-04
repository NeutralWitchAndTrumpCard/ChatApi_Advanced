package com.springbootchatapi.controller;

import com.springbootchatapi.member.Member;
import com.springbootchatapi.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession; // HttpSession import

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController

// register.html에서  url을 http://localhost:8080/register 로 변경
// 혹은 RegisterController.java에서 @RequestMapping("/api") 삭제

//@RequestMapping("/api")

public class LoginController {
    private final MemberRepository memberRepository;

    @Autowired
    private MemberRepository repository;    // 추가사항
    private HttpSession httpSession;  // Inject HttpSession

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);       // 추가사항

    public LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> user, HttpSession httpSession) {
        Map<String, Object> response = new HashMap<>();
        String id = user.get("id");
        String password = user.get("password");
        Optional<Member> optionalMember = repository.findById(id);

        if (!optionalMember.isPresent()) {
            response.put("success", false);
            response.put("message", "ID 혹은 비밀번호가 일치하지 않습니다.");
            logger.info("ID 혹은 비밀번호가 틀렸습니다. (Log.ID.Error) ");  // Log ID error
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Member member = optionalMember.get();

        if (!password.equals(member.getPassword())) { // Modified line
            response.put("success", false);
            response.put("message", "ID 혹은 비밀번호가 일치하지 않습니다.");
            logger.info("ID 혹은 비밀번호가 틀렸습니다. (Log.PW.Error) ");  // Log password error
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        // Login successful, save user info in session
        httpSession.setAttribute("user", member);
        logger.info("로그인 성공: 로그인에 성공하였습니다.");  // Log success

        response.put("success", true);
        response.put("message", "로그인에 성공하였습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "로그아웃 되었습니다.");
        return response;
    }


    @GetMapping("/checkLoginStatus")
    public Map<String, Object> checkLoginStatus(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member member = (Member) session.getAttribute("user");
        if (member != null) {
            // 로그인 상태
            response.put("loggedIn", true);
            response.put("name", member.getName());
        } else {
            // 비로그인 상태
            response.put("loggedIn", false);
        }
        return response;
    }


    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        public Map<String, String> handleIllegalArgumentException(IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return response;
        }
    }
}