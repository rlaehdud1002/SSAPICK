package com.ssapick.server.domain.user.dto;

import com.ssapick.server.domain.user.entity.Campus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

public class CampusData {
    @Data
    public static class Create {
        @NotBlank(message = "캠퍼스 이름은 필수 입력 값입니다.")
        private String name;
        @NotNull(message = "반은 필수 입력 값입니다.")
        private short section;
        @NotNull(message = "캠퍼스 설명은 필수 입력 값입니다.")
        private String description;

        public Campus toEntity() {
            return Campus.createCampus(name, section, description);
        }
    }

    @Data
    public static class SearchResponse {
        private String name;
        private int section;
        private String description;

        public static SearchResponse fromEntity(Campus campus) {
            SearchResponse searchResponse = new SearchResponse();
            searchResponse.setName(campus.getName());
            searchResponse.setSection(campus.getSection());
            searchResponse.setDescription(campus.getDescription());
            return searchResponse;
        }
    }
}
