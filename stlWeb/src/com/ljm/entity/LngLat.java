package com.ljm.entity;

/**
 * 经纬度实体
 * @author ljm
 *
 */
public class LngLat {
	/**
	 *地名
	 */
	 String name;
	/**
	 * 经度
	 */
	String lng;
	/**
	 * 纬度
	 */
	String lat;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public LngLat(String [] str)
	{
		this.name=str[0];
		this.lat=str[1];
		this.lng=str[2];
	}
}
