package com.cherkashyn.vitalii.market.domain;

public class Point2Commodity {

	private Integer id;
	private int idPoint;
	private int idCommodity;
	private Float percent;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getIdPoint() {
		return idPoint;
	}
	public void setIdPoint(int idPoint) {
		this.idPoint = idPoint;
	}
	public int getIdCommodity() {
		return idCommodity;
	}
	public void setIdCommodity(int idCommodity) {
		this.idCommodity = idCommodity;
	}
	public Float getPercent() {
		return percent;
	}
	public void setPercent(Float percent) {
		this.percent = percent;
	}
	
}
