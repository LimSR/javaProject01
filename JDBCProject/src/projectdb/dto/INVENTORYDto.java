package projectdb.dto;

public class INVENTORYDto {
	private String wcode;
	private int remainingamount;

	public INVENTORYDto() {
		// TODO �ڵ� ������ ������ ����
	}

	public INVENTORYDto(String wcode, int remainingamount) {
		super();
		this.wcode = wcode;
		this.remainingamount = remainingamount;
	}

	public String getWcode() {
		return wcode;
	}

	public void setWcode(String wcode) {
		this.wcode = wcode;
	}

	public int getRemainingamount() {
		return remainingamount;
	}

	public void setRemainingamount(int remainingamount) {
		this.remainingamount = remainingamount;
	}

}
