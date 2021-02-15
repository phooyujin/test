package com.ch.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ch.mybatis.dao.MemberDao;
import com.ch.mybatis.model.Member;
import com.ch.mybatis.model.MemberPhoto;

@Repository
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao md;
	public Member select(String id) {
		return md.select(id);
	}
	public int insert(Member member) {
		return md.insert(member);
	}
	public int update(Member member) {
		return md.update(member);
	}
	public int delete(String id) {
		return md.delete(id);
	}
	public void insertPhoto(List<MemberPhoto> photos) {
		md.insertPhoto(photos);
		//return은 result로 보낸다 그래서 보낼 필요가 없다
	}
	public List<MemberPhoto> listPhoto(String id) {
		return md.listPhoto(id);
	}
}