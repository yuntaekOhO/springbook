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
@SessionAttributes("board") //���� �۾� ó���� �� ���� - 
//1. writer null�� ������Ʈ ����
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//�� ��� ��ȯ ó�� JSON , getBoardList()�� ����
	@RequestMapping("/dataTransform.do")
	@ResponseBody //�ڹ� ��ü�� http ���� ���������� ��ü�� ��ȯ�ϱ� ���� ���
	public List<BoardVO> dataTransform(BoardVO vo){
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		return boardList;
	}
	
	//�˻� ���� ��� ����
	@ModelAttribute("conditionMap") //View(JSP)���� ����� �����͸� ������ �뵵
	// or Command ��ü(UserVO) �̸� ����
	//@ModelAttribute �޼ҵ� ���� ����� ���ϵ� ��ü�� �ڵ����� Model�� ����ȴ�. = ���ϵ� ��ü�� View ���������� ����� �� �ִ�
	//@ModelAttribute �� @RequestMapping ���� ���� ȣ�� �ȴ� 
	public Map<String, String> searchConditionMap() {
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("����", "TITLE");
		conditionMap.put("����", "CONTENT");
		return conditionMap;
	}
	
	//�� ���
	@RequestMapping("/insertBoard.do")
	public String insertBoard(BoardVO vo) throws IOException {
		//���� ���ε� ó��
		MultipartFile uploadFile = vo.getUploadFile();
		if(!uploadFile.isEmpty()) {
			String fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("D:/" + fileName));
		}
		boardService.insertBoard(vo);
		return "getBoardList.do";
	}
	
	//�� ����
	@RequestMapping("/updateBoard.do") //������ �ۼ��� �̸��� Ȯ��
	public String updateBoard(@ModelAttribute("board") BoardVO vo) {
		// 1. �� �޼ҵ尡 ȣ��� �� ���ǿ� board��� �̸����� ����� �����Ͱ� �ִ��� Ȯ��, �ִٸ� vo�� �Ҵ�
		//�׸��� ����ڰ� �Է��� �Ķ���� ���� vo ��ü�� �Ҵ�
		System.out.println("��ȣ : "+vo.getSeq());
		System.out.println("���� : "+vo.getTitle());
		System.out.println("�ۼ��� �̸� : "+vo.getWriter());//������ �ۼ��� �̸��� Ȯ��
		System.out.println("���� : "+vo.getContent());
		System.out.println("����� : "+vo.getRegDate());
		System.out.println("��ȸ�� : "+vo.getCnt());
		boardService.updateBoard(vo);
		return "getBoardList.do";	
	}
	
	//�� ����
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo) {
		boardService.deleteBoard(vo);
		return "getBoardList.do";
	}
	
	//�� �� ��ȸ
	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {
		model.addAttribute("board", boardService.getBoard(vo));//Model ���� ����
		return "getBoard.jsp";
	}
	
	//�� ��� �˻�
	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		//null check ��ȭ�鿡�� �۸�ϰ� �� or �α��� �� getBoardList.do ��û ���޵� �� 
		if(vo.getSearchCondition()==null) vo.setSearchCondition("TITLE");
		if(vo.getSearchKeyword()==null)	vo.setSearchKeyword("");
		
		model.addAttribute("boardList", boardService.getBoardList(vo));//Model ���� ����
		return "getBoardList.jsp";
	}
}
