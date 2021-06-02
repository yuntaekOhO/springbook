package com.springbook.biz.board.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.common.Log4jAdvice;
//import com.springbook.biz.common.BeforeAdvice;
//test
@Service("boardService") // <- Client가 요청할 id
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAOSpring boardDAO;
	
	/*@Autowired
	private BoardDAO boardDAO;*/
	
	@Override
	public void insertBoard(BoardVO vo) {
		/*if(vo.getSeq()==0) {
			throw new IllegalArgumentException("0번 글은 등록할 수 없습니다.");
		} //예외를 발생 시키는 코드*/
		boardDAO.insertBoard(vo);
		//boardDAO.insertBoard(vo); // exception 발생
	}

	@Override
	public void updateBoard(BoardVO vo) {
		boardDAO.updateBoard(vo);
	}

	@Override
	public void deleteBoard(BoardVO vo) {
		boardDAO.deleteBoard(vo);
	}

	@Override
	public BoardVO getBoard(BoardVO vo) {
		return boardDAO.getBoard(vo);
	}

	@Override
	public List<BoardVO> getBoardList(BoardVO vo) {
		return boardDAO.getBoardList(vo);
	}
	
	
}
