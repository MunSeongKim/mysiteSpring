package com.cafe24.mysite.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cafe24.mysite.repository.BoardDAO;

@Component
public class PagerUtil {    
    @Autowired
    private BoardDAO dao;
    
    public Pager setUpPager( Pager pager, int pageNo, String keyword ) {
	int count = dao.readCount( keyword );
		
	pager = setCurrentPageNumber( pager, pageNo );
	pager = setStartPostNumber( pager, count );
	pager = setTotalPageCount( pager, count );
	pager = setNavigator( pager );
	return pager;
    }

    private Pager setCurrentPageNumber( Pager pager, int pageNo ) {
	int pageCount = pager.getPageCount();

	int diff = ((pageNo - 1) % pageCount);

	pager.setStartPageNumber( pageNo - diff );
	pager.setEndPageNumber( pageNo + (pageCount - diff - 1) );

	pager.setCurrentPageNumber( pageNo );
	return pager;
    }
    
    private Pager setStartPostNumber( Pager pager, int count ) {
	int currentPage = pager.getCurrentPageNumber();
	int postCount = pager.getPostCount();

	pager.setStartPostNumber( count - ((currentPage - 1) * postCount) );
	return pager;
    }

    private static Pager setTotalPageCount( Pager pager, int count ) {
	int postCount = pager.getPostCount();

	if ( (count % postCount) == 0 ) {
	    pager.setTotalPageCount( count / postCount );
	    return pager;
	}
	pager.setTotalPageCount( (count / postCount) + 1 );
	return pager;
    }

    private Pager setNavigator( Pager pager ) {
	int startPageNumber = pager.getStartPageNumber();
	int endPageNumber = pager.getEndPageNumber();
	int totalPageCount = pager.getTotalPageCount();
	int pageCount = pager.getPageCount();

	pager.setLeftNavigator( false );
	pager.setRightNavigator( false );

	if ( endPageNumber < totalPageCount ) {
	    pager.setRightNavigator( true );
	}
	if ( startPageNumber > pageCount ) {
	    pager.setLeftNavigator( true );
	}

	return pager;
    }
}
