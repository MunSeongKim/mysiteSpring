package com.cafe24.mysite.dto;

import com.cafe24.mysite.vo.BoardVO;

public class BoardDTO {
    private BoardVO vo;
    private String userName;

    public BoardVO getVo() {
	return vo;
    }

    public void setVo( BoardVO vo ) {
	this.vo = vo;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName( String userName ) {
	this.userName = userName;
    }

    @Override
    public String toString() {
	return "BoardDTO [vo=" + vo + ", userName=" + userName + "]";
    }

}
