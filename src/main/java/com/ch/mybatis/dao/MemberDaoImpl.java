package com.ch.mybatis.dao;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ch.mybatis.model.Member;
import com.ch.mybatis.model.MemberPhoto;
@Repository
public class MemberDaoImpl implements MemberDao{
	@Autowired
	private SqlSessionTemplate sst;
	public Member select(String id) {
		return sst.selectOne("memberns.select", id);
	}
	public int insert(Member member) {
		return sst.insert("memberns.insert", member);
	}
	public int update(Member member) {
		return sst.update("memberns.update",member);
	}
	public int delete(String id) {
		return sst.update("memberns.delete",id);
	}
	//void 현재 메서드를 호출한 객체에게 처리 결과를 전달하지 않는다
//	public void insertPhoto(List<MemberPhoto> photos) {
//		sst.insert("memberns.insertPhoto", photos);		
//	}
	public void insertPhoto(List<MemberPhoto> photos) {
		for(MemberPhoto mp: photos) {
			sst.insert("memberns.insertPh", mp);		
		}	
	}
	public List<MemberPhoto> listPhoto(String id) {
		return sst.selectList("memberns.listPhoto",id);
	}
}