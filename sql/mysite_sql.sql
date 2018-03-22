-- 게시판
ALTER TABLE `board`
	DROP FOREIGN KEY `FK_users_TO_board`; -- 회원 -> 게시판

-- 댓글
ALTER TABLE `comment`
	DROP FOREIGN KEY `FK_users_TO_comment`; -- 회원 -> 댓글

-- 댓글
ALTER TABLE `comment`
	DROP FOREIGN KEY `FK_board_TO_comment`; -- 게시판 -> 댓글

-- 게시판
ALTER TABLE `board`
	ADD CONSTRAINT `FK_users_TO_board` -- 회원 -> 게시판
		FOREIGN KEY (
			`user_no` -- 사용자번호
		)
		REFERENCES `users` ( -- 회원
			`no` -- 번호
		)
        ON DELETE SET NULL
        ON UPDATE CASCADE;
        

-- 댓글
ALTER TABLE `comment`
	ADD CONSTRAINT `FK_users_TO_comment` -- 회원 -> 댓글
		FOREIGN KEY (
			`user_no` -- 회원번호
		)
		REFERENCES `users` ( -- 회원
			`no` -- 번호
		)
        ON DELETE SET NULL
        ON UPDATE CASCADE;

-- 댓글
ALTER TABLE `comment`
	ADD CONSTRAINT `FK_board_TO_comment` -- 게시판 -> 댓글
		FOREIGN KEY (
			`board_no` -- 게시판번호
		)
		REFERENCES `board` ( -- 게시판
			`no` -- 번호
		)
        ON DELETE CASCADE
        ON UPDATE CASCADE;

ALTER TABLE board AUTO_INCREMENT=1;
ALTER TABLE board CHANGE user_no user_no int(11) null;
ALTER TABLE comment CHANGE user_no user_no int(11) null;

INSERT
  INTO guestbook
VALUES (null, '안대혁', password('1234'), '안녕하세요.안녕하세요.', DATE_FORMAT('2013-01-15', '%Y-%m-%d %H:%i:%s'));

delete from guestbook where no =1;

SELECT no, name, password, content, DATE_FORMAT(reg_date, '%Y-%m-%d %H:%i:%s')
  FROM guestbook;
  
SELECT * from users;

-- 회원가입
INSERT INTO users
VALUES (null, '둘리', 'dooly@a.com', PASSWORD('1234'), 'male', now());

-- 로그인
SELECT no, name, password
  FROM users
 WHERE email='go@a.com';

-- 수정(회원정보)
UPDATE users
   SET password=PASSWORD('1234')
 WHERE no=2;
 
 
 SELECT * FROM board LIMIT 1;
(SELECT IFNULL(MAX(group_no), 0) +1 FROM board);

SELECT last_insert_id();

  SELECT board.no,
	     board.title,
         board.content,
         board.hit_count,
         board.reg_date,
         board.group_no,
         board.order_no,
         board.depth,
         board.user_no,
         users.no,
         users.name
    FROM board, users
   WHERE (board.user_no = users.no
     AND board.title LIKE '%11%')
      OR (board.user_no = users.no AND board.content LIKE '%11%')
ORDER BY group_no DESC, order_no ASC
   LIMIT 0, 10;
   
DELETE FROM board WHERE no=147;

SELECT COUNT(*) FROM board;

-- 글쓰기
INSERT INTO board
VALUES(
		null,
		'첫번째 글입니다.',
        '첫번째 글 테스트',
        0,
        now(),
        (SELECT IFNULL(MAX(group_no), 0)+1 FROM board as max_no),
        1,
        0,
        2
       );
 
-- Modify Test
UPDATE board
   SET title = '피곤', content = '지침'
 WHERE no = 3;
 

-- comment 
INSERT INTO comment
values (null, '긁적', now(), 1, 137);
INSERT INTO comment
values (null, '두번째', now(), 2, 137);
INSERT INTO comment
values (null, '세번째', now(), 1, 137);
INSERT INTO comment
values (null, '  네번째', now(), 2, 137);
INSERT INTO comment
values (null, '다섯', now(), 1, 137);
INSERT INTO comment
values (null, '육개장', now(), 2, 137);

INSERT INTO comment
values (null, '긁적', now(), 1, 136);
INSERT INTO comment
values (null, '두번째', now(), 2, 136);
INSERT INTO comment
values (null, '세번째', now(), 1, 136);
INSERT INTO comment
values (null, '  네번째', now(), 2, 136);
INSERT INTO comment
values (null, '다섯', now(), 1, 136);
INSERT INTO comment
values (null, '육개장', now(), 2, 136);



  SELECT c.no,
	     c.content,
         DATE_FORMAT(c.reg_date, '%Y-%m-%d %H:%i:%s'),
         c.user_no,
         c.board_no,
         u.name
    FROM comment c, users u
   WHERE c.user_no = u.no
     AND board_no = 136
ORDER BY c.reg_date DESC;




-- Delete All
DELETE FROM board;


-- Insert a dummy datas for paging test
DELIMITER $$
DROP PROCEDURE IF EXISTS loopInsert$$
CREATE PROCEDURE loopInsert()
BEGIN
	DECLARE i INT DEFAULT 1;
	WHILE i <= 137 DO
		INSERT INTO board
          VALUES (null,
				  concat(i, '번째 게시물'),
                  concat(i, '번째 글글글글'),
                  0,
                  CURRENT_TIMESTAMP,
                  (SELECT IFNULL(MAX(group_no), 0)+1 FROM board as max_no),
                  1, 0, 2);
		SET i = i + 1;
	END WHILE;
END$$
DELIMITER $$

CALL loopInsert$$

SELECT * FROM board;







