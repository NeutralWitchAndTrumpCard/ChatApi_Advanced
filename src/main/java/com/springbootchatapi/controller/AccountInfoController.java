package com.springbootchatapi.controller;


// import com.springbootchatapi.account.AccountInformation;
import com.springbootchatapi.member.Member;
import com.springbootchatapi.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class AccountInfoController {

    @GetMapping("/getAccountDataWhenLoggedIn")
    public Map<String, Object> getAccountDataWhenLoggedIn(HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Member member = (Member) session.getAttribute("user");
        if (member != null) {
            // 로그인 상태
            response.put("loggedIn", true);
            response.put("name", member.getName());
            response.put("id", member.getId());
            response.put("email", member.getEmail());
            response.put("createdAt", member.getCreatedAt());
        } else {
            // 비로그인 상태
            response.put("loggedIn", false);
        }
        return response;
    }

    @PostMapping("/logRedirect")
    public void logRedirect() {
        System.out.println("User is about to redirect to account_modify.html");
    }

}




/* Legacy code final date in 2023-09-07 14:07
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

    @GetMapping("/api/accountInfoFromLoginId")
    public Member getAccountInfo(HttpSession session) {
        // String sessionId = session.getId();

        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            logger.error("User ID not found in session.");
            return null;
        }

        System.out.println("UserID from session: " + userId);                   // 로그를 통해 세션에서 가져온 ID 확인
        return memberRepository.findById(userId).orElse(null);  // ID를 기반으로 회원 정보 조회
    }


}
*/