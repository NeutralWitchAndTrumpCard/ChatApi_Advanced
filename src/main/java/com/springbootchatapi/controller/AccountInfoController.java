package com.springbootchatapi.controller;

import com.springbootchatapi.account.AccountInformation;
import com.springbootchatapi.member.Member;
import com.springbootchatapi.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AccountInfoController {

    private final Logger logger = LoggerFactory.getLogger(AccountInfoController.class);

    @RequestMapping("/api/accountInfo")
    public AccountInformation getAccountInfo(HttpServletRequest request) {
        logger.info("Entering getAccountInfo method.");
        logger.info("Exiting getAccountInfo method.");

        // 다음 논리를 실제 데이터베이스 액세스 논리로 대체할 수 있습니다.
        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("memberId");  // 세션으로부터 memberID를 가져옴

        // memberId를 사용하여 데이터베이스에서 회원 정보를 가져옵니다.
        MemberRepository memberRepository = null;
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();


            return new AccountInformation(member.getId(), member.getName(), member.getEmail());


        } else {
            // 회원 정보가 없는 경우, null 또는 예외 처리
            return null;
        }


    }


    public static class AccountInfo {
    }

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/getAccountInfo")
    public Member getAccountInfo(HttpSession session) {
        // String sessionId = session.getId();

        String userId = (String) session.getAttribute("userId");
        System.out.println("UserID from session: " + userId);                   // 로그를 통해 세션에서 가져온 ID 확인
        return memberRepository.findById(userId).orElse(null);  // ID를 기반으로 회원 정보 조회
        // 로그 구현을 위한 코드 추가
        
        // sessionId를 기반으로 회원의 ID를 가져오는 로직
        // 이 부분은 세션을 어떻게 관리하고 있는지에 따라 다릅니다.
        // String memberId = "sessionId를_기반으로_memberId를_가져오는_로직";

        // Member member = memberRepository.findById(memberId).orElse(null);
        // return member;
    }


}

