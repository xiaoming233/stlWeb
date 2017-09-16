package com.ljm.entity;

/**
 * 经纬度实体
 * @author ljm
 *
 */
public class Point {
	/**
	 *地名
	 */
	 String label;
	/**
	 * 经度
	 */
	String lng;
	/**
	 * 纬度
	 */
	String lat;
	public Point() {
		// TODO Auto-generated constructor stub
	}
	public Point(String label,String lng,String lat) {
		// TODO Auto-generated constructor stub
		super(); 
		this.label=label;
		this.lng=lng;
		this.lat=lat;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public Point(String [] str)
	{
		this.label=str[0];
		this.lat=str[1];
		this.lng=str[2];
	}
}
