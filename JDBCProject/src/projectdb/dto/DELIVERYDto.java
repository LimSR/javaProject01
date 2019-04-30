package projectdb.dto;

import java.sql.Date;

public class DELIVERYDto {
	private int dcode;
	private String dcompanyname;
	private String dcompanyaddr;
	private Date contractenddate;

	public DELIVERYDto() {
		// TODO 磊悼 积己等 积己磊 胶庞
	}

	public DELIVERYDto(int dcode, String dcompanyname, String dcompanyaddr, Date contractenddate) {
		this.dcode = dcode;
		this.dcompanyname = dcompanyname;
		this.dcompanyaddr = dcompanyaddr;
		this.contractenddate = contractenddate;
	}

	public DELIVERYDto(int dcode, String dcompanyname) {
		this.dcode = dcode;
		this.dcompanyname = dcompanyname;
	}

	public int getDcode() {
		return dcode;
	}

	public void setDcode(int dcode) {
		this.dcode = dcode;
	}

	public String getDcompanyname() {
		return dcompanyname;
	}

	public void setDcompanyname(String dcompanyname) {
		this.dcompanyname = dcompanyname;
	}

	public String getDcompanyaddr() {
		return dcompanyaddr;
	}

	public void setDcompanyaddr(String dcompanyaddr) {
		this.dcompanyaddr = dcompanyaddr;
	}

	public Date getContractenddate() {
		return contractenddate;
	}

	public void setContractenddate(Date contractenddate) {
		this.contractenddate = contractenddate;
	}

}
