CREATE TABLE board (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(60),
    content LONGTEXT
);

create index index_title on board (title); -- index 만듦

SHOW INDEX FROM board; -- index 보기

SELECT * FROM board WHERE title = ""; -- index타고 검색됨

SELECT * FROM board ORDER BY id DESC; -- 거꾸로 orderby하기

SELECT /*+ INDEX_DESC(user PRIMARY) */ id -- 힌트 줘서 거꾸로 orderby하기 index사용
FROM User;

SELECT * FROM Post LIMIT 0, 3; -- 3개씩 페이징

SELECT * FROM Post;