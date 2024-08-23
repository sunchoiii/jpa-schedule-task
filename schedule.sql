create table SCHEDULE
(
    schedule_id    bigint auto_increment comment '일정 ID'  primary key,
    username   varchar(50)                        not null comment '유저명',
    title       varchar(100)                      not null comment '일정 제목',
    contents       varchar(100)                   not null comment '일정 내용',
    createDate datetime default CURRENT_TIMESTAMP not null comment '작성일',
    updateDate datetime default CURRENT_TIMESTAMP not null comment '수정일'
);

create table COMMENT
(
    comment_id    bigint auto_increment comment '댓글 ID'  primary key,
    username   varchar(50)                        not null comment '유저명',
    contents       varchar(100)                   not null comment '댓글 내용',
    createDate datetime default CURRENT_TIMESTAMP not null comment '작성일',
    updateDate datetime default CURRENT_TIMESTAMP not null comment '수정일'
);

create table USER
(
    user_id    bigint auto_increment comment '유저 ID'  primary key,
    username   varchar(50)                        not null comment '유저명',
    email       varchar(100)                      not null comment '이메일',
    createDate datetime default CURRENT_TIMESTAMP not null comment '작성일',
    updateDate datetime default CURRENT_TIMESTAMP not null comment '수정일'
);

