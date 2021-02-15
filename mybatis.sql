create table member3 (
	id varchar2(20) primary key,
	email varchar2(30),
	password varchar2(30),
	name varchar2(30),
	fileName varchar2(50),
	del char(1) default 'n',
	regdate date
);
-- 하나의 아이디에 사진 여러개 저장하는 table
create table memberphoto (
	num number(10) primary key,
	id varchar2(20) references member3(id),
	fileName varchar2(50)
);
-- 자동으로 1씩 증가
create sequence memberphoto_seq;
--sql developer를 사용하여 생성 하여야 한다
create or replace FUNCTION get_seq
RETURN NUMBER
IS
BEGIN
RETURN memberphoto_seq.nextval;
END;

select * from member3;
select * from memberphoto;