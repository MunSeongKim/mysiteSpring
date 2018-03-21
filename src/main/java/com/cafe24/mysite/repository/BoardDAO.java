package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.component.Pager;
import com.cafe24.mysite.dto.BoardDTO;
import com.cafe24.mysite.vo.BoardVO;

@Repository
public class BoardDAO {
        
    public List<BoardDTO> readAll(String keyword, Pager pager) {
	List<BoardDTO> list = new ArrayList<BoardDTO>();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    conn = getConnection();
	    String sql = "   SELECT board.no," + 
	    		 "	    board.title," + 
	    		 "          board.content," + 
	    		 "          board.hit_count," + 
	    		 "          DATE_FORMAT(board.reg_date, '%Y-%m-%d %h:%i:%s')," +
	    		 "          board.group_no," + 
	    		 "          board.order_no," + 
	    		 "          board.depth," + 
	    		 "          board.user_no," +
	    		 "          users.name" + 
	    		 "     FROM board, users" + 
	    		 "    WHERE (board.user_no = users.no AND board.title LIKE '%" + keyword + "%')" +
	    		 "       OR (board.user_no = users.no AND board.content LIKE '%" + keyword + "%')" +
	    		 " ORDER BY group_no DESC, order_no ASC" + 
	    		 "    LIMIT ?, ?";
	    pstmt = conn.prepareStatement( sql );
	    pstmt.setInt( 1, (pager.getCurrentPageNumber() - 1) * pager.getPostCount() );
	    pstmt.setInt( 2, pager.getPostCount() );
	    rs = pstmt.executeQuery();
	    
	    while ( rs.next() ) {
		BoardVO vo = new BoardVO();
		vo.setNo( rs.getLong( 1 ) );
		vo.setTitle( rs.getString( 2 ) );
		vo.setContent( rs.getString( 3 ) );
		vo.setHitCount( rs.getLong( 4 ) );
		vo.setRegDate( rs.getString( 5 ) );
		vo.setGroupNo( rs.getLong( 6 ) );
		vo.setOrderNo( rs.getLong( 7 ) );
		vo.setDepth( rs.getLong( 8 ) );
		vo.setUserNo( rs.getLong( 9 ) );
		String userName = rs.getString( 10 );

		BoardDTO dto = new BoardDTO();
		dto.setVo( vo );
		dto.setUserName( userName );

		list.add( dto );
	    }
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( rs != null ) rs.close();
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}
	return list;
    }
    
    public BoardVO read( Long no ) {
	BoardVO vo = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    conn = getConnection();
	    String sql = "   SELECT board.no," + 
	    		 "	    board.title," + 
	    		 "          board.content," + 
	    		 "          board.hit_count," + 
	    		 "          DATE_FORMAT(board.reg_date, '%Y-%m-%d %h:%i:%s')," +
	    		 "          board.group_no," + 
	    		 "          board.order_no," + 
	    		 "          board.depth," + 
	    		 "          board.user_no" +
	    		 "     FROM board" + 
	    		 "    WHERE board.no = ?";
	    pstmt = conn.prepareStatement( sql );
	    pstmt.setLong( 1, no );
	    rs = pstmt.executeQuery();
	    
	    if ( rs.next() ) {
		vo = new BoardVO();
		vo.setNo( rs.getLong( 1 ) );
		vo.setTitle( rs.getString( 2 ) );
		vo.setContent( rs.getString( 3 ) );
		vo.setHitCount( rs.getLong( 4 ) );
		vo.setRegDate( rs.getString( 5 ) );
		vo.setGroupNo( rs.getLong( 6 ) );
		vo.setOrderNo( rs.getLong( 7 ) );
		vo.setDepth( rs.getLong( 8 ) );
		vo.setUserNo( rs.getLong( 9 ) );

	    }
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( rs != null ) rs.close();
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}
	return vo;
    }
    
    public BoardVO readAtLast( ) {
	BoardVO vo = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    conn = getConnection();
	    String sql = "   SELECT board.no," + 
	    		 "	    board.title," + 
	    		 "          board.content," + 
	    		 "          board.hit_count," + 
	    		 "          DATE_FORMAT(board.reg_date, '%Y-%m-%d %h:%i:%s')," + 
	    		 "          board.group_no," + 
	    		 "          board.order_no," + 
	    		 "          board.depth" + 
	    		 "     FROM board" +
	    		 " ORDER BY board.no DESC" +
	    		 " LIMIT 1";
	    pstmt = conn.prepareStatement( sql );
	    rs = pstmt.executeQuery();
	    
	    if ( rs.next() ) {
		vo = new BoardVO();
		vo.setNo( rs.getLong( 1 ) );
		vo.setTitle( rs.getString( 2 ) );
		vo.setContent( rs.getString( 3 ) );
		vo.setHitCount( rs.getLong( 4 ) );
		vo.setRegDate( rs.getString( 5 ) );
		vo.setGroupNo( rs.getLong( 6 ) );
		vo.setOrderNo( rs.getLong( 7 ) );
		vo.setDepth( rs.getLong( 8 ) );
	    }
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( rs != null ) rs.close();
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}
	return vo;
    }
    
    public int readCount( String keyword ) {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	int count = 0;
	
	try {
	    conn = getConnection();
	    String sql = "   SELECT COUNT(*)"
	    	       + "     FROM board"
	    	       + "    WHERE title LIKE '%" + keyword + "%'"
	    	       + "       OR content LIKE '%" + keyword + "%'";
	    pstmt = conn.prepareStatement( sql );
	    rs = pstmt.executeQuery();
	    
	    if ( rs.next() ) {
		count = rs.getInt(1);
	    }
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( rs != null ) rs.close();
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}
	return count;
    }

    public boolean create( BoardVO vo, Long userNo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = " INSERT INTO board" + 
	    		 " VALUES(null, ?, ?, 0, now()," + 
	    		 "       (SELECT IFNULL(MAX(group_no), 0)+1 FROM board as max_no)," + 
	    		 "        1, 0, ?)";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setString( 1, vo.getTitle() );
	    pstmt.setString( 2, vo.getContent() );
	    pstmt.setLong( 3, userNo );

	    int count = pstmt.executeUpdate();
	    result = (count == 1);
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }
    
    public boolean create( BoardVO vo, Long userNo, Long groupNo, Long orderNo, Long depth ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	update(groupNo, orderNo);
	
	try {
	    conn = getConnection();
	    String sql = " INSERT INTO board" + 
	    		 " VALUES(null, ?, ?, 0, now()," + 
	    		 "        ?, ?, ?, ?)";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setString( 1, vo.getTitle() );
	    pstmt.setString( 2, vo.getContent() );
	    pstmt.setLong( 3, groupNo );
	    pstmt.setLong( 4, orderNo );
	    pstmt.setLong( 5, depth );
	    pstmt.setLong( 6, userNo );

	    int count = pstmt.executeUpdate();
	    result = (count == 1);
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }
    
    public boolean update( BoardVO vo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = " UPDATE board" + 
	    		 "    SET title = ?, content = ?" + 
	    		 "  WHERE no = ?";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setString( 1, vo.getTitle() );
	    pstmt.setString( 2, vo.getContent() );
	    pstmt.setLong( 3, vo.getNo() );

	    int count = pstmt.executeUpdate();
	    result = (count == 1) ? true : false;
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }
    
    public boolean update( Long no ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = " UPDATE board" + 
	    		 "    SET hit_count = hit_count + 1" + 
	    		 "  WHERE no = ?";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setLong( 1, no );

	    int count = pstmt.executeUpdate();
	    result = (count == 1);
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }
    
    public boolean update( Long groupNo, Long orderNo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = " UPDATE board" + 
	    		 "    SET order_no = order_no + 1" + 
	    		 "  WHERE group_no = ? and order_no >= ?";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setLong( 1, groupNo );
	    pstmt.setLong( 2, orderNo );

	    int count = pstmt.executeUpdate();
	    result = (count == 1);
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }

    public boolean delete( Long no ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "DELETE FROM board where no = ?";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setLong( 1, no );

	    int count = pstmt.executeUpdate();
	    result = (count == 1);
	} catch ( SQLException e ) {
	    e.printStackTrace();
	} finally {
	    try {
		if ( pstmt != null ) pstmt.close();
		if ( conn != null ) conn.close();
	    } catch ( SQLException e ) {
		e.printStackTrace();
	    }
	}

	return result;
    }

    private Connection getConnection() throws SQLException {
	Connection conn = null;

	try {
	    Class.forName( "com.mysql.jdbc.Driver" );
	    String url = "jdbc:mysql://localhost:3306/webdb";
	    conn = DriverManager.getConnection( url, "webdb", "webdb" );
	} catch ( ClassNotFoundException e ) {
	    System.out.println( "Failed to load driver : " + e );
	}

	return conn;
    }
}
