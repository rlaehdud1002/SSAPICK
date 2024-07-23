package com.ssapick.server.domain.user.dto;

import com.ssapick.server.domain.user.entity.Campus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

public class CampusData {
    @Data
    public static class Create {
        private String name;
        private short section;
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
