package test.interfaceuse;

public class Adeveloper implements InterDeveloper {

	@Override
	public void repeatName(String name, int cnt) {

		for (int i = 0; i < cnt; i++) {
			System.out.println((i+1) + "." + name);
		}
	}

}
