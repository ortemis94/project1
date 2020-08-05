package test.interfaceuse;

public class TestMain {

	public static void main(String[] args) {

		InterDeveloper ad = new Bdeveloper(); // 다형성을 이용하여 Interface로 객체를 생성하면 객체를 바꿀 때 뒤의 생성자만 바꾸면 되므로 편리. 
		ad.repeatName("   ", 10);
	}

}
