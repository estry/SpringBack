package com.ssafy.happyhouse.model;

public class StoreInfoDto {
	private int no;
	private String storeName;
	private String locName;
	private String smallCategoryCode;
	private String smallCategoryName;
	private String dongCode;
	private String dongName;
	private String address;
	private String roadName;
	private String roadAddress;
	private int PostNo;
	private double lng;
	private double lat;
	private String storeCode;
	private String date;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getSmallCategoryCode() {
		return smallCategoryCode;
	}

	public void setSmallCategoryCode(String smallCategoryCode) {
		this.smallCategoryCode = smallCategoryCode;
	}

	public String getSmallCategoryName() {
		return smallCategoryName;
	}

	public void setSmallCategoryName(String smallCategoryName) {
		this.smallCategoryName = smallCategoryName;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getDongName() {
		return dongName;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoadName() {
		return roadName;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public String getRoadAddress() {
		return roadAddress;
	}

	public void setRoadAddress(String roadAddress) {
		this.roadAddress = roadAddress;
	}

	public int getPostNo() {
		return PostNo;
	}

	public void setPostNo(int postNo) {
		PostNo = postNo;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "StoreInfoDto [no=" + no + ", storeName=" + storeName + ", locName=" + locName + ", smallCategoryCode="
				+ smallCategoryCode + ", smallCategoryName=" + smallCategoryName + ", dongCode=" + dongCode
				+ ", dongName=" + dongName + ", address=" + address + ", roadName=" + roadName + ", roadAddress="
				+ roadAddress + ", PostNo=" + PostNo + ", lng=" + lng + ", lat=" + lat + ", storeCode=" + storeCode
				+ ", date=" + date + "]";
	}

}
