package com.ssapick.server.domain.pick.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssapick.server.core.annotation.ValidPickRequest;
import com.ssapick.server.domain.pick.entity.Pick;
import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.user.dto.ProfileData;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

public class PickData {
	public enum PickStatus {
		PICKED, PASS, BLOCK
	}

	@Data
	public static class Search {
		private Long id;
		private ProfileData.Search sender;
		private ProfileData.Search receiver;
		private QuestionData.Search question;
		private boolean isMessageSend;
		private boolean alarm;
		private LocalDateTime createdAt;

		@JsonProperty("openedHints")
		private List<String> openedHints = new ArrayList<>();

		public static Search fromEntity(Pick pick, boolean isReceiver) {
			Search search = new Search();
			if (isReceiver) {
				search.sender = ProfileData.Search.fromEntityAnonymous(pick.getSender().getProfile());
				pick.getHintOpens().forEach(hintOpen -> search.openedHints.add(hintOpen.getContent()));
			} else {
				search.sender = ProfileData.Search.fromEntity(pick.getSender().getProfile());
			}

			search.receiver = ProfileData.Search.fromEntity(pick.getReceiver().getProfile());

			search.id = pick.getId();
			search.question = QuestionData.Search.fromEntity(pick.getQuestion());
			search.isMessageSend = pick.isMessageSend();
			search.createdAt = pick.getCreatedAt();
			search.alarm = pick.isAlarm();
			return search;
		}
	}

	@Builder
	@Data
	public static class PickCondition{
		private Integer index;
		private Integer pickCount;
		private Integer blockCount;
		private Integer passCount;
		boolean isCooltime;

		public static PickCondition init() {
			return PickCondition.builder()
				.index(0)
				.pickCount(0)
				.blockCount(0)
				.passCount(0)
				.isCooltime(false)
				.build();
		}

		public static PickCondition cooltime() {
			return PickCondition.builder()
				.isCooltime(true)
				.build();
		}
	}

    @Data
    @ValidPickRequest
    public static class Create {
        private Long receiverId;

        @NotNull(message = "질문 ID는 필수입니다.")
        private Long questionId;

        @Min(value = 0, message = "불가능한 상태입니다.")
        private int index;

        @NotNull(message = "픽 상태는 필수입니다.")
        private PickStatus status;
    }
}
