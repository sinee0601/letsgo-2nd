-- =============================================================================
-- myScheduleMapper.xml 검증용 SQL
-- 더미 데이터(DummyData.sql) 적재 후 실행 가정
-- 각 블록 단독 실행 가능. UPDATE/INSERT/DELETE는 BEGIN~ROLLBACK으로 감쌌으므로
-- 실제 데이터에 영향 없음. 영구 반영하려면 ROLLBACK을 COMMIT으로 교체.
-- =============================================================================


-- =============================================================================
-- [SELECT] getMyScheduleListAllByDate
-- 입력: userId = 'user01'
-- 기대: user01 본인 스케줄(S001, S002) + 공유받은 스케줄(S003, S005)
-- =============================================================================
SELECT s.my_schedule_id,
       s.title AS sch_title,
       s.start_at,
       s.is_shared,
       p.title AS place_title,
       p.addr1,
       p.first_image,
       v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01'
UNION ALL
SELECT s.my_schedule_id,
       s.title AS sch_title,
       s.start_at,
       s.is_shared,
       p.title AS place_title,
       p.addr1,
       p.first_image,
       v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
ORDER BY start_at DESC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleListAllByTitle
-- 입력: userId = 'user01'
-- =============================================================================
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01'
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
ORDER BY sch_title ASC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleListSharedByDate
-- 입력: userId = 'user01'
-- 기대: user01 본인 스케줄 중 is_shared=1인 것(S001) + 공유받은 스케줄
-- =============================================================================
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND s.is_shared = 1
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
ORDER BY start_at DESC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleListSharedByTitle
-- 입력: userId = 'user01'
-- =============================================================================
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND s.is_shared = 1
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
ORDER BY sch_title ASC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleListSearchByDate
-- 입력: userId = 'user01', keyword = '한강'
-- 기대: 스케줄 제목 또는 장소 제목에 '한강' 포함된 행
-- =============================================================================
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01' AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
ORDER BY start_at DESC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleListSearchByTitle
-- 입력: userId = 'user01', keyword = '한강'
-- =============================================================================
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01' AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
ORDER BY sch_title ASC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleListSearchSharedByDate
-- 입력: userId = 'user01', keyword = '한강'
-- =============================================================================
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND s.is_shared = 1
  AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01' AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
ORDER BY start_at DESC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleListSearchSharedByTitle
-- 입력: userId = 'user01', keyword = '한강'
-- =============================================================================
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND s.is_shared = 1
  AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01' AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
ORDER BY sch_title ASC, visit_order ASC;


-- =============================================================================
-- [SELECT] getMyScheduleList (동적 쿼리 - 4가지 케이스)
-- =============================================================================
-- 케이스1: sharedFilter=false, keyword 없음, sortType='date'
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01'
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
ORDER BY start_at DESC, visit_order ASC;

-- 케이스2: sharedFilter=true, keyword 없음, sortType='title'
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND s.is_shared = 1
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
ORDER BY sch_title ASC, visit_order ASC;

-- 케이스3: sharedFilter=false, keyword='한강', sortType='date'
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01'
  AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
  AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
ORDER BY start_at DESC, visit_order ASC;

-- 케이스4: sharedFilter=true, keyword='한강', sortType='title'
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.user_id = 'user01' AND s.is_shared = 1
  AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
UNION ALL
SELECT s.my_schedule_id, s.title AS sch_title, s.start_at, s.is_shared,
       p.title AS place_title, p.addr1, p.first_image, v.visit_order
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
         JOIN schedule_share_user ssu ON s.my_schedule_id = ssu.my_schedule_id
WHERE ssu.shared_user_id = 'user01'
  AND (s.title LIKE CONCAT('%', '한강', '%') OR p.title LIKE CONCAT('%', '한강', '%'))
ORDER BY sch_title ASC, visit_order ASC;


-- =============================================================================
-- [DELETE] deleteVisitItemsByScheduleId
-- 입력: scheduleId = 'S001'
-- 검증: ROLLBACK 전에 COUNT 변화 확인
-- =============================================================================
START TRANSACTION;
SELECT COUNT(*) AS before_cnt FROM visit_item WHERE schedule_id = 'S001';
DELETE FROM visit_item WHERE schedule_id = 'S001';
SELECT COUNT(*) AS after_cnt FROM visit_item WHERE schedule_id = 'S001';
ROLLBACK;


