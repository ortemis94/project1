package project.employee.management;

import java.util.Scanner;

public class EmployeeMngMainExecution {



	public static void main(String[] args) {

		InterEmployeeMngCtrl ctrl = new EmployeeMngCtrl();

		Scanner sc = new Scanner(System.in);

		EmployeeDTO loginEmp = null;

		while (true) {
/*			if (loginEmp != null) {
				System.out.println(">>>> Main Menu [" + loginEmp.getName() + "님 로그인중..] <<<<");

			}else {
				System.out.println(">>>> Main Menu <<<<");
			}*/
			
			String login = loginEmp == null ? ">>>> Main Menu <<<<" :  ">>>> Main Menu [" + loginEmp.getName() + "님 로그인중..] <<<<" ;
			
			System.out.println(login);
			System.out.println("1.부서등록  2.사원등록  3.로그인  4.로그아웃  5.프로그램 종료");
			System.out.print("=> 메뉴번호 선택 : ");
			String choice = sc.nextLine();
			switch (choice) {
			case "1": // 부서등록
				ctrl.registerDept(sc);
				break;
			case "2": // 사원등록
				ctrl.registerEmployee(sc);
				break;
			case "3": // 로그인
				if (loginEmp != null) { // 이미 로그인 하여 loginEmp 객체에 데이터가 있다면 
					System.out.println("[" + loginEmp.getName() + "]님 이미 로그인 중입니다!!!\n");
				}else {					// loginEmp 객체가 null로 비어있다면
					loginEmp = ctrl.login(sc);		// login 메서드를 호출하여 리턴된 객체를 loginEmp에 저장하고 
					ctrl.employeeMenu(sc, loginEmp);// loginEmp를 전달하여 사원 메뉴 메서드를 호출함.
				}
				break;
			case "4": // 로그아웃
				System.out.println("== 로그아웃 되었습니다 ==");
				loginEmp = null; // 로그인 계정을 확인하는 loginEmp를 null로 바꾸어 비워준다.
				break;
			case "5": // 프로그램 종료
				System.out.println(">>> 프로그램을 종료합니다. <<<");
				System.exit(0); // return;으로 반복문을 빠져나가도 됨. 
				sc.close();
				break;
			default:
				System.out.println(">>> 메뉴에 없는 번호 입니다. 다시 선택하세요!!");
				break;
			}
			System.out.println();

		} // while문 end Do~While문으로도 가능.

	} // end of main()---------------------------

}
