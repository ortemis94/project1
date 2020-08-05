package test.interfaceuse;

public class Bdeveloper implements InterDeveloper{

	@Override
	public void repeatName(String name, int cnt) {

		// 유효성 검사
		if (name == null || name.trim().isEmpty()) {
			System.out.println("~~~ 성명은 공백이 안됩니다. 올바르게 입력하세요!!");
			return; // 종료
		}
		
		if (cnt < 1) {
			System.out.println("~~~ 반복횟수는 1이상 이어야 합니다.");
			return; // 종료
		}
		
		for (int i = 0; i < cnt; i++) {
			System.out.println((i+1) + "." + name);
		}		
	}

}
