package com.cafe24.mysite.vo;

public class BoardVO {
    private Long no;
    private String title;
    private String content;
    private Long hitCount;
    private String regDate;
    private Long groupNo;
    private Long orderNo;
    private Long depth;
    private Long userNo;

    public Long getNo() {
	return no;
    }

    public void setNo( Long no ) {
	this.no = no;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle( String title ) {
	this.title = title;
    }

    public String getContent() {
	return content;
    }

    public void setContent( String content ) {
	this.content = content;
    }

    public Long getHitCount() {
	return hitCount;
    }

    public void setHitCount( Long hitCount ) {
	this.hitCount = hitCount;
    }

    public String getRegDate() {
	return regDate;
    }

    public void setRegDate( String regDate ) {
	this.regDate = regDate;
    }

    public Long getGroupNo() {
	return groupNo;
    }

    public void setGroupNo( Long groupNo ) {
	this.groupNo = groupNo;
    }

    public Long getOrderNo() {
	return orderNo;
    }

    public void setOrderNo( Long orderNo ) {
	this.orderNo = orderNo;
    }

    public Long getDepth() {
	return depth;
    }

    public void setDepth( Long depth ) {
	this.depth = depth;
    }

    public Long getUserNo() {
	return userNo;
    }

    public void setUserNo( Long userNo ) {
	this.userNo = userNo;
    }

    @Override
    public String toString() {
	return "BoardVO [no=" + no + ", title=" + title + ", content=" + content + ", hitCount=" + hitCount
		+ ", regDate=" + regDate + ", groupNo=" + groupNo + ", orderNo=" + orderNo + ", depth=" + depth
		+ ", userNo=" + userNo + "]";
    }

}
