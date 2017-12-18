package com.cherkashyn.vitalii.market.domain;

public class Point {
	Integer id;
	Integer pointnum;
	Integer active=1;
	Integer positionX;
	Integer positionY;
	String html;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPointnum() {
		return pointnum;
	}
	public void setPointnum(Integer pointnum) {
		this.pointnum = pointnum;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	public Integer getPositionX() {
		return positionX;
	}
	public void setPositionX(Integer positionX) {
		this.positionX = positionX;
	}
	public Integer getPositionY() {
		return positionY;
	}
	public void setPositionY(Integer positionY) {
		this.positionY = positionY;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	
}
