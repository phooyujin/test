package com.ch.mybatis.controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ch.mybatis.model.Member;
import com.ch.mybatis.model.MemberPhoto;
import com.ch.mybatis.service.MemberService;
@Controller
public class MemberController {
	@Autowired
	private MemberService ms;
	@RequestMapping("joinForm")
	public String joinForm() {
		return "joinForm"; //jsp파일인 joinForm으로 이동
	}
	@RequestMapping("joinForm2")
	public String joinForm2() {
		return "joinForm2";
	}
	@RequestMapping(value = "idChk",
			produces = "text/html;charset=utf-8")
	//jsp로 가지 말고 바로 본문 문장을 전달해라
	@ResponseBody
	public String idChk(String id) {
		String msg="";
		//현재 있는 id가지고 memberService에서 읽어와라
		Member member = ms.select(id);
		if(member==null) msg="사용 가능한 아이디입니다";
		else msg = "이미 사용중이니 다른 아이디를 사용하세요";
		return msg; //jsp없이 바로 msg를 전달
	}
	@RequestMapping("join")
	public String join(Member member, Model model,HttpSession session) throws IOException {
		int result = 0;
		//member는 화면에서 입력한 데이터, mem은 테이블에서 id로 읽어온 데이터
		Member mem = ms.select(member.getId());
		if(mem == null) {
			String fileName = member.getFile().getOriginalFilename();
			member.setFileName(fileName);
			String real = session.getServletContext()
					.getRealPath("/resources/upload");
			FileOutputStream fos = new FileOutputStream(
					new File(real+"/"+fileName));
			fos.write(member.getFile().getBytes());
			fos.close();
			result = ms.insert(member);
		} else result = -1; //-1: 이미 있는 데이터를 왜 입력하냐
		model.addAttribute("result",result);//처리 결과를 보내줌
		return "join";
	}
	@RequestMapping("join2")
	public String join2(Member member, Model model, HttpSession session,
			MultipartHttpServletRequest mr) throws IOException {
		int result = 0;
		Member mem = ms.select(member.getId());
		if(mem == null) {
			String real = session.getServletContext()
					.getRealPath("/resources/upload");
			//여러 개 그림 파일을 한번에 받아 온다
			List<MultipartFile> list = mr.getFiles("file");
			//사진 이름 여러개 저장
			List<MemberPhoto> photos = new ArrayList<MemberPhoto>();
			//여러 개 사진을 하나씩 추출하여 폴더에 저장하는 작업
			for(MultipartFile mf: list) {
				MemberPhoto mp = new MemberPhoto();
				String fileName = mf.getOriginalFilename();
				mp.setFileName(fileName);
				mp.setId(member.getId());
				//DB에 저장하기 위해서 그림파일명과 id를 저장을 여러 개
				photos.add(mp);
				//실제 upload 폴더에 저장하는 작업
				FileOutputStream fos = new FileOutputStream(
						new File(real+"/"+fileName));
				fos.write(mf.getBytes());
				fos.close();
				//member에 마지막 그림 저장
				member.setFileName(fileName);
			}
			//다 끝나면 입력을 한다! 회원정보와 마지막 사진 1장만 저장
			result = ms.insert(member);
			ms.insertPhoto(photos);//사진과 id만 여러개 저장
		} else result = -1;
		model.addAttribute("result",result);
		return "join";
	}
	@RequestMapping("loginForm")
	public String loginForm() {
		return "loginForm";
	}
	@RequestMapping("login")
	public String login(Member member,Model model,HttpSession session) {
		int result = 0; //암호가 다르다
		Member mem = ms.select(member.getId());
		if(mem == null || mem.getDel().equals("y"))
			result = -1; //없는 아이디
		else if(mem.getPassword().equals(member.getPassword())) {
			result = 1; //성공
			session.setAttribute("id", member.getId());
		}
		model.addAttribute("result",result);
		return "login";
	}
	@RequestMapping("main")
	public String main(Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		Member member = null;
		if(id != null && !id.equals("")) {
			member =ms.select(id);
			model.addAttribute("member",member);
		}
		return "main";
	}
	@RequestMapping("updateForm")
	public String updateForm(Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		Member member = ms.select(id);
		model.addAttribute("member",member);
		return "updateForm";
	}
	@RequestMapping("update")
	public String update(Member member, Model model,HttpSession session) throws IOException {
		int result = 0;
		String fileName = member.getFile().getOriginalFilename();
		// 파일명이 변경 되었으면 
		if(fileName != null && !fileName.equals("")) {
			member.setFileName(fileName);
			String real = session.getServletContext()
					.getRealPath("/resources/upload");
			FileOutputStream fos = new FileOutputStream(
					new File(real+"/"+fileName));
			fos.write(member.getFile().getBytes());
			fos.close();
		} else result = -1;
		result = ms.update(member);
		model.addAttribute("result",result);//처리 결과를 보내줌
		return "update";
	}
	@RequestMapping("delete")
	public String delete(Model model,HttpSession session) {
		String id = (String)session.getAttribute("id");
		int result = ms.delete(id);
		if(result>0) session.invalidate();
		model.addAttribute("result",result);
		return "delete";
	}
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "logout";
	}
	@RequestMapping("view2")
	public String view2(Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		Member member = ms.select(id);
		List<MemberPhoto> list = ms.listPhoto(id);
		model.addAttribute("member",member);
		model.addAttribute("list",list);
		return "view2";
	}
	@RequestMapping("view")
	public String view(Model model, HttpSession session) {
		String id = (String)session.getAttribute("id");
		Member member = ms.select(id);
		model.addAttribute("member",member);
		return "view";
	}
}