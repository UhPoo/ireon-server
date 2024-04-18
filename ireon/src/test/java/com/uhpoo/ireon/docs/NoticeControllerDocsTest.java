package com.uhpoo.ireon.docs;

import com.uhpoo.ireon.api.PageResponse;
import com.uhpoo.ireon.api.controller.notice.NoticeController;
import com.uhpoo.ireon.api.controller.notice.request.CreateNoticeRequest;
import com.uhpoo.ireon.api.controller.notice.response.NoticeResponse;
import com.uhpoo.ireon.api.service.notice.NoticeQueryService;
import com.uhpoo.ireon.api.service.notice.NoticeService;
import com.uhpoo.ireon.api.service.notice.dto.CreateNoticeDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 공지사항 API Docs 테스트
 *
 * @author 최영환
 */
@WebMvcTest(NoticeControllerDocsTest.class)
public class NoticeControllerDocsTest extends RestDocsSupport {

    private final NoticeService noticeService = mock(NoticeService.class);
    private final NoticeQueryService noticeQueryService = mock(NoticeQueryService.class);

    @Override
    protected Object initController() {
        return new NoticeController(noticeService, noticeQueryService);
    }

    @DisplayName("공지사항 게시글 등록 API")
    @Test
    @WithMockUser
    void createNotice() throws Exception {
        CreateNoticeRequest request = CreateNoticeRequest.builder()
                .title("제목")
                .content("내용")
                .build();

        given(noticeService.createNotice(any(CreateNoticeDto.class), anyString()))
                .willReturn(1L);

        mockMvc.perform(
                        post("/notice")
                                .header("Authentication", "authentication")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("create-notice",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER)
                                        .description("등록된 공지사항 게시글 PK 값")
                        )
                ));
    }

    @DisplayName("게시글 전체 조회 API")
    @Test
    @WithMockUser
    void getAbandons() throws Exception {

        NoticeResponse item1 = NoticeResponse.builder()
                .noticeId(3L)
                .title("제목1")
                .author("작성자1")
                .createdDate("2024-03-05")
                .build();

        NoticeResponse item2 = NoticeResponse.builder()
                .noticeId(1L)
                .title("제목2")
                .author("작성자2")
                .createdDate("2024-03-04")
                .build();

        List<NoticeResponse> items = List.of(item1, item2);

        PageResponse<List<NoticeResponse>> response = PageResponse.of(false, items);

        given(noticeQueryService.getNotices(anyLong()))
                .willReturn(response);

        mockMvc.perform(
                        get("/notice")
                                .header("Authentication", "authentication")
                                .queryParam("lastNoticeId", "0")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-notices",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("lastNoticeId").description("마지막으로 조회된 공지사항 PK")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음 페이지 존재 여부"),
                                fieldWithPath("data.items").type(JsonFieldType.ARRAY)
                                        .description("게시글 목록"),
                                fieldWithPath("data.items[].noticeId").type(JsonFieldType.NUMBER)
                                        .description("공지사항 게시글 PK"),
                                fieldWithPath("data.items[].title").type(JsonFieldType.STRING)
                                        .description("글 제목"),
                                fieldWithPath("data.items[].author").type(JsonFieldType.STRING)
                                        .description("작성자"),
                                fieldWithPath("data.items[].createdDate").type(JsonFieldType.STRING)
                                        .description("작성일")
                        )
                ));

    }
}