-- =============================================================================
-- [DELETE] deleteScheduleById
-- 입력: scheduleId = 'S009'
-- 주의: visit_item과 schedule_share_user에 FK CASCADE 걸려 있으므로 함께 삭제됨
-- =============================================================================
START TRANSACTION;
SELECT * FROM my_schedule WHERE my_schedule_id = 'S009';
DELETE FROM my_schedule WHERE my_schedule_id = 'S009';
SELECT * FROM my_schedule WHERE my_schedule_id = 'S009';
ROLLBACK;


-- =============================================================================
-- [DELETE] deleteScheduleByIdAndUserId
-- 입력: scheduleId = 'S001', userId = 'user01'
-- =============================================================================
START TRANSACTION;
SELECT * FROM my_schedule WHERE my_schedule_id = 'S001' AND user_id = 'user01';
DELETE FROM my_schedule WHERE my_schedule_id = 'S001' AND user_id = 'user01';
SELECT * FROM my_schedule WHERE my_schedule_id = 'S001';
ROLLBACK;


-- =============================================================================
-- [UPDATE] updateVisitItem
-- 입력: visitItemId = 1, visitOrder = 99, distanceToNext = 999
-- =============================================================================
START TRANSACTION;
SELECT visit_item_id, visit_order, distance_to_next FROM visit_item WHERE visit_item_id = '1';
UPDATE visit_item
SET visit_order = 99, distance_to_next = 999
WHERE visit_item_id = '1';
SELECT visit_item_id, visit_order, distance_to_next FROM visit_item WHERE visit_item_id = '1';
ROLLBACK;


-- =============================================================================
-- [UPDATE] updateSchedule
-- 입력: scheduleId='S001', userId='user01', scheduleTitle='수정된 제목',
--      startAt='2026-12-31 00:00:00', budgetDetail='30만원', todoDetail='수정 메모', isShared=1
-- =============================================================================
START TRANSACTION;
SELECT title, start_at, budget_details, todo_details, is_shared FROM my_schedule WHERE my_schedule_id = 'S001';
UPDATE my_schedule
SET title = '수정된 제목', start_at = '2026-12-31 00:00:00',
    budget_details = '30만원', todo_details = '수정 메모', is_shared = 1
WHERE my_schedule_id = 'S001' AND user_id = 'user01';
SELECT title, start_at, budget_details, todo_details, is_shared FROM my_schedule WHERE my_schedule_id = 'S001';
ROLLBACK;


-- =============================================================================
-- [UPDATE] setMyScheduleTitle
-- 입력: myScheduleId='S001', userId='user01', title='제목변경'
-- =============================================================================
START TRANSACTION;
SELECT title FROM my_schedule WHERE my_schedule_id = 'S001';
UPDATE my_schedule SET title = '제목변경'
WHERE my_schedule_id = 'S001' AND user_id = 'user01';
SELECT title FROM my_schedule WHERE my_schedule_id = 'S001';
ROLLBACK;


-- =============================================================================
-- [UPDATE] setTodoDetail
-- 입력: scheduleId='S001', todoDetail='새 할일'
-- =============================================================================
START TRANSACTION;
SELECT todo_details FROM my_schedule WHERE my_schedule_id = 'S001';
UPDATE my_schedule SET todo_details = '새 할일' WHERE my_schedule_id = 'S001';
SELECT todo_details FROM my_schedule WHERE my_schedule_id = 'S001';
ROLLBACK;


-- =============================================================================
-- [UPDATE] setBudgetDetail
-- 입력: scheduleId='S001', budgetDetail='50만원'
-- =============================================================================
START TRANSACTION;
SELECT budget_details FROM my_schedule WHERE my_schedule_id = 'S001';
UPDATE my_schedule SET budget_details = '50만원' WHERE my_schedule_id = 'S001';
SELECT budget_details FROM my_schedule WHERE my_schedule_id = 'S001';
ROLLBACK;


