package com.uhpoo.ireon.api.controller.member;

import com.uhpoo.ireon.api.ApiResponse;
import com.uhpoo.ireon.api.controller.member.request.MemberLoginRequest;
import com.uhpoo.ireon.api.controller.member.request.MemberSignUpRequest;
import com.uhpoo.ireon.api.controller.member.request.MemberUpdateRequest;
import com.uhpoo.ireon.api.controller.member.response.MemberResponse;
import com.uhpoo.ireon.api.controller.member.response.MemberSignUpResponse;
import com.uhpoo.ireon.api.controller.member.response.TokenResponse;
import com.uhpoo.ireon.api.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberSignUpResponse> signup(@Valid @RequestBody MemberSignUpRequest request) {
        log.debug("MemberController#signup called.");
        log.debug("MemberSignUpRequest={}", request);

        MemberSignUpResponse response = memberService.signup(request.toDto());
        log.debug("MemberSignUpResponse={}",response);

        return ApiResponse.created(response);
    }

    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody MemberLoginRequest request) {
        log.debug("MemberController#login called.");
        log.debug("MemberLoginRequest={}", request);

        TokenResponse response = memberService.login(request.toDto());
        log.debug("TokenDto={}",response);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Object> logout(HttpServletRequest request) {
        log.debug("MemberController#logout called.");
        //String encryptedRefreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String encryptedRefreshToken = "encryptedRefreshToken";
        //String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String accessToken = "accessToken";

        memberService.logout(encryptedRefreshToken,accessToken); //TODO: 로그아웃 시 레디스 저장

        return ApiResponse.of(HttpStatus.NO_CONTENT,"Logged out successfully", null);
    }

    @GetMapping("/info")
    public ApiResponse<MemberResponse> getMemeber(@RequestParam("email") String email)  {
        log.debug("MemberController#getMemeber called.");
        // TODO: 로그인 사용자와 요청한 사용자 일치 여부 확인
        //String memberEmail = SecurityUtil.getCurrentMemberUsername();
        String memberEmail = "email";
        log.debug("memberEmail={}",memberEmail);

        MemberResponse response = memberService.getMember(memberEmail);
        log.debug("response={}",response);

        return ApiResponse.ok(response);
    }

    @PostMapping("info")
    public ApiResponse<MemberResponse> updateMember(@Valid @RequestBody MemberUpdateRequest request) {
        log.debug("MemberController#updateMember called.");
        log.debug("MemberUpdateRequest={}",request);
        // TODO: 로그인 사용자와 요청 사용자 일치 여부 확인

        MemberResponse response = memberService.updateMember(request.toDto());
        log.debug("response={}",response);

        return ApiResponse.ok(response);
    }


}
