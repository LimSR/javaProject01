package projectdb.dto;

import java.sql.Date;

public class WARECHOUSINGDto {
	private String wcode;
	private String meatcode;
	private String meatamount;
	private Date arrival_time;
	private Date expiration_date;
	private int price;
	private int dcode;

	public WARECHOUSINGDto() {
		// TODO 磊悼 积己等 积己磊 胶庞
	}

	public WARECHOUSINGDto(String wcode, String meatcode, String meatamount, Date arrival_time, Date expiration_date,
			int price, int dcode) {
		this.wcode = wcode;
		this.meatcode = meatcode;
		this.meatamount = meatamount;
		this.arrival_time = arrival_time;
		this.expiration_date = expiration_date;
		this.price = price;
		this.dcode = dcode;
	}

	public String getWcode() {
		return wcode;
	}

	public void setWcode(String wcode) {
		this.wcode = wcode;
	}

	public String getMeatcode() {
		return meatcode;
	}

	public void setMeatcode(String meatcode) {
		this.meatcode = meatcode;
	}

	public String getMeatamount() {
		return meatamount;
	}

	public void setMeatamount(String meatamount) {
		this.meatamount = meatamount;
	}

	public Date getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(Date arrival_time) {
		this.arrival_time = arrival_time;
	}

	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDcode() {
		return dcode;
	}

	public void setDcode(int dcode) {
		this.dcode = dcode;
	}

}
