package project;

public class Butcher_Main {
	private Butcher_First_Frame first_Frame;

	public static void main(String[] args) {
		// TODO �ڵ� ������ �޼ҵ� ����
		Butcher_Main bm = new Butcher_Main();
		bm.first_Frame = new Butcher_First_Frame();
		bm.first_Frame.setMain(bm);
	}
}
