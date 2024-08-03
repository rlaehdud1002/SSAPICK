-- campus
INSERT INTO public.campus (description, name, section)
VALUES ('캠퍼스 설명 A', '캠퍼스 A', 1),
       ('캠퍼스 설명 B', '캠퍼스 B', 2),
       ('캠퍼스 설명 C', '캠퍼스 C', 3);

-- question_category
INSERT INTO question_category (question_category_id, name, thumbnail)
VALUES ('1', '연애/데이트', 'image'),
       ('2', '친구', 'image'),
       ('3', '가족', 'image'),
       ('4', '학업/프로젝트', 'image'),
       ('5', '직장/커리어', 'image'),
       ('6', '건강/운동', 'image'),
       ('7', '취미/여가', 'image'),
       ('8', '기술/IT', 'image'),
       ('9', '문화/예술', 'image'),
       ('10', '패션/뷰티', 'image'),
       ('11', '라이프스타일', 'image');
-- users
INSERT INTO public.users (created_at, updated_at, is_deleted, email, gender, is_locked, is_mattermost_confirmed, name,
                          provider_id, provider_type, role_type, username)
VALUES (NOW(), NOW(), FALSE, 'user1@example.com', 'M', FALSE, TRUE, 'User One', 'provider1', 'GOOGLE', 'USER', 'user1'),
       (NOW(), NOW(), FALSE, 'user2@example.com', 'F', FALSE, TRUE, 'User Two', 'provider2', 'NAVER', 'ADMIN', 'user2'),
       (NOW(), NOW(), FALSE, 'user3@example.com', 'M', FALSE, TRUE, 'User Three', 'provider3', 'KAKAO', 'PREMIUM_USER',
        'user3');

-- alarm
INSERT INTO public.alarm (add_question_alarm, message_alarm, nearby_alarm, pick_alarm, user_id)
VALUES (TRUE, FALSE, TRUE, TRUE, 1),
       (FALSE, TRUE, FALSE, TRUE, 2),
       (TRUE, TRUE, TRUE, FALSE, 3);

-- attendance
INSERT INTO public.attendance (created_at, updated_at, user_id)
VALUES (NOW(), NOW(), 1),
       (NOW(), NOW(), 2),
       (NOW(), NOW(), 3);

-- follow
INSERT INTO public.follow (created_at, updated_at, follower_id, following_id)
VALUES (NOW(), NOW(), 1, 2),
       (NOW(), NOW(), 2, 3),
       (NOW(), NOW(), 3, 1);


-- hint
INSERT INTO public.hint (created_at, updated_at, content, hint_type, visibility, user_id)
VALUES (NOW(), NOW(), '힌트 내용 A', 1, TRUE, 1),
       (NOW(), NOW(), '힌트 내용 B', 2, TRUE, 2),
       (NOW(), NOW(), '힌트 내용 C', 3, FALSE, 3);

-- notification
INSERT INTO public.notification (created_at, updated_at, is_read, notification_type, reference_id, user_id)
VALUES (NOW(), NOW(), FALSE, 'MESSAGE', 1, 1),
       (NOW(), NOW(), TRUE, 'PICK', 2, 2),
       (NOW(), NOW(), FALSE, 'ADD_QUESTION', 3, 3);

-- profile
INSERT INTO public.profile (created_at, updated_at, is_deleted, cohort, pickco, profile_image, campus_id, user_id)
VALUES (NOW(), NOW(), FALSE, 2024, 100, 'profile_image_a.png', 1, 1),
       (NOW(), NOW(), FALSE, 2024, 150, 'profile_image_b.png', 2, 2),
       (NOW(), NOW(), FALSE, 2024, 200, 'profile_image_c.png', 3, 3);

-- question
INSERT INTO public.question (created_at, updated_at, is_deleted, ban_count, content, is_alarm_sent, skip_count, user_id,
                             question_category_id, question_id)
VALUES (NOW(), NOW(), FALSE, 0, '연애/데이트1', FALSE, 0, 1, 1, 1),
       (NOW(), NOW(), FALSE, 1, '질문 내용 B', TRUE, 1, 2, 2, 2),
       (NOW(), NOW(), TRUE, 2, '질문 내용 C', FALSE, 2, 3, 3, 3),
       (NOW(), NOW(), FALSE, 0, '연애/데이트2', FALSE, 0, 1, 1, 4);

-- pick
INSERT INTO public.pick (created_at, updated_at, is_alarm_sent, is_message_sent, question_id, receiver, sender)
VALUES (NOW(), NOW(), TRUE, FALSE, 1, 2, 1),
       (NOW(), NOW(), FALSE, TRUE, 2, 3, 2),
       (NOW(), NOW(), TRUE, TRUE, 2, 1, 3),
       (NOW(), NOW(), TRUE, TRUE, 2, 1, 3),
       (NOW(), NOW(), TRUE, TRUE, 4, 1, 3),
       (NOW(), NOW(), TRUE, TRUE, 1, 1, 3),
       (NOW(), NOW(), TRUE, TRUE, 1, 1, 3),
       (NOW(), NOW(), TRUE, TRUE, 1, 1, 3);

-- hint_open
INSERT INTO public.hint_open (created_at, updated_at, hint_id, pick_id)
VALUES (NOW(), NOW(), 1, 1),
       (NOW(), NOW(), 2, 2),
       (NOW(), NOW(), 3, 3);

-- message
INSERT INTO public.message (created_at, updated_at, content, is_alarm_sent, is_receiver_deleted, is_sender_deleted,
                            pick_id, receiver, sender)
VALUES (NOW(), NOW(), '메시지 내용 A', TRUE, FALSE, FALSE, 1, 2, 1),
       (NOW(), NOW(), '메시지 내용 B', FALSE, TRUE, FALSE, 2, 3, 2),
       (NOW(), NOW(), '메시지 내용 C', TRUE, FALSE, TRUE, 3, 1, 3);

-- question_ban
INSERT INTO public.question_ban (created_at, updated_at, question_id, user_id, question_ban_id)
VALUES (NOW(), NOW(), 1, 2, 1),
       (NOW(), NOW(), 2, 3, 2),
       (NOW(), NOW(), 3, 1, 3),
       (NOW(), NOW(), 2, 1, 4);


-- question_registration
INSERT INTO public.question_registration (content, user_id, question_category_id)
VALUES ('등록된 질문 A', 1, 1),
       ('등록된 질문 B', 2, 2),
       ('등록된 질문 C', 3, 3);

-- user_ban
INSERT INTO public.user_ban (created_at, updated_at, from_user_id, to_user_id)
VALUES (NOW(), NOW(), 1, 2),
       (NOW(), NOW(), 2, 3),
       (NOW(), NOW(), 3, 1);

-- pickco_log
INSERT INTO public.pickco_log (created_at, updated_at, change, pickco_log_type, remain, user_id)
VALUES (NOW(), NOW(), 10, 'SIGN_UP', 100, 1),
       (NOW(), NOW(), -5, 'HINT_OPEN', 95, 2),
       (NOW(), NOW(), 20, 'ATTENDANCE', 115, 3);

