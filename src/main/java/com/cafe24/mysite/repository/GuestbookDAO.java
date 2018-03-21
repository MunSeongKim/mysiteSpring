package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.GuestbookVO;

@Repository
public class GuestbookDAO {
    public List<GuestbookVO> readAll() {
	List<GuestbookVO> list = new ArrayList<GuestbookVO>();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
	    conn = getConnection();
	    String sql = "SELECT no, name, content, DATE_FORMAT(reg_date, '%Y-%m-%d %h:%i:%s') FROM guestbook ORDER BY no DESC";
	    pstmt = conn.prepareStatement( sql );
	    rs = pstmt.executeQuery();

	    while ( rs.next() ) {
		GuestbookVO vo = new GuestbookVO();
		vo.setNo( rs.getLong( 1 ) );
		vo.setName( rs.getString( 2 ) );
		vo.setContent( rs.getString( 3 ) );
		vo.setRegDate( rs.getString( 4 ) );

		list.add( vo );
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

    public boolean create( GuestbookVO vo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "INSERT INTO guestbook VALUES(null, ?, PASSWORD(?), ?, now())";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setString( 1, vo.getName() );
	    pstmt.setString( 2, vo.getPassword() );
	    pstmt.setString( 3, vo.getContent() );

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

    public boolean delete( GuestbookVO vo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "DELETE FROM guestbook where no = ? AND password = PASSWORD(?)";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setLong( 1, vo.getNo() );
	    pstmt.setString( 2, vo.getPassword() );

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
