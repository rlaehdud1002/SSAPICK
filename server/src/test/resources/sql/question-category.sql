-- 테이블 비우기
TRUNCATE TABLE public.hint_open CASCADE;
TRUNCATE TABLE public.hint CASCADE;
TRUNCATE TABLE public.notification CASCADE;
TRUNCATE TABLE public.message CASCADE;
TRUNCATE TABLE public.pick CASCADE;
TRUNCATE TABLE public.pickco_log CASCADE;
TRUNCATE TABLE public.profile CASCADE;
TRUNCATE TABLE public.question_registration CASCADE;
TRUNCATE TABLE public.question_ban CASCADE;
TRUNCATE TABLE public.question CASCADE;
TRUNCATE TABLE public.follow CASCADE;
TRUNCATE TABLE public.attendance CASCADE;
TRUNCATE TABLE public.alarm CASCADE;
TRUNCATE TABLE public.user_ban CASCADE;
TRUNCATE TABLE public.users CASCADE;
TRUNCATE TABLE public.campus CASCADE;
TRUNCATE TABLE public.question_category CASCADE;




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