create database if not exists catstagram;

use catstagram;

create table if not exists catstagram.catstagram_member
(
    member_idx             int auto_increment comment '用户的唯一标识'
        primary key,
    member_id              varchar(50)  not null comment '用户账号ID',
    member_name            varchar(100) not null comment '用户名称',
    member_pwd             varchar(255) not null comment '用户密码',
    member_email           varchar(100) null comment '用户邮箱',
    member_intro           text         null comment '用户简介',
    member_img             varchar(255) null comment '用户头像URL',
    member_joindate        date         null comment '用户注册日期',
    member_quit            varchar(10)  null comment '用户退出状态',
    member_last_alarm_time date         null comment '最后一次提醒时间',
    is_follow              int          null comment '是否已关注（1：是，0：否）',
    feed_count             int          null comment '用户发布的动态数量' CHECK (feed_count >= 0),
    following_count        int          null comment '用户关注的人数' CHECK (following_count >= 0),
    follower_count         int          null comment '用户的粉丝数量' CHECK (follower_count >= 0),
    feed_count_KM          varchar(100) null comment '用户动态数量的缩写',
    following_count_KM     varchar(100) null comment '用户关注人数的缩写',
    follower_count_KM      varchar(100) null comment '用户粉丝数量的缩写',
    created_at             date         null comment '创建时间',
    constraint member_email
        unique (member_email),
    constraint member_id
        unique (member_id)
) comment '用户信息表';

INSERT INTO catstagram_member
(member_id, member_name, member_pwd, member_email, member_intro, member_img, member_joindate, member_quit,
 member_last_alarm_time, is_follow, feed_count, following_count, follower_count, feed_count_KM, following_count_KM,
 follower_count_KM)
VALUES ('catstagram', 'catstagram', 'dfa48014d6e8a09b02cbb8fa138fd57f7da2b6e40e3d886ea7574dd06b38b57d',
        'catstagram@example.com', '这是一位用户的简介', 'http://example.com/avatar.jpg', CURDATE(), 'n', NULL, 0, 0, 0,
        0,
        '0', '0', '0');

# 账号:catstagram
# 密码:Catstagram@1

create table if not exists catstagram_feed
(
    feed_idx        int auto_increment primary key comment '动态的唯一标识',
    member_idx      int          not null comment '发布动态的用户ID',
    feed_img        varchar(255) null comment '动态图片URL',
    feed_content    text         null comment '动态内容',
    feed_like_count int          null comment '动态点赞数量' CHECK (feed_like_count >= 0),
    feed_date       date         null comment '动态发布日期',
    constraint catstagram_feed_ibfk_1 foreign key (member_idx) references catstagram_member (member_idx)
) comment '用户发布的动态表';

create table if not exists catstagram.catstagram_comment
(
    comment_idx         int auto_increment comment '评论的唯一标识'
        primary key,
    feed_idx            int                                           not null comment '对应动态的ID',
    member_idx          int                                           not null comment '发表评论的用户ID',
    comment_content     varchar(255)                                  null comment '评论内容',
    comment_like_count  int default 0 CHECK (comment_like_count >= 0) null comment '评论点赞数量',
    comment_date        date                                          null comment '评论日期',
    member_id           varchar(50)                                   null comment '发表评论的用户ID（冗余存储）',
    member_img          varchar(255)                                  null comment '发表评论的用户头像',
    comment_date_minute int                                           null comment '评论时间（分钟）',
    comment_date_time   varchar(50)                                   null comment '评论时间（详细）',
    comment_like_idx    int                                           null comment '点赞的唯一标识',
    constraint catstagram_comment_ibfk_1
        foreign key (feed_idx) references catstagram.catstagram_feed (feed_idx)
            on delete cascade,
    constraint catstagram_comment_ibfk_2
        foreign key (member_idx) references catstagram.catstagram_member (member_idx)
)
    comment '用户评论表';

create table if not exists catstagram.catstagram_comment_like
(
    comment_like_idx  int auto_increment comment '评论点赞的唯一标识'
        primary key,
    comment_idx       int  not null comment '对应评论的ID',
    member_idx        int  not null comment '点赞的用户ID',
    comment_like_date date null comment '点赞日期',
    created_at        date null comment '创建时间',
    constraint catstagram_comment_like_ibfk_1
        foreign key (comment_idx) references catstagram.catstagram_comment (comment_idx)
            on delete cascade,
    constraint catstagram_comment_like_ibfk_2
        foreign key (member_idx) references catstagram.catstagram_member (member_idx)
)
    comment '用户评论点赞表';

create table if not exists catstagram_feed_like
(
    feed_like_idx  int auto_increment primary key comment '动态点赞的唯一标识',
    feed_idx       int  not null comment '对应动态的ID',
    member_idx     int  not null comment '点赞的用户ID',
    feed_like_date date null comment '点赞日期',
    created_at     date null comment '创建时间',
    constraint catstagram_feedlike_ibfk_1 foreign key (feed_idx) references catstagram_feed (feed_idx) on delete cascade,
    constraint catstagram_feedlike_ibfk_2 foreign key (member_idx) references catstagram_member (member_idx)
) comment '用户动态点赞表';

create table if not exists catstagram_follow
(
    follow_idx                     int auto_increment primary key comment '关注的唯一标识',
    member_from                    int          not null comment '发起关注的用户ID',
    member_to                      int          not null comment '被关注的用户ID',
    follow_date                    date         null comment '关注日期',
    following_idx_of_my_following  int          null comment '我的关注的唯一标识',
    following_id_of_my_following   varchar(50)  null comment '我关注的用户ID',
    following_name_of_my_following varchar(100) null comment '我关注的用户名称',
    following_img_of_my_following  varchar(255) null comment '我关注的用户头像URL',
    num_of_followers               int          null comment '粉丝数量' CHECK (num_of_followers >= 0),
    my_following_list              text         null comment '我的关注列表',
    my_following_list_arr          text         null comment '我的关注列表（数组）',
    constraint catstagram_follow_ibfk_1 foreign key (member_from) references catstagram_member (member_idx),
    constraint catstagram_follow_ibfk_2 foreign key (member_to) references catstagram_member (member_idx)
) comment '用户关注表';

create table if not exists catstagram_alarm
(
    activity_type     varchar(50)  null comment '活动类型（如：点赞、评论）',
    member_id         varchar(50)  null comment '相关用户的ID',
    follow_idx        int          null comment '对应关注的ID',
    feed_idx          int          null comment '对应动态的ID',
    feed_img          varchar(255) null comment '对应动态的图片URL',
    feed_like_idx     int          null comment '对应点赞的唯一标识',
    comment_idx       int          null comment '对应评论的ID',
    comment_content   text         null comment '评论内容',
    comment_like_idx  int          null comment '评论点赞的唯一标识',
    alarm_date        datetime     null comment '提醒日期',
    alarm_date_minute int          null comment '提醒时间（分钟）',
    alarm_date_string varchar(255) null comment '提醒日期的字符串格式',
    constraint catstagram_alarm_ibfk_1 foreign key (follow_idx) references catstagram_follow (follow_idx),
    constraint catstagram_alarm_ibfk_2 foreign key (feed_idx) references catstagram_feed (feed_idx),
    constraint catstagram_alarm_ibfk_3 foreign key (comment_idx) references catstagram_comment (comment_idx)
) comment '用户提醒表';

create table if not exists catstagram_mainfollowingfeed
(
    member_idx       int          not null comment '发布动态的用户ID',
    member_id        varchar(50)  null comment '发布动态的用户ID（冗余存储）',
    member_img       varchar(255) null comment '发布动态的用户头像URL',
    feed_idx         int          not null primary key comment '动态的唯一标识',
    feed_img         varchar(255) null comment '动态图片URL',
    feed_content     text         null comment '动态内容',
    feed_like_count  int          null comment '动态点赞数量' CHECK (feed_like_count >= 0),
    feed_date        date         null comment '动态发布日期',
    feed_date_minute int          null comment '动态发布时间（分钟）',
    feed_date_time   varchar(50)  null comment '动态发布时间的字符串格式',
    feed_like_idx    int          null comment '点赞的唯一标识',
    constraint catstagram_mainfollowingfeed_ibfk_1 foreign key (member_idx) references catstagram_member (member_idx),
    constraint catstagram_mainfollowingfeed_ibfk_2 foreign key (feed_idx) references catstagram_feed (feed_idx)
) comment '用户关注的动态信息表';

-- 创建索引
create index feed_idx on catstagram_comment (feed_idx);
create index member_idx on catstagram_comment (member_idx);
create index comment_idx on catstagram_comment_like (comment_idx);
create index member_idx on catstagram_comment_like (member_idx);
create index member_idx on catstagram_feed (member_idx);
create index feed_idx on catstagram_feed_like (feed_idx);
create index member_idx on catstagram_feed_like (member_idx);
create index member_from on catstagram_follow (member_from);
create index member_to on catstagram_follow (member_to);
create index comment_idx on catstagram_alarm (comment_idx);
create index feed_idx on catstagram_alarm (feed_idx);
create index follow_idx on catstagram_alarm (follow_idx);
create index member_idx on catstagram_mainfollowingfeed (member_idx);
