package com.ssapick.server.domain.location.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.ssapick.server.core.configuration.SecurityConfig;
import com.ssapick.server.core.filter.JWTFilter;
import com.ssapick.server.core.support.RestDocsSupport;
import com.ssapick.server.domain.auth.controller.AuthController;
import com.ssapick.server.domain.auth.service.AuthService;
import com.ssapick.server.domain.location.dto.LocationData;
import com.ssapick.server.domain.location.service.LocationService;
import com.ssapick.server.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.stream.Stream;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("위치 컨트롤러 테스트")
@WebMvcTest(
        value = LocationController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class),
        }
)
class LocationControllerTest extends RestDocsSupport {
    @MockBean
    private LocationService locationService;

    @Test
    @DisplayName("사용자_위치_조회_확인_테스트")
    @WithMockUser(username = "test-user")
    void 사용자_위치_조회_확인_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser("test-user");
        List<LocationData.GeoLocation> locations = Stream.of(1, 2, 3).map((index) -> {
            LocationData.GeoLocation geoLocation = new LocationData.GeoLocation();
            geoLocation.setUsername("유저 이름 " + index);
            geoLocation.setProfileImage("프로필 이미지");
            geoLocation.setAlarm(true);
            geoLocation.setPosition(LocationData.Position.of(1, 2));
            geoLocation.setDistance(3);
            return geoLocation;
        }).toList();
        LocationData.Response response = new LocationData.Response(1, locations);

        when(locationService.findFriends(
                argThat(u -> u.getUsername().equals(user.getUsername())))
        ).thenReturn(response);

        // * WHEN: 이걸 실행하면
        ResultActions actions = this.mockMvc.perform(get("/api/v1/location"));

        // * THEN: 이런 결과가 나와야 한다
        actions.andExpect(status().isOk())
                .andDo(this.restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("위치 정보")
                                .summary("사용자 주위 위치 조회 API")
                                .description("현재 접속한 사용자 주위에 존재하는 사람들의 위치 정보들을 반환해준다.")
                                .responseFields(response(
                                        fieldWithPath("data.count").type(SimpleType.NUMBER).description("사용자가 선택 가능한 사용자 개수"),
                                        fieldWithPath("data.locations[].username").type(SimpleType.STRING).description("사용자 이름"),
                                        fieldWithPath("data.locations[].profileImage").type(SimpleType.STRING).description("프로필 이미지"),
                                        fieldWithPath("data.locations[].alarm").type(SimpleType.BOOLEAN).description("알람 여부"),
                                        fieldWithPath("data.locations[].position.x").type(SimpleType.NUMBER).description("위도"),
                                        fieldWithPath("data.locations[].position.y").type(SimpleType.NUMBER).description("경도"),
                                        fieldWithPath("data.locations[].distance").type(SimpleType.NUMBER).description("거리")
                                ))
                                .build()
                )));
    }

    @Test
    @DisplayName("사용자_특정한_유저_선택_테스트")
    @WithMockUser(username = "test-user")
    void 사용자_특정한_유저_선택_테스트() throws Exception {
        // * GIVEN: 이런게 주어졌을 때
        User user = this.createUser("test-user");
        LocationData.PickRequest pickRequest = new LocationData.PickRequest();
        pickRequest.setUsername("new user");

        // * WHEN: 이걸 실행하면
        ResultActions actions = this.mockMvc.perform(post("/api/v1/location")
                .contentType("application/json")
                .content(this.toJson(pickRequest))
        );

        // * THEN: 이런 결과가 나와야 한다
        actions.andExpect(status().isOk()).andDo(this.restDocs.document(resource(
                        ResourceSnippetParameters.builder()
                                .tag("위치 정보")
                                .summary("사용자 위치 선택 API")
                                .description("사용자가 다른 사용자를 선택했을 때 호출되는 API")
                                .requestFields(
                                        fieldWithPath("username").type(SimpleType.STRING).description("선택한 사용자 이름")
                                )
                                .responseFields(empty())
                                .build()
                )));
    }
}