package com.cafe24.mysite.dto;

import com.cafe24.mysite.vo.CommentVO;

public class CommentDTO {
    private CommentVO vo;
    private String userName;

    public CommentVO getVo() {
	return vo;
    }

    public void setVo( CommentVO vo ) {
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
	return "CommentDTO [vo=" + vo + ", userName=" + userName + "]";
    }

}