-- =============================================================================
-- [UPDATE] setStartAt
-- 입력: scheduleId='S001', userId='user01', startAt='2026-12-31'
-- =============================================================================
START TRANSACTION;
SELECT start_at FROM my_schedule WHERE my_schedule_id = 'S001';
UPDATE my_schedule SET start_at = STR_TO_DATE('2026-12-31', '%Y-%m-%d')
WHERE my_schedule_id = 'S001' AND user_id = 'user01';
SELECT start_at FROM my_schedule WHERE my_schedule_id = 'S001';
ROLLBACK;


-- =============================================================================
-- [UPDATE] setCompanionPermission
-- 입력: myScheduleId='S001', sharedUserId='user02', permission='W'
-- =============================================================================
START TRANSACTION;
SELECT permission FROM schedule_share_user WHERE my_schedule_id = 'S001' AND shared_user_id = 'user02';
UPDATE schedule_share_user SET permission = 'W'
WHERE my_schedule_id = 'S001' AND shared_user_id = 'user02';
SELECT permission FROM schedule_share_user WHERE my_schedule_id = 'S001' AND shared_user_id = 'user02';
ROLLBACK;


-- =============================================================================
-- [SELECT] getTodoDetail
-- 입력: scheduleId='S001'
-- =============================================================================
SELECT todo_details FROM my_schedule WHERE my_schedule_id = 'S001';


-- =============================================================================
-- [SELECT] getBudgetDetail
-- 입력: scheduleId='S001'
-- =============================================================================
SELECT budget_details FROM my_schedule WHERE my_schedule_id = 'S001';


-- =============================================================================
-- [SELECT] getScheduleTitle
-- 입력: scheduleId='S001'
-- =============================================================================
SELECT title FROM my_schedule WHERE my_schedule_id = 'S001';


-- =============================================================================
-- [SELECT] getStartAt
-- 입력: scheduleId='S001'
-- =============================================================================
SELECT DATE_FORMAT(start_at, '%Y-%m-%d') FROM my_schedule WHERE my_schedule_id = 'S001';


-- =============================================================================
-- [SELECT] getNextMyScheduleIdCandidate
-- 시퀀스 호출. 실행할 때마다 다음 값. CACHE 20이므로 캐시 단위로 jump 가능
-- =============================================================================
SELECT CONCAT('S', LPAD(NEXTVAL(seq_my_schedule), 3, '0')) AS next_id;


-- =============================================================================
-- [SELECT] checkMyScheduleIdExists
-- 입력: id='S001' / 'S999'
-- 기대: 1 / 0
-- =============================================================================
SELECT COUNT(my_schedule_id) FROM my_schedule WHERE my_schedule_id = 'S001';
SELECT COUNT(my_schedule_id) FROM my_schedule WHERE my_schedule_id = 'S999';


-- =============================================================================
-- [INSERT] insertMyScheduleRow
-- 입력: myScheduleId='S999', title='테스트', userId='user01'
-- =============================================================================
START TRANSACTION;
INSERT INTO my_schedule (my_schedule_id, title, start_at, user_id, is_shared)
VALUES ('S999', '테스트', NOW(), 'user01', 0);
SELECT * FROM my_schedule WHERE my_schedule_id = 'S999';
ROLLBACK;


-- =============================================================================
-- [INSERT] addVisitItem
-- 입력: visitOrder=1, placeId='1', scheduleId='S002'
-- =============================================================================
START TRANSACTION;
SELECT COUNT(*) AS before_cnt FROM visit_item WHERE schedule_id = 'S002';
INSERT INTO visit_item (visit_item_id, visit_order, schedule_type, place_id, schedule_id)
VALUES (NEXTVAL(seq_visit_item), 1, 'SCHEDULE', '1', 'S002');
SELECT COUNT(*) AS after_cnt FROM visit_item WHERE schedule_id = 'S002';
ROLLBACK;


