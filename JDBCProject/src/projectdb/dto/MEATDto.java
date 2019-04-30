package projectdb.dto;

public class MEATDto {
	private String meatcode;
	private String meattype;
	private String meatarea;
	private String origin;
	private String grade;
	private int total_stock;
	private int price;

	public MEATDto() {
		// TODO 磊悼 积己等 积己磊 胶庞
	}

	public MEATDto(String meatcode, String meattype, String meatarea, String origin, String grade, int total_stock,
			int price) {
		this.meatcode = meatcode;
		this.meattype = meattype;
		this.meatarea = meatarea;
		this.origin = origin;
		this.grade = grade;
		this.total_stock = total_stock;
		this.price = price;
	}
	

	public MEATDto(String origin, String grade) {
		this.origin = origin;
		this.grade = grade;
	}

	public String getMeatcode() {
		return meatcode;
	}

	public void setMeatcode(String meatcode) {
		this.meatcode = meatcode;
	}

	public String getMeattype() {
		return meattype;
	}

	public void setMeattype(String meattype) {
		this.meattype = meattype;
	}

	public String getMeatarea() {
		return meatarea;
	}

	public void setMeatarea(String meatarea) {
		this.meatarea = meatarea;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getTotal_stock() {
		return total_stock;
	}

	public void setTotal_stock(int total_stock) {
		this.total_stock = total_stock;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
