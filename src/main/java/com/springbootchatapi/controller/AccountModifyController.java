// 회원정보 수정 전 회원 비밀번호 확인 작업 필요
// 회원정보 수정 시 회원정보 수정 내용이 DB에 반영 필요

package com.springbootchatapi.controller;

import com.springbootchatapi.member.Member;
import com.springbootchatapi.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AccountModifyController {
    @Autowired
    private final MemberRepository memberRepository;

    public AccountModifyController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/getAccountDataWhenLoggedIn_AccountModify")
    public Map<String, Object> getAccountDataWhenLoggedIn_AccountModify(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member member = (Member) session.getAttribute("user");
        if (member != null) {
            // 로그인 상태
            response.put("loggedIn", true);
            response.put("name", member.getName());
            response.put("id", member.getId());
            response.put("email", member.getEmail());
        } else {
            // 비로그인 상태
            response.put("loggedIn", false);
        }
        return response;
    }

    // 중복 이름 확인 API
    @PostMapping("/checkDuplicateName")
    public Map<String, Object> checkDuplicateName(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String name = request.get("name");

        boolean existsByName = memberRepository.existsByName(name);

        response.put("duplicate", existsByName);

        return response;
    }

    // 중복 이메일 확인 API
    @PostMapping("/checkDuplicateEmail")
    public Map<String, Object> checkDuplicateEmail(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String email = request.get("email");

        boolean existsByEmail = memberRepository.existsByEmail(email);

        response.put("duplicate", existsByEmail);

        return response;
    }


    @PostMapping("/updateAccountInfo")
    public Map<String, Boolean> updateAccountInfo(@RequestBody Map<String, String> params) {
        System.out.println("updateAccountInfo called.");                            // IDEA 콘솔 로그
        System.out.println("Received params: " + params.toString());        //

        Map<String, Boolean> result = new HashMap<>();
        String id = params.get("id");
        String name = params.get("name");
        String email = params.get("email");

        Member existingMember = memberRepository.findById(id).orElse(null);

        if (existingMember != null) {
            // 중복 이름 체크
            Member foundMemberByName = memberRepository.findByName(name).orElse(null);
            if (foundMemberByName != null && !foundMemberByName.getId().equals(id)) {
                result.put("duplicate", true);
                result.put("success", false);
                System.out.println("Response: " + result.toString());
                return result;
            /*
            if (memberRepository.findByName(name) != null) {
                result.put("duplicate", true);
                result.put("success", false);
                System.out.println("Response: " + result.toString());
                return result;

             */
            }

            // 중복 이메일 체크
            Member foundMemberByEmail = memberRepository.findByEmail(email).orElse(null);
            if (foundMemberByEmail != null && !foundMemberByEmail.getId().equals(id)) {
                result.put("duplicate", true);
                result.put("success", false);
                System.out.println("Response: " + result.toString());
                return result;
            /*
            if (memberRepository.findByEmail(email) != null) {
                result.put("duplicate", true);
                result.put("success", false);
                System.out.println("Response: " + result.toString());
                return result;

             */
            }
            
            
            // 입력 필드가 비어있는 조건에 따라 한가지 데이터 유형만 수정 갱신
            if (!name.isEmpty() && email.isEmpty()) {
                existingMember.setName(name);
            } else if (name.isEmpty() && !email.isEmpty()) {
                existingMember.setEmail(email);
            } else {
                existingMember.setName(name);
                existingMember.setEmail(email);
            }
            memberRepository.save(existingMember);

            // updated_at 컬럼의 시간을 갱신 (JPA가 알아서 해줄 수도 있습니다)
            existingMember.setUpdatedAt(LocalDateTime.now());

            result.put("duplicate", false);
            result.put("success", true);
            System.out.println("Response: " + result.toString());
            return result;

        } else {
            result.put("success", false);
            System.out.println("Response: " + result.toString());
            return result;

        }

    }


}
