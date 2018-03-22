package com.cafe24.mysite.dto;

public class CommentListDTO {
    private Long no;
    private String content;
    private String regDate;
    private Long userNo;
    private Long boardNo;
    private String userName;

    public Long getNo() {
	return no;
    }

    public void setNo( Long no ) {
	this.no = no;
    }

    public String getContent() {
	return content;
    }

    public void setContent( String content ) {
	this.content = content;
    }

    public String getRegDate() {
	return regDate;
    }

    public void setRegDate( String regDate ) {
	this.regDate = regDate;
    }

    public Long getUserNo() {
	return userNo;
    }

    public void setUserNo( Long userNo ) {
	this.userNo = userNo;
    }

    public Long getBoardNo() {
	return boardNo;
    }

    public void setBoardNo( Long boardNo ) {
	this.boardNo = boardNo;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName( String userName ) {
	this.userName = userName;
    }

    @Override
    public String toString() {
	return "CommentListDTO [no=" + no + ", content=" + content + ", regDate=" + regDate + ", userNo=" + userNo
		+ ", boardNo=" + boardNo + ", userName=" + userName + "]";
    }

}
