package projectdb.dto;

import java.sql.Date;

public class SALEDto {
	private String scode;
	private String wcode;
	private int salemount;
	private int sprice;
	private Date saledate;

	public SALEDto() {
		// TODO 磊悼 积己等 积己磊 胶庞
	}

	public SALEDto(String scode, String wcode, int salemount, int sprice, Date saledate) {
		super();
		this.scode = scode;
		this.wcode = wcode;
		this.salemount = salemount;
		this.sprice = sprice;
		this.saledate = saledate;
	}

	public String getScode() {
		return scode;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getWcode() {
		return wcode;
	}

	public void setWcode(String wcode) {
		this.wcode = wcode;
	}

	public int getSalemount() {
		return salemount;
	}

	public void setSalemount(int salemount) {
		this.salemount = salemount;
	}

	public int getSprice() {
		return sprice;
	}

	public void setSprice(int sprice) {
		this.sprice = sprice;
	}

	public Date getSaledate() {
		return saledate;
	}

	public void setSaledate(Date saledate) {
		this.saledate = saledate;
	}

}
