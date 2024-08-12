package com.ssapick.server.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	// Common
	SERVER_ERROR(1000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생하였습니다."),

	// Authentication, Authorization
	UNAUTHORIZED(2000, HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
	FORBIDDEN(2001, HttpStatus.FORBIDDEN, "권한이 없습니다."),
	INVALID_TOKEN(2002, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
	EXPIRED_TOKEN(2003, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
	INVALID_REFRESH_TOKEN(2004, HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
	EXPIRED_REFRESH_TOKEN(2005, HttpStatus.UNAUTHORIZED, "만료된 리프레시 토큰입니다."),
	ALREADY_AUTHORIZED(2006, HttpStatus.BAD_REQUEST, "이미 인증된 사용자입니다."),
	NOT_FOUND_USER(2007, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
	INVALID_ACCESS_TOKEN(2008, HttpStatus.UNAUTHORIZED, "유효하지 않은 액세스 토큰입니다."),
	INVALID_MATTERMOST_INFO(2009, HttpStatus.UNAUTHORIZED, "메타모스트 정보가 일치하지 않습니다."),
	ACCESS_DENIED(2010, HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
	ALREADY_FOLLOWED_USER(2011, HttpStatus.BAD_REQUEST, "이미 팔로우한 사용자입니다."),
	NOT_FOLLOWED_USER(2012, HttpStatus.BAD_REQUEST, "팔로우 되지 않은 사용자입니다."),
	FCM_TOKEN_NOT_FOUND(2013, HttpStatus.NOT_FOUND, "FCM 토큰을 찾을 수 없습니다."),

	// Invalid Value
	INVALID_INPUT_VALUE(3000, HttpStatus.BAD_REQUEST, "올바르지 않은 입력 값입니다."),
	NOTIFICATION_SEND_FAIL(3001, HttpStatus.BAD_REQUEST, "알림 전송에 실패하였습니다."),

	// Attendance
	ALREADY_CHECKIN_TODAY(4000, HttpStatus.BAD_REQUEST, "오늘 이미 출석을 하셨습니다."),

	// Question
	DELETED_QUESTION(5001, HttpStatus.BAD_REQUEST, "삭제된 질문입니다."),
	NOT_FOUND_QUESTION_CATEGORY(5002, HttpStatus.NOT_FOUND, "질문 카테고리를 찾을 수 없습니다."),
	EXIST_QUESTION_BAN(5003, HttpStatus.BAD_REQUEST, "이미 질문이 차단되어 있습니다."),
	NOT_FOUND_QUESTION(5004, HttpStatus.NOT_FOUND, "질문을 찾을 수 없습니다."),
	EXIST_QUESTION(5005, HttpStatus.BAD_REQUEST, "이미 비슷한 질문이 존재합니다."),
	NOT_FOUND_QUESTION_BAN(5006, HttpStatus.NOT_FOUND, "질문 차단을 찾을 수 없습니다."),

	//Pick
	NOT_FOUND_PICK(6001, HttpStatus.NOT_FOUND, "픽을 찾을 수 없습니다."),
	INVALID_PICK_INDEX(6002, HttpStatus.BAD_REQUEST, "픽 인덱스가 올바르지 않습니다."),
	PICK_COOLTIME(6003, HttpStatus.BAD_REQUEST, "픽 쿨타임입니다."),
	PASS_BLOCK_LIMIT(6004, HttpStatus.BAD_REQUEST, "패스와 차단은 합쳐서 최대 5개까지 할 수 있습니다."),
	USER_PICK_LOCK(6005, HttpStatus.BAD_REQUEST, "이미 들어온 요청입니다. 다시 요청해주세요."),

	//MESSAGE
	ALREADY_SEND_MESSAGE(7001, HttpStatus.BAD_REQUEST, "하나의 픽에 대해서는 하나의 메시지만 보낼 수 있습니다."),
	NOT_FOUND_MESSAGE(7002, HttpStatus.NOT_FOUND, "메시지를 찾을 수 없습니다."),

	// User
	EMPTY_FILE(8000, HttpStatus.BAD_REQUEST, "파일이 비어있습니다."),
	FAIL_TO_DELETE_FILE(8001, HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제에 실패하였습니다."),
	NOT_SUPPORTED_EXTENTION(8002, HttpStatus.BAD_REQUEST, "지원하지 않는 확장자입니다."),
	FAIL_TO_CREATE_FILE(8003, HttpStatus.INTERNAL_SERVER_ERROR, "파일 생성에 실패하였습니다."),

	//AI
	OFFENSIVE_CONTENT(9001, HttpStatus.BAD_REQUEST, "부적절한 내용을 포함하고 있습니다."),

	// PICKCO
	SHORT_OF_PICKCO(10000, HttpStatus.BAD_REQUEST, "픽코가 부족합니다."),

	//UserBan
	NOT_FOUND_USER_BAN(11000, HttpStatus.NOT_FOUND, "사용자 차단을 찾을 수 없습니다."),
	ALREADY_BAN_USER(11001, HttpStatus.BAD_REQUEST, "이미 차단한 사용자입니다."),

	// Notification
	NOT_FOUND_NOTIFICATION(12000, HttpStatus.NOT_FOUND, "알림을 찾을 수 없습니다."),
	API_REQUEST_ERROR(12001, HttpStatus.INTERNAL_SERVER_ERROR, "API 요청 중 오류가 발생하였습니다.");

	private final int code;
	private final HttpStatus status;
	private final String message;
}
