package project.employee.management;

import java.util.List;
import java.util.Scanner;

public interface InterEmployeeMngCtrl {

	// 부서등록
	void registerDept(Scanner sc);
	
	//사원등록
	void registerEmployee(Scanner sc);
	
	// 중복 아이디 검사하기
	boolean isUseID(String id);
	
	// 로그인 하기
	EmployeeDTO login(Scanner sc);
	
	/////////////////////////////////////////////////////////////////////
	
	// 로그인된 후 사원관리 메뉴 
	void employeeMenu(Scanner sc, EmployeeDTO loginEmp);
	
	// 사원관리 메뉴 중 2번 내정보 변경하기 메서드 
	EmployeeDTO updateMyInfo(EmployeeDTO loginEmp, Scanner sc);
	
	// 사원관리 메뉴 중 3번 모든 사원 정보보기 메서드
	void showAllEmployee();
	
	// 사원관리 메뉴 중 사원사직시키기 메서드 (사장님으로 로그인 했을때만 가능하도록.) 
	void deleteEmployee(EmployeeDTO loginEmp, Scanner sc);
	
	//////////////////////////////////////////////////////////////////////
	
	// 사원관리 메뉴 중 4번 사원검색 메뉴 
	void searchEmployeeMenu(Scanner sc, EmployeeDTO loginEmp);
	
	// 사원 검색 결과 출력 메서드
	void printEmployee(String title, List<EmployeeDTO> empList);
	
	// 사원검색 메뉴 중 사원명 검색 메서드
	void searchEmployeeByName(Scanner sc, List<EmployeeDTO> emplList);

	// 사원검색 메뉴 중 연령대 검색 메서드
	void searchEmployeeByAge(Scanner sc, List<EmployeeDTO> emplList);

	// 사원검색 메뉴 중 직급 검색 메서드
	void searchEmployeeByPosition(Scanner sc, List<EmployeeDTO> emplList);

	// 사원검색 메뉴 중 급여범위 검색 메서드
	void searchEmployeeBySalary(Scanner sc, List<EmployeeDTO> emplList);
	
	// 사원검색 메뉴 중 부서명으로 검색 메서드
	void searchEmployeeByDname(Scanner sc, List<EmployeeDTO> emplList);
	
}
