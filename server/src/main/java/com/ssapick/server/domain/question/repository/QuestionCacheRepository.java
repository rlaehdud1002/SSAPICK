package com.ssapick.server.domain.question.repository;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Repository;

import com.ssapick.server.domain.question.dto.QuestionData;
import com.ssapick.server.domain.question.entity.Question;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class QuestionCacheRepository {

	private static final String QUESTION_LIST_KEY = "question:list";
	@Resource(name = "redisTemplate")
	private ListOperations<String, QuestionData.Search> listOperations;

	// 질문들을 Redis 리스트에 저장
	public void saveAll(List<Question> questions){
		listOperations.trim(QUESTION_LIST_KEY, 0, 0);
		questions.stream()
			.forEach(question -> {
				listOperations.rightPush(QUESTION_LIST_KEY, QuestionData.Search.fromEntity(question));
			});
	}

	// 레디스에서 모든 질문 가져오기
	public List<QuestionData.Search> findAll() {
		return listOperations.range(QUESTION_LIST_KEY, 0, -1);
	}

	// 카테고리 별로 조회
	public List<QuestionData.Search> findQuestionsByCategory(Long categoryIds) {
		List<QuestionData.Search> questions = this.findAll();

		return questions.stream()
			.filter(question -> question.getCategory().getId().equals(categoryIds))
			.toList();
	}

	// 질문을 하나 추가하기
	public void add(Question question) {
		listOperations.rightPush(QUESTION_LIST_KEY, QuestionData.Search.fromEntity(question));
	}

	// 모든 질문 삭제
	public void removeAll() {
		listOperations.trim(QUESTION_LIST_KEY, 0, 0);
	}

	// 질문 ID로 조회
	public Optional<QuestionData.Search> findById(Long id) {
		List<QuestionData.Search> questions = this.findAll();
		return questions.stream()
			.filter(question -> question.getId().equals(id))
			.findFirst();
	}

	public void remove(Long id) {
		List<QuestionData.Search> questions = this.findAll();

		QuestionData.Search target = questions.stream()
			.filter(question -> question.getId().equals(id))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("해당 질문이 존재하지 않습니다."));

		listOperations.remove(QUESTION_LIST_KEY, 1, target);
	}
}
