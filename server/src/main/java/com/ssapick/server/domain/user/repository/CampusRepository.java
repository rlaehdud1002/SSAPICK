package com.ssapick.server.domain.user.repository;

import com.ssapick.server.domain.user.entity.Campus;
import com.ssapick.server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    /** 캠퍼스 이름으로 캠퍼스 검색 */
    List<Campus> findByName(String name);

    /**
     * 반별 특징 (전공/비전공)으로 캠퍼스 검색
     */
    List<Campus> findByDescription(String description);

    /** 캠퍼스 정보(IDs) 기반으로 속한 학생들 데이터 조회 */
    @Query("SELECT u FROM User u LEFT JOIN FETCH Profile p, Campus c WHERE u.profile.campus.id IN :campusIds")
    List<User> findUserByCampusIds(List<Long> campusIds);

    /** 캠퍼스 정보(ID) 기반으로 속한 학생들 데이터 조회 */
    @Query("SELECT u FROM User u LEFT JOIN FETCH Profile p, Campus c WHERE u.profile.campus.id = :campusId")
    List<User> findUserByCampusId(Long campusId);
}
