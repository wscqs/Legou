package com.cqs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.cqs.entity.Shop;

public class Goods implements Parcelable {

	private static final long serialVersionUID = 1L;
	private String id;//商品ID
	private String categoryId;//分类ID
	private String shopId;//商家ID
	private String cityId;//城市ID
	private String title;//商品名称
	private String sortTitle;//商品描述信息
	private String imgUrl;//商品的图片
	private String startTime;//开始时间
	private String value;//商品原价
	private String price;//商品销售价
	private String ribat;//商品折扣
	private String bought;
	private String maxQuota;
	private String post;
	private String soldOut;
	private String tip;
	private String endTime;//结束时间
	private String detail;//描述详情
	private boolean isRefund;
	private boolean isOverTime;
	private String minquota;
	private String distance;
	private Shop shop;//所属商家
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSortTitle() {
		return sortTitle;
	}
	public void setSortTitle(String sortTitle) {
		this.sortTitle = sortTitle;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getRibat() {
		return ribat;
	}
	public void setRibat(String ribat) {
		this.ribat = ribat;
	}
	public String getBought() {
		return bought;
	}
	public void setBought(String bought) {
		this.bought = bought;
	}
	public String getMaxQuota() {
		return maxQuota;
	}
	public void setMaxQuota(String maxQuota) {
		this.maxQuota = maxQuota;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getSoldOut() {
		return soldOut;
	}
	public void setSoldOut(String soldOut) {
		this.soldOut = soldOut;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public boolean isRefund() {
		return isRefund;
	}
	public void setRefund(boolean isRefund) {
		this.isRefund = isRefund;
	}
	public boolean isOverTime() {
		return isOverTime;
	}
	public void setOverTime(boolean isOverTime) {
		this.isOverTime = isOverTime;
	}
	public String getMinquota() {
		return minquota;
	}
	public void setMinquota(String minquota) {
		this.minquota = minquota;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.categoryId);
		dest.writeString(this.shopId);
		dest.writeString(this.cityId);
		dest.writeString(this.title);
		dest.writeString(this.sortTitle);
		dest.writeString(this.imgUrl);
		dest.writeString(this.startTime);
		dest.writeString(this.value);
		dest.writeString(this.price);
		dest.writeString(this.ribat);
		dest.writeString(this.bought);
		dest.writeString(this.maxQuota);
		dest.writeString(this.post);
		dest.writeString(this.soldOut);
		dest.writeString(this.tip);
		dest.writeString(this.endTime);
		dest.writeString(this.detail);
		dest.writeByte(isRefund ? (byte) 1 : (byte) 0);
		dest.writeByte(isOverTime ? (byte) 1 : (byte) 0);
		dest.writeString(this.minquota);
		dest.writeString(this.distance);
		dest.writeParcelable(this.shop, flags);
	}

	public Goods() {
	}

	protected Goods(Parcel in) {
		this.id = in.readString();
		this.categoryId = in.readString();
		this.shopId = in.readString();
		this.cityId = in.readString();
		this.title = in.readString();
		this.sortTitle = in.readString();
		this.imgUrl = in.readString();
		this.startTime = in.readString();
		this.value = in.readString();
		this.price = in.readString();
		this.ribat = in.readString();
		this.bought = in.readString();
		this.maxQuota = in.readString();
		this.post = in.readString();
		this.soldOut = in.readString();
		this.tip = in.readString();
		this.endTime = in.readString();
		this.detail = in.readString();
		this.isRefund = in.readByte() != 0;
		this.isOverTime = in.readByte() != 0;
		this.minquota = in.readString();
		this.distance = in.readString();
		this.shop = in.readParcelable(Shop.class.getClassLoader());
	}

	public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>() {
		public Goods createFromParcel(Parcel source) {
			return new Goods(source);
		}

		public Goods[] newArray(int size) {
			return new Goods[size];
		}
	};
}
