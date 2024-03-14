# DB 커넥션 수정
# 화면공통함수 = function.js
---

# DDL
CREATE TABLE `tb_post` (
    `id`            bigint(20)    NOT NULL AUTO_INCREMENT COMMENT 'PK',
    `title`         varchar(100)  NOT NULL COMMENT '제목',
    `content`       varchar(3000) NOT NULL COMMENT '내용',
    `writer`        varchar(20)   NOT NULL COMMENT '작성자',
    `view_cnt`      int(11)       NOT NULL COMMENT '조회 수',
    `notice_yn`     tinyint(1)    NOT NULL COMMENT '공지글 여부',
    `delete_yn`     tinyint(1)    NOT NULL COMMENT '삭제 여부',
    `created_date`  datetime      NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
    `modified_date` datetime               DEFAULT NULL COMMENT '최종 수정일시',
    PRIMARY KEY (`id`)
) COMMENT '게시글';


-- drop table `tb_member`;
CREATE TABLE `tb_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '회원 번호 (PK)',
  `login_id` varchar(20) NOT NULL COMMENT '로그인 ID',
  `password` varchar(60) NOT NULL COMMENT '비밀번호',
  `name` varchar(20) NOT NULL COMMENT '이름',
  `gender` enum('M','F') COMMENT '성별',
  `birthday` date comment '생년월일',
  `phone` varchar(15) comment '폰번호',
  `delete_yn` tinyint(1) NOT NULL COMMENT '삭제 여부(0:정상, 1:탈퇴)' default 0,
  `main_address` varchar(300) COMMENT '메인주소',
  `detail_address` varchar(300) COMMENT '디테일주소',
  `extra_address` varchar(300) COMMENT '참고항목-주소',
  `member_type` char(1) COMMENT '회원유형(0:일반회원, 1:관리자)' default 0,
  `created_date` datetime NOT NULL DEFAULT current_timestamp() COMMENT '생성일시',
  `modified_date` datetime DEFAULT NULL COMMENT '최종 수정일시',
  PRIMARY KEY (`id`),
  UNIQUE KEY uix_member_login_id (`login_id`)
) COMMENT '회원';





create table tb_comment (
      id bigint not null auto_increment comment '댓글 번호 (PK)'
    , post_id bigint not null comment '게시글 번호 (FK)'
    , content varchar(1000) not null comment '내용'
    , writer varchar(20) not null comment '작성자'
    , delete_yn tinyint(1) not null comment '삭제 여부'
    , created_date datetime not null default CURRENT_TIMESTAMP comment '생성일시'
    , modified_date datetime comment '최종 수정일시'
    , primary key(id)
) comment '댓글';


_select * from tb_member;_



---

# 
