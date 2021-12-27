package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

//import com.ssafy.util.PageNavigation;
import com.ssafy.happyhouse.model.BoardDto;
import com.ssafy.happyhouse.model.BoardParameterDto;
import com.ssafy.happyhouse.model.mapper.BoardMapper;
import com.ssafy.happyhouse.model.mapper.HouseMapMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeArticle(BoardDto boardDto) throws Exception {
		if(boardDto.getSubject() == null || boardDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).writeArticle(boardDto) == 1;
	}

	@Override
//	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception {
	public List<BoardDto> listArticle() throws Exception {
//		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
//		boardParameterDto.setStart(start);
//		return sqlSession.getMapper(BoardMapper.class).listArticle(boardParameterDto);
		return sqlSession.getMapper(BoardMapper.class).listArticle();
		
	}

	/*
	 * @Override public PageNavigation makePageNavigation(BoardParameterDto
	 * boardParameterDto) throws Exception { int naviSize = 5; PageNavigation
	 * pageNavigation = new PageNavigation();
	 * pageNavigation.setCurrentPage(boardParameterDto.getPg());
	 * pageNavigation.setNaviSize(naviSize); int totalCount =
	 * sqlSession.getMapper(BoardMapper.class).getTotalCount(boardParameterDto);//
	 * 총글갯수 269 pageNavigation.setTotalCount(totalCount); int totalPageCount =
	 * (totalCount - 1) / boardParameterDto.getSpp() + 1;//27
	 * pageNavigation.setTotalPageCount(totalPageCount); boolean startRange =
	 * boardParameterDto.getPg() <= naviSize;
	 * pageNavigation.setStartRange(startRange); boolean endRange = (totalPageCount
	 * - 1) / naviSize * naviSize < boardParameterDto.getPg();
	 * pageNavigation.setEndRange(endRange); pageNavigation.makeNavigator(); return
	 * pageNavigation; }
	 */

	@Override
	public BoardDto getArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getArticle(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyArticle(BoardDto boardDto) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyArticle(boardDto) == 1;
	}

	@Override
	@Transactional
	public int deleteArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).deleteArticle(articleno);
	}
}