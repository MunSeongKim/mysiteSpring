package com.cafe24.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.cafe24.mysite.exception.UserDAOException;
import com.cafe24.mysite.vo.UserVO;

@Repository
public class UserDAO {
    public boolean create( UserVO vo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    String sql = "INSERT INTO users VALUES(null, ?, ?, PASSWORD(?), ?, now())";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setString( 1, vo.getName() );
	    pstmt.setString( 2, vo.getEmail() );
	    pstmt.setString( 3, vo.getPassword() );
	    pstmt.setString( 4, vo.getGender() );

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

    public UserVO read( String email, String password ) throws UserDAOException {
	UserVO vo = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    conn = getConnection();
	    String sql = "SELECT no, name FROM users WHERE email=? AND password=PASSWORD(?)";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setString( 1, email );
	    pstmt.setString( 2, password );

	    rs = pstmt.executeQuery();

	    if ( rs.next() ) {
		vo = new UserVO();
		vo.setNo( rs.getLong( 1 ) );
		vo.setName( rs.getString( 2 ) );
	    }

	} catch ( SQLException e ) {
	    throw new UserDAOException();
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

    public UserVO read( Long no ) {
	UserVO vo = null;
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	try {
	    conn = getConnection();
	    String sql = "SELECT no, name, email, gender FROM users WHERE no=?";
	    pstmt = conn.prepareStatement( sql );

	    pstmt.setLong( 1, no );

	    rs = pstmt.executeQuery();

	    if ( rs.next() ) {
		vo = new UserVO();
		vo.setNo( rs.getLong( 1 ) );
		vo.setName( rs.getString( 2 ) );
		vo.setEmail( rs.getString( 3 ) );
		vo.setGender( rs.getString( 4 ) );
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

    public boolean update( UserVO vo ) {
	boolean result = false;
	Connection conn = null;
	PreparedStatement pstmt = null;

	try {
	    conn = getConnection();
	    if( vo.getPassword() == "" ){
		String sql = "UPDATE users SET name=?, gender=? WHERE no=?";
		pstmt = conn.prepareStatement( sql );
		pstmt.setString( 1, vo.getName() );
		pstmt.setString( 2, vo.getGender() );
		pstmt.setLong( 3, vo.getNo() );
	    } else {
		String sql = "UPDATE users SET name=?, password=PASSWORD(?), gender=? WHERE no=?";
		pstmt = conn.prepareStatement( sql );
		pstmt.setString( 1, vo.getName() );
		pstmt.setString( 2, vo.getPassword() );
		pstmt.setString( 3, vo.getGender() );
		pstmt.setLong( 4, vo.getNo() );
	    }

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
