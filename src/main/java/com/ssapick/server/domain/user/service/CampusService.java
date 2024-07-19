package com.ssapick.server.domain.user.service;

import com.ssapick.server.domain.user.dto.CampusData;
import com.ssapick.server.domain.user.dto.ProfileData;
import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.Profile;
import com.ssapick.server.domain.user.entity.User;
import com.ssapick.server.domain.user.repository.CampusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CampusService {
    private final CampusRepository campusRepository;

    /**
     * 캠퍼스 이름으로 캠퍼스 검색
     * @param name 검색할 캠퍼스 이름
     * @return {@link CampusData.SearchResponse} 조회된 캠퍼스 반 리스트
     */
    public List<CampusData.SearchResponse> searchCampusByName(String name) {
        return campusRepository.findByName(name).stream().map(CampusData.SearchResponse::fromEntity).toList();
    }

    /**
     * 반별 특징 (전공/비전공)으로 캠퍼스 검색
     * @param description 검색할 캠퍼스 반의 특징 (
     * @return {@link CampusData.SearchResponse} 조회된 캠퍼스 반 리스트
     */
    public List<CampusData.SearchResponse> searchCampusByDescription(String description) {
        return campusRepository.findByDescription(description).stream().map(CampusData.SearchResponse::fromEntity).toList();
    }

    public void createCampus(CampusData.Create request) {
        campusRepository.save(request.toEntity());
    }

    public List<ProfileData.Search> searchProfileByCampusIds(List<Long> campusIds) {
        return campusRepository.findUserByCampusIds(campusIds).stream().map(User::getProfile).map(ProfileData.Search::fromEntity).toList();
    }

    public List<ProfileData.Search> searchProfileByCampusId(Long campusId) {
        return campusRepository.findUserByCampusId(campusId).stream().map(User::getProfile).map(ProfileData.Search::fromEntity).toList();
    }
}
