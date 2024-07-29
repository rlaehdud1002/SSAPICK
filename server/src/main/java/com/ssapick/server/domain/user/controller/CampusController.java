package com.ssapick.server.domain.user.controller;

import com.ssapick.server.core.annotation.IsAdmin;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.user.dto.CampusData;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.service.CampusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/campus")
public class CampusController {
    private final CampusService campusService;

    /**
     * 캠퍼스에 해당된 반 정보들을 조회하는 API
     * @param campusId 조회할 캠퍼스 ID
     * @return {@link ProfileData.Search} 조회된 캠퍼스 반에 속한 사용자 리스트
     */
    @GetMapping(value = "/{campusId}")
    public SuccessResponse<List<ProfileData.Search>> getCampus(@PathVariable Long campusId) {
        return SuccessResponse.of(campusService.searchProfileByCampusId(campusId));
    }

    /**
     * 캠퍼스 이름을 기반으로 캠퍼스에 해당된 반 정보들을 조회하는 API
     * @param name 검색할 컴퍼스 이름
     * @return {@link CampusData.SearchResponse} 조회된 캠퍼스 반 리스트
     */
    @GetMapping(value = "/search/name")
    public SuccessResponse<List<CampusData.SearchResponse>> searchCampusByName(@RequestParam String name) {
        return SuccessResponse.of(campusService.searchCampusByName(name));
    }

    /**
     * 반 이름을 기반으로 캠퍼스에 해당된 반 정보들을 조회하는 API
     * @param description 검색할 캠퍼스 반의 특징
     * @return {@link CampusData.SearchResponse} 조회된 캠퍼스 반 리스트
     */
    @GetMapping(value = "/search/description")
    public SuccessResponse<List<CampusData.SearchResponse>> searchCampusByDescription(@RequestParam String description) {
        return SuccessResponse.of(campusService.searchCampusByDescription(description));
    }

    /**
     * [관리자 전용 API] 캠퍼스 생성 API
     * @param create 생성할 캠퍼스 정보
     */
    @IsAdmin
    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse<Void> createCampus(@RequestBody @Valid CampusData.Create create) {
        campusService.createCampus(create);
        return SuccessResponse.created();
    }
}
