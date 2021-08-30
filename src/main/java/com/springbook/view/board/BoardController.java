package com.springbook.view.board;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.impl.BoardDAO;
import com.springbook.biz.board.impl.BoardService;

@Controller
@SessionAttributes("board") //수정 작업 처리할 때 유용 - 
//1. writer null로 업데이트 방지
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//글 목록 변환 처리 JSON , getBoardList()와 같다
	@RequestMapping("/dataTransform.do")
	@ResponseBody //자바 객체를 http 응답 프로토콜의 몸체로 변환하기 위해 사용
	public List<BoardVO> dataTransform(BoardVO vo){
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		return boardList;
	}
	
	//검색 조건 목록 설정
	@ModelAttribute("conditionMap") //View(JSP)에서 사용할 데이터를 설정한 용도
	// or Command 객체(UserVO) 이름 변경
	//@ModelAttribute 메소드 실행 결과로 리턴된 객체는 자동으로 Model에 저장된다. = 리턴된 객체를 View 페이지에서 사용할 수 있다
	//@ModelAttribute 이 @RequestMapping 보다 먼저 호출 된다 
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		return conditionMap;
	}
	
	//글 등록
	@RequestMapping("/insertBoard.do")
	public String insertBoard(BoardVO vo) throws IOException {
		//파일 업로드 처리
		MultipartFile uploadFile = vo.getUploadFile();
		if(!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("D:/" + fileName));
		}
		boardService.insertBoard(vo);
		return "getBoardList.do";
	}
	
	//글 수정
	@RequestMapping("/updateBoard.do") //수정할 작성자 이름을 확인
	public String updateBoard(@ModelAttribute("board") BoardVO vo) {
		// 1. 이 메소드가 호출될 때 세션에 board라는 이름으로 저장된 데이터가 있는지 확인, 있다면 vo에 할당
		//그리고 사용자가 입력한 파라미터 값을 vo 객체에 할당
		System.out.println("번호 : "+vo.getSeq());
		System.out.println("제목 : "+vo.getTitle());
		System.out.println("작성자 이름 : "+vo.getWriter());//수정할 작성자 이름을 확인
		System.out.println("내용 : "+vo.getContent());
		System.out.println("등록일 : "+vo.getRegDate());
		System.out.println("조회수 : "+vo.getCnt());
		boardService.updateBoard(vo);
		return "getBoardList.do";	
	}
	
	//글 삭제
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo) {
		boardService.deleteBoard(vo);
		return "getBoardList.do";
	}
	
	//글 상세 조회
	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {
		model.addAttribute("board", boardService.getBoard(vo));//Model 정보 저장
		return "getBoard.jsp";
	}
	
	//글 목록 검색
	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		//null check 상세화면에서 글목록갈 때 or 로그인 후 getBoardList.do 요청 전달될 때 
		if(vo.getSearchCondition()==null) vo.setSearchCondition("TITLE");
		if(vo.getSearchKeyword()==null)	vo.setSearchKeyword("");
		
		model.addAttribute("boardList", boardService.getBoardList(vo));//Model 정보 저장
		return "getBoardList.jsp";
	}
}
