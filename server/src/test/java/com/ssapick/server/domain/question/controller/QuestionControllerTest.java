package com.ssapick.server.domain.question.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.entity.Question;
import com.ssapick.server.domain.question.entity.QuestionCategory;
import com.ssapick.server.domain.question.service.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("질문 컨트롤러 테스트")
@WebMvcTest(value = QuestionController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
class QuestionControllerTest extends RestDocsSupport {
    @MockBean
    private QuestionService questionService;

    @Test
    @DisplayName("모든 질문 조회 테스트")
    @WithMockUser(username = "test-user")
    void 모든_질문_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        List<QuestionData.Search> searches = Stream.of("질문 1", "질문 2", "질문 3")
                .map((content) -> {
                    Question question = spy(createQuestion(content));
                    when(question.getId()).thenReturn(1L);
                    return question;
                })
                .map(QuestionData.Search::fromEntity)
                .toList();
        when(questionService.searchQuestions()).thenReturn(searches);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/questions"));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("question")
                                .summary("모든 질문 조회 API")
                                .description("모든 질문을 조회한다.")
                                .responseFields(response(
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("질문 ID"),
                                        fieldWithPath("data[].banCount").description("질문을 차단한 횟수"),
                                        fieldWithPath("data[].skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
                                        fieldWithPath("data[].category.id").type(JsonFieldType.NUMBER).description("질문 카테고리 ID"),
                                        fieldWithPath("data[].category.name").type(JsonFieldType.STRING).description("질문 카테고리명"),
                                        fieldWithPath("data[].category.thumbnail").type(JsonFieldType.STRING).description("질문 카테고리 썸네일"),
                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("질문 내용")
                                ))
                                .build())
                ));
        verify(questionService).searchQuestions();
    }

    @Test
    @DisplayName("질문 ID 조회 테스트")
    void 질문_ID_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        Question question = spy(createQuestion("질문 1"));
        when(question.getId()).thenReturn(1L);
        QuestionData.Search search = QuestionData.Search.fromEntity(question);
        when(questionService.searchQuestionByQuestionId(question.getId()))
                .thenReturn(search);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(
                get("/api/v1/questions/{questionId}", question.getId())
        );

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("question")
                                .summary("질문 ID로 질문 조회 API")
                                .description("질문 ID로 질문을 조회한다.")
                                .pathParameters(parameterWithName("questionId").type(SimpleType.NUMBER).description("질문 ID"))
                                .responseFields(response(
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("질문 ID"),
                                        fieldWithPath("data.banCount").description("질문을 차단한 횟수"),
                                        fieldWithPath("data.skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
                                        fieldWithPath("data.category.id").type(JsonFieldType.NUMBER).description("질문 카테고리 ID"),
                                        fieldWithPath("data.category.name").type(JsonFieldType.STRING).description("질문 카테고리명"),
                                        fieldWithPath("data.category.thumbnail").type(JsonFieldType.STRING).description("질문 카테고리 썸네일"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("질문 내용")
                                ))
                                .build())
                ));

        verify(questionService).searchQuestionByQuestionId(question.getId());
    }

    @Test
    @DisplayName("카테고리별 질문 조회 테스트")
    void 카테고리별_질문_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        long categoryId = 1L;
        List<QuestionData.Search> searches = Stream.of("질문 1", "질문 2", "질문 3")
                .map((content) -> {
                    Question question = spy(createQuestion(content, categoryId));
                    when(question.getId()).thenReturn(1L);
                    return question;
                })
                .map(QuestionData.Search::fromEntity)
                .toList();
        when(questionService.searchQuestionsByCategory(categoryId)).thenReturn(searches);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(
                get("/api/v1/questions/category/{categoryId}", categoryId)
        );

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(this.restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("question")
                                .summary("카테고리별 질문 조회 API")
                                .description("카테고리 ID를 기준으로 카테고리에 속한 질문들을 조회한다.")
                                .pathParameters(parameterWithName("categoryId").type(SimpleType.NUMBER).description("질문 ID"))
                                .responseFields(response(
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("질문 ID"),
                                        fieldWithPath("data[].banCount").description("질문을 차단한 횟수"),
                                        fieldWithPath("data[].skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
                                        fieldWithPath("data[].category.id").type(JsonFieldType.NUMBER).description("질문 카테고리 ID"),
                                        fieldWithPath("data[].category.name").type(JsonFieldType.STRING).description("질문 카테고리명"),
                                        fieldWithPath("data[].category.thumbnail").type(JsonFieldType.STRING).description("질문 카테고리 썸네일"),
                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("질문 내용")
                                ))
                                .build()
                )));

        verify(questionService).searchQuestionsByCategory(categoryId);
    }

    @Test
    @DisplayName("질문 생성 성공 테스트")
    @WithMockUser(username = "test-user")
    void 질문_생성_성공_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        QuestionCategory category = createCategory();

        QuestionData.Create create = new QuestionData.Create();
        create.setCategoryId(category.getId());
        create.setContent("신규 질문 내용");

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(
                post("/api/v1/questions")
                        .contentType("application/json")
                        .content(toJson(create))
        );

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isCreated())
                .andDo(restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("question")
                                .summary("질문 생성 API")
                                .description("입력된 값을 기반으로 질문을 생성한다.")
                                .requestFields(
                                        fieldWithPath("categoryId").type(JsonFieldType.NUMBER).description("질문 카테고리 ID"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("질문 내용")
                                )
                                .responseFields(empty())
                                .build()
                )));
        verify(questionService).createQuestion(
                argThat(user -> user.getUsername().equals("test-user")),
                argThat(data -> Objects.equals(data.getCategoryId(), category.getId()) && data.getContent().equals("신규 질문 내용"))
        );
    }

    @Test
    @DisplayName("픽 하기 위한 사용자 맞춤형 질문 조회 테스트")
    @WithMockUser(username = "test-user")
    void 픽_하기_위한_사용자_맞춤형_질문_조회_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        List<QuestionData.Search> searches = Collections.nCopies(15, 0).stream().map((i) -> {
            Question question = spy(createQuestion("질문 " + (i + 1)));
            when(question.getId()).thenReturn((long) i + 1);
            return question;
        }).map(QuestionData.Search::fromEntity).toList();
        when(questionService.searchQeustionList(argThat(user -> user.getUsername().equals("test-user")))).thenReturn(searches);

        // * WHEN: 이걸 실행하면
        ResultActions perform = this.mockMvc.perform(get("/api/v1/questions/pick"));

        // * THEN: 이런 결과가 나와야 한다
        perform.andExpect(status().isOk())
                .andDo(this.restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("question")
                                .summary("싸픽 서비스에서 사용할 질문 조회 API")
                                .description("로그인된 사용자에게 맞는 질문(스킵하거나 차단한 질문 제외)을 조회한다.")
                                .responseFields(response(
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("질문 ID"),
                                        fieldWithPath("data[].banCount").description("질문을 차단한 횟수"),
                                        fieldWithPath("data[].skipCount").type(JsonFieldType.NUMBER).description("질문을 스킵한 횟수"),
                                        fieldWithPath("data[].category.id").type(JsonFieldType.NUMBER).description("질문 카테고리 ID"),
                                        fieldWithPath("data[].category.name").type(JsonFieldType.STRING).description("질문 카테고리명"),
                                        fieldWithPath("data[].category.thumbnail").type(JsonFieldType.STRING).description("질문 카테고리 썸네일"),
                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("질문 내용")
                                ))
                                .build()
                )));

        verify(questionService).searchQeustionList(argThat(user -> user.getUsername().equals("test-user")));
    }

    private Question createQuestion(String content) {
        QuestionCategory category = spy(QuestionCategory.create("TEST_CATEGORY", "http://thumbnail.com"));
        when(category.getId()).thenReturn(1L);
        return Question.createQuestion(category, content, createUser("author"));
    }

    private Question createQuestion(String content, long categoryId) {
        QuestionCategory category = spy(QuestionCategory.create("TEST_CATEGORY", "http://thumbnail.com"));
        when(category.getId()).thenReturn(categoryId);
        return Question.createQuestion(category, content, createUser("author"));
    }

    private QuestionCategory createCategory() {
        QuestionCategory category = spy(QuestionCategory.create("TEST_CATEGORY", "http://thumbnail.com"));
        when(category.getId()).thenReturn(1L);
        return category;
    }
}