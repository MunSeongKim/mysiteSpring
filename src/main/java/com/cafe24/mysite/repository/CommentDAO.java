package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.dto.CommentDTO;
import com.cafe24.mysite.vo.CommentVO;

@Repository
public class CommentDAO {
    public List<CommentDTO> readAll( Long boardNo ) {
	List<CommentDTO> list = new ArrayList<CommentDTO>();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    conn = getConnection();
	    String sql = "   SELECT c.no," + "	    c.content,"
		    + "          DATE_FORMAT(c.reg_date, '%Y-%m-%d %h:%i:%s')," + "          c.user_no,"
		    + "          c.board_no," + "          u.name" + "     FROM comment c, users u"
		    + "    WHERE c.user_no = u.no" + "      AND board_no = ?" + " ORDER BY c.reg_date DESC";

	    pstmt = conn.prepareStatement( sql );
	    pstmt.setLong( 1, boardNo );
	    rs = pstmt.executeQuery();

	    while ( rs.next() ) {
		CommentVO vo = new CommentVO();
		vo.setNo( rs.getLong( 1 ) );
		vo.setContent( rs.getString( 2 ) );
		vo.setRegDate( rs.getString( 3 ) );
		vo.setUserNo( rs.getLong( 4 ) );
		vo.setBoardNo( rs.getLong( 5 ) );

		String userName = rs.getString( 6 );

		CommentDTO dto = new CommentDTO();
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

    public boolean create( CommentVO vo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = " INSERT INTO comment" + 
	    		 " values (null, ?, now(), ?, ?)";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setString( 1, vo.getContent() );
	    pstmt.setLong( 2, vo.getUserNo() );
	    pstmt.setLong( 3, vo.getBoardNo() );

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
	    String sql = "DELETE FROM comment where no = ?";
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
