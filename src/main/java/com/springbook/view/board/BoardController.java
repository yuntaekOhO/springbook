package com.springbook.view.board;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.impl.BoardDAO;

@Controller
@SessionAttributes("board") //���� �۾� ó���� �� ���� - 
//1. writer null�� ������Ʈ ����
public class BoardController {
	
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
	public String insertBoard(BoardVO vo, BoardDAO boardDAO) {
		boardDAO.insertBoard(vo);
		return "getBoardList.do";
	}
	
	//�� ����
	@RequestMapping("/updateBoard.do") //������ �ۼ��� �̸��� Ȯ��
	public String updateBoard(@ModelAttribute("board") BoardVO vo, BoardDAO boardDAO) {
		// 1. �� �޼ҵ尡 ȣ��� �� ���ǿ� board��� �̸����� ����� �����Ͱ� �ִ��� Ȯ��, �ִٸ� vo�� �Ҵ�
		//�׸��� ����ڰ� �Է��� �Ķ���� ���� vo ��ü�� �Ҵ�
		System.out.println("��ȣ : "+vo.getSeq());
		System.out.println("���� : "+vo.getTitle());
		System.out.println("�ۼ��� �̸� : "+vo.getWriter());//������ �ۼ��� �̸��� Ȯ��
		System.out.println("���� : "+vo.getContent());
		System.out.println("����� : "+vo.getRegDate());
		System.out.println("��ȸ�� : "+vo.getCnt());
		boardDAO.updateBoard(vo);
		return "getBoardList.do";	
	}
	
	//�� ����
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO vo, BoardDAO boardDAO) {
		boardDAO.deleteBoard(vo);
		return "getBoardList.do";
	}
	
	//�� �� ��ȸ
	@RequestMapping("/getBoard.do")
	public String getBoard(BoardVO vo, BoardDAO boardDAO, Model model) {
		model.addAttribute("board", boardDAO.getBoard(vo));//Model ���� ����
		return "getBoard.jsp";
	}
	
	//�� ��� �˻�
	/*@RequestMapping("/getBoardList.do")
	public String getBoardList(@RequestParam(value="searchCondition", defaultValue="TITLE", required=false) String condition, 
								@RequestParam(value="searchKeyword", defaultValue="", required=false) String keyword,
								 BoardDAO boardDAO, Model model)*/
	@RequestMapping("/getBoardList.do")
	public String getBoardList(BoardVO vo,BoardDAO boardDAO, Model model) {
		//System.out.println("�˻� ���� : " + condition);
		//System.out.println("�˻� �ܾ� : " + keyword);
		model.addAttribute("boardList", boardDAO.getBoardList(vo));//Model ���� ����
		return "getBoardList.jsp";
	}
}