-- =============================================================================
-- [INSERT] addCompanion
-- 입력: myScheduleId='S001', sharedUserId='user03'
-- =============================================================================
START TRANSACTION;
INSERT INTO schedule_share_user (share_id, permission, my_schedule_id, shared_user_id)
VALUES (NEXTVAL(seq_schedule_share_user), 'R', 'S001', 'user03');
SELECT * FROM schedule_share_user WHERE my_schedule_id = 'S001' AND shared_user_id = 'user03';
ROLLBACK;


-- =============================================================================
-- [INSERT] shareToPostInsert + shareVisitItemsToPost (연동 검증)
-- 입력: myScheduleId='S001', userId='user01', isAnonymous=0
-- 매퍼에서 두 쿼리 연달아 호출하는 시나리오. 같은 세션의 LASTVAL 의존
-- =============================================================================
START TRANSACTION;
INSERT INTO schedule_post
(post_id, title, budget_details, todo_details, is_anonymous, view_count, like_count, posted_at, user_id)
SELECT CONCAT('P', LPAD(NEXTVAL(seq_schedule_post), 3, '0')),
       title, budget_details, todo_details, 0, 0, 0, NOW(), 'user01'
FROM my_schedule WHERE my_schedule_id = 'S001';

INSERT INTO visit_item (visit_item_id, visit_order, distance_to_next, schedule_type, place_id, schedule_id)
SELECT NEXTVAL(seq_visit_item), visit_order, distance_to_next, 'POST', place_id,
       CONCAT('P', LPAD(LASTVAL(seq_schedule_post), 3, '0'))
FROM visit_item WHERE schedule_id = 'S001';

SELECT * FROM schedule_post WHERE user_id = 'user01' ORDER BY posted_at DESC LIMIT 1;
SELECT * FROM visit_item WHERE schedule_type = 'POST'
                           AND schedule_id = (SELECT post_id FROM schedule_post WHERE user_id = 'user01' ORDER BY posted_at DESC LIMIT 1);
ROLLBACK;


-- =============================================================================
-- [SELECT] isScheduleOwnedByUser
-- 기대: 'S001'+'user01' → 1, 'S001'+'user02' → 0
-- =============================================================================
SELECT COUNT(my_schedule_id) FROM my_schedule WHERE my_schedule_id = 'S001' AND user_id = 'user01';
SELECT COUNT(my_schedule_id) FROM my_schedule WHERE my_schedule_id = 'S001' AND user_id = 'user02';


-- =============================================================================
-- [SELECT] listMyScheduleIdAndTitle
-- 입력: userId='user01'
-- =============================================================================
SELECT my_schedule_id, title
FROM my_schedule WHERE user_id = 'user01'
ORDER BY start_at DESC, my_schedule_id DESC;


-- =============================================================================
-- [SELECT] getScheduleRoute
-- 입력: scheduleId='S001'
-- =============================================================================
SELECT v.visit_item_id, v.visit_order, p.place_id, p.title
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE v.schedule_id = 'S001'
ORDER BY v.visit_order ASC;


-- =============================================================================
-- [SELECT] getMapSchedule
-- 입력: scheduleId='S001'
-- =============================================================================
SELECT p.title, v.visit_order, p.mapx, p.mapy, v.distance_to_next
FROM my_schedule s
         JOIN visit_item v ON s.my_schedule_id = v.schedule_id
         JOIN place p ON v.place_id = p.place_id
WHERE s.my_schedule_id = 'S001'
ORDER BY v.visit_order ASC;


-- =============================================================================
-- [DELETE] deleteVisitItemById
-- 입력: visitItemId='1'
-- =============================================================================
START TRANSACTION;
SELECT * FROM visit_item WHERE visit_item_id = '1';
DELETE FROM visit_item WHERE visit_item_id = '1';
SELECT * FROM visit_item WHERE visit_item_id = '1';
ROLLBACK;


-- =============================================================================
-- [SELECT] getCompanionList
-- 입력: myScheduleId='S001'
-- 기대: S001을 공유받은 사용자(user02) 정보
-- =============================================================================
SELECT u.user_id, u.name, u.email, ssu.permission
FROM schedule_share_user ssu
         JOIN users u ON ssu.shared_user_id = u.user_id
WHERE ssu.my_schedule_id = 'S001';