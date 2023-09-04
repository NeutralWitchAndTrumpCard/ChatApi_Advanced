package com.springbootchatapi.controller;

import com.springbootchatapi.member.Member;
import com.springbootchatapi.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


// 비밀번호 해시화

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController

// register.html에서  url을 http://localhost:8080/register 로 변경
// 혹은 RegisterController.java에서 @RequestMapping("/api") 삭제

//@RequestMapping("/api")

public class RegistrationController {
    private final MemberRepository memberRepository;

    @Autowired
    public RegistrationController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> user) {
        Map<String, Object> response = new HashMap<>();
        String id = user.get("id");
        String name = user.get("name");
        String password = user.get("password");
        String email = user.get("email");


        //  ID와 이메일이 이미 사용중인지 확인합니다. (중복 검사)

        boolean existsById = memberRepository.existsById(id);
        boolean existsByName = memberRepository.existsByName(name);
        boolean existsByEmail = memberRepository.existsByEmail(email);

        if (existsById) {
            throw new IllegalArgumentException("이미 존재하는 ID입니다.");
        }

        if (existsByName) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        if (existsByEmail) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }


        // 이 부분에서 실제로 회원가입 로직을 수행합니다.
        Member member = new Member();
        member.setId(id);
        member.setName(name);
        member.setPasswordHash(password); // 실제로는 비밀번호를 해시화해야 합니다.
        member.setEmail(email);
        memberRepository.save(member); // 회원 정보를 데이터베이스에 저장합니다.
        member.setCreatedAt(LocalDateTime.now());   // 회원가입 시간 적용

        // 회원가입이 성공했다고 가정하고 응답을 만듭니다.
        response.put("success", true);
        response.put("message", "회원가입이 완료되었습니다.");

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