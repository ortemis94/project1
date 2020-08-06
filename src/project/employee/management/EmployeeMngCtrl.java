package project.employee.management;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

public class EmployeeMngCtrl implements InterEmployeeMngCtrl {

	private final String DEPTLISTFILENAME = "C:/iotestdata/project/employeemng/deptlist.dat";

	private final String EMPLLISTFILENAME = "C:/iotestdata/project/employeemng/employeelist.dat";

	private EmpMngSerializable serial = new EmpMngSerializable();

	// 부서 등록 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void registerDept(Scanner sc) {
		DeptDTO deptDto = new DeptDTO();

		System.out.println("\n== 부서등록하기 ==");

		/*
			 nextInt()의 입력되는 데이터의 종결자는 공백 또는 엔터이다. 
			 nextInt()는 입력된 값의 앞부분에 공백이 있으면 공백을 무시하고 종결자(공백 또는 엔터) 전까지의 int타입 데이터만 읽어들여서 
			  해당 변수에 넣어주고 종결자를 포함한 나머지 데이터는 버퍼에서 삭제되지 않고 그대로 남아있게 된다. 
		 */
		/*
			 nextLine()은 버퍼에서 종결자(엔터)를 포함한 모든 String타입(공백 포함) 데이터를 읽어들여서 종결자(엔터)전까지의 데이터를 해당 변수에 넣어주고
			 종결자(엔터)는 버퍼에서 삭제해준다. 
		 */

		// 유효성 검사
		do {
			System.out.print("▶  부서번호: "); // 10  20  30
			String deptNo = sc.nextLine();

			try {
				int nDeptNo = Integer.parseInt(deptNo); // String형으로nextLine()을 사용한 후 int로 변환하는 것이 아닌
				deptDto.setDeptNo(nDeptNo); // 바로nextInt()를 이용하여 저장하면 엔터(\n)값이 처리가 되지 않아서 무한으로 catch문이 반복되기 때문에
				break;						// catch문에 nextLine()을 한번 더 기입하여 버퍼에 남은 엔터(\n)값을 처리해주어야 한다. 
			} catch (Exception e) { // Integer형으로 변환이 안되는 값 예외처리
				System.out.println("~~~~ 오류 : 부서번호는 숫자로만 입력하세요!!!\n");
			}

		} while(true);

		System.out.print("▶  부서명: "); 	// 관리부 연구부 생산부
		deptDto.setDeptName(sc.nextLine());

		System.out.print("▶  부서위치: ");	// 서울 인천 수원
		deptDto.setDeptLoc(sc.nextLine());

		File file = new File(DEPTLISTFILENAME);	
		// DEPTLISTFILENAME 에 해당하는 파일 객체 생성하기. 

		Map<String, DeptDTO> deptMap = null;
		int n = 0;

		if (!file.exists()) {	// 파일이 존재하지 않는 경우. 즉, 최초로 부서등록을 하는 경우.
			deptMap = new HashMap<>();
			deptMap.put(String.valueOf(deptDto.getDeptNo()), deptDto);

			// DEPTLISTFILENAME 파일에 deptDto 객체를 저장시킨다.
			n = serial.objectToFileSave(deptMap, DEPTLISTFILENAME);
		}else {					// 파일이 존재하는 경우. 적어도 두번째로 부서를 등록하는 경우. 
			// DEPTLISTFILENAME 파일에 저장된 객체를 불러온다. 
			Object deptMapObj = serial.getobjectFromFile(DEPTLISTFILENAME); // 기존 파일에 있는 객체 불러옴.
			deptMap = (HashMap<String, DeptDTO>)deptMapObj;					// 해당 객체를 실제 형식인 HashMap으로 형변환
			deptMap.put(String.valueOf(deptDto.getDeptNo()), deptDto);		// 새로운 객체를 컬렉션에 삽입

			// --- 확인용 ---
			/*	Set<String> keySet = deptMap.keySet(); // "10"  "20"
				for (String key : keySet) {
					System.out.println("~~~확인용 key : " + key); // 10  20
					DeptDTO dto = deptMap.get(key);
					System.out.println("~~~확인용 dto : " + dto.toString());

			 * 1. 부서번호 : 10 2. 부서명 : 관리부 3. 부서위치 : 서울
			 * 1. 부서번호 : 20 2. 부서명 : 연구부 3. 부서위치 : 인천


				}// 확인용 끝	*/
			n = serial.objectToFileSave(deptMap, DEPTLISTFILENAME);
		}

		if (n == 1) {
			System.out.println(">>> 부서등록 성공!! <<<\n");
		}else {
			System.out.println(">>> 부서등록 실패!! <<<\n");
		}

	} // registerDept 메서드 end


	// 사원등록 메서드
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void registerEmployee(Scanner sc) {

		// 등록할 사원 객체 생성
		EmployeeDTO emplDto = new EmployeeDTO();

		do { // 사원 id를 입력받아 저장
			System.out.print("\n▶  아이디: ");
			String id = sc.nextLine();

			if (id.trim().isEmpty()) {	// id의 좌우 공백을 모두 없앴을 때 id는 비어있는 문자열이면 // trim()은 String의 좌우 공백을 없애줌.
				System.out.println("~~~ 아이디를 입력하세요!!\n");
				// continue;
			}else if(!isUseID(id)) { 	// id가 정상입력 됐으나 isUseID()메서드로 확인하여 기존 리스트에 이미 해당 id가 존재한다면
				System.out.println("~~~ " + id + "는 이미 존재하므로 다른 ID를 입력하세요!!\n"); 
				// continue;
			}else{						// id가 정상입력 됐으며 기존 리스트에도 없는 id라면
				emplDto.setId(id);  	// 사원 객체에 id를 저장하고
				break;					// do~while문 빠져나감.
			} // else문 end 	
		} while(true);

		// 사원 pw, 이름, 생년월일, 주소, 직급을 입력받아 저장
		System.out.print("▶  암호: ");
		emplDto.setPw(sc.nextLine());

		System.out.print("▶  사원명: ");
		emplDto.setName(sc.nextLine());

		System.out.print("▶  생년월일: ");
		emplDto.setbDay(sc.nextLine());

		System.out.print("▶  주소: ");
		emplDto.setAddress(sc.nextLine());

		System.out.print("▶  직급: ");
		emplDto.setPosition(sc.nextLine());

		do {	// 사원 급여를 입력받아 저장
			System.out.print("▶  급여: ");
			String salary = sc.nextLine();

			try {
				int nSalary = Integer.parseInt(salary); // Integer형으로 형변환 해서 
				emplDto.setSalary(nSalary); 			// 정상적으로 형변환 되면 객체에 저장.
				break;									// 무한반복 탈출.
			} catch (Exception e) {						// 형변환에 예외가 발생하면
				System.out.println("~~~~ 오류 : 급여는 숫자로만 입력하세요!!!\n"); // 문자열 출력
			}

		} while(true);	

		do {	// 사원 부서번호를 입력받아 저장
			System.out.print("▶  부서번호: ");
			String partNo = sc.nextLine();

			/*
			 부서정보가 저장되어진 파일에 가서 모든 부서 번호를 읽어온다.
			 읽어온 부서 번호중에 위에서 사용자가 입력한 부서 번호에 일치하는 것이 없으면 
			 존재하지 않는 부서번호임을 띄워주고 다시 부서번호를 입력받도록 하면 된다. 
			 */

			Map<String, DeptDTO> deptMap = (HashMap<String,DeptDTO>)serial.getobjectFromFile(DEPTLISTFILENAME);

			/*			try {	
				int nPartNo = Integer.parseInt(partNo); // Integer형으로 형변환하여 

				if (deptMap.containsKey(partNo)) {	    // 만약 Map의 key중에 입력한 부서번호(partNo)가 존재한다면
					emplDto.setPartNo(nPartNo);				// 객체에 저장 후
					break;									// 반복문 탈출.
				}else {							  	    // Map의 key중에 입력한 부서번호(partNo)가 존재하지 않는다면
					System.out.println("~~~ 입력하신 부서번호 " + partNo + "은 부서로 존재하지 않습니다."); // 출력문 출력
				}
			} catch (Exception e) {						// 형변환중 예외발생하면
				System.out.println("~~~~ 오류 : 부서번호는 숫자로만 입력하세요!!!\n");// 출력문 출력
			}*/

			Set<String> keyset = deptMap.keySet();
			boolean flag = false;

			for (String key : keyset) {
				if (key.equals(partNo)) {
					flag = true;
					break;
				}
			}
			try {	
				int nPartNo = Integer.parseInt(partNo); // Integer형으로 형변환. 
				if (!flag) { // 만일 입력한 부서번호가 기존 부서번호 리스트에 없을 경우 
					System.out.println("~~~ 입력하신 부서번호 " + partNo + "은 부서로 존재하지 않습니다.\n"); // 출력문 출력
					continue;
				}
				// emplDto.setPartNo(nPartNo); 부서객체를 사원멤버변수로 넣었기 때문에 따로 부서번호를 멤버변수로 가질 필요가 없음.
				emplDto.setDeptDto(deptMap.get(partNo));// 입력한 부서번호가 리스트에 존재하면 객체에 저장 후
				break;									// 반복문 탈출.
			} catch (Exception e) {						// 형변환중 예외발생하면
				System.out.println("~~~~ 오류 : 부서번호는 숫자로만 입력하세요!!!\n");// 출력문 출력
			}

		} while (true);


		// 파일 객체 생성
		File file = new File(EMPLLISTFILENAME);

		// 파일에 저장할 사원리스트 생성
		List<EmployeeDTO> emplList = null;

		if (!file.exists()) { // 만일 파일이 없다면. 즉, 최초 등록 시
			emplList = new ArrayList<>(); // ArrayList를 새로 생성하여 emplList에 넣어줌.
			emplList.add(emplDto);		  // 사원정보가 담긴 emplDto 객체를 emplList에 넣어줌. 

		}else { // 데이터가 존재할 시. 즉, 두번째 등록부터
			emplList = (ArrayList<EmployeeDTO>)serial.getobjectFromFile(EMPLLISTFILENAME); // 파일에 있는 Object를 불러와서 ArrayList로 형변환 시킨 후 emplList에 넣어줌.
			emplList.add(emplDto);		  // 사원정보가 담긴 emplDto 객체를 emplList에 넣어줌.

		}

		int n = serial.objectToFileSave(emplList, EMPLLISTFILENAME); // 파일 저장.

		if (n == 1) { // 파일 저장이 잘 되었다면 
			System.out.println(">>> 사원등록 성공!! <<<\n");
		}else {		  // 파일 저장이 되지 않았다면
			System.out.println(">>> 사원등록 실패!! <<<\n");
		}

	} // registerEmployee 메서드 end


	// 회원 가입시 중복 아이디 체크 메서드
	@SuppressWarnings("unchecked")
	@Override
	public boolean isUseID(String id) {

		boolean flag = true;	// id 중복 여부를 확인하는 변수. 최초 가입시 파일에 저장된 객체가 없기 때문에 중복된 id가 없어서 사용가능하도록 true로 한다.
		List<EmployeeDTO> emplList = (ArrayList<EmployeeDTO>)serial.getobjectFromFile(EMPLLISTFILENAME); // 파일에 저장된 리스트를 불러옴.

		if (emplList != null) {	// 파일이 존재한다면. 파일에 emplList 객체가 있다라면

			for (EmployeeDTO emp : emplList) { // 파일에 저장된 리스트를 돌려 

				if (emp.getId().equals(id)) {  // 입력했던 id가 이미 존재한다면 
					flag = false; 			// id 중복 여부 확인변수를 false로 저장. 입력받은 id가 객체속에 존재한다면 사용불가인 id이다. 
					break; 			// for 반복문 나감.
				} // if문 end 

			} // for문 end

		} // if문 end
		return flag;
	} // 회원 가입시 중복 아이디 체크 메서드 end


	// 로그인 메서드
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDTO login(Scanner sc) {

		List<EmployeeDTO> emplList = (ArrayList<EmployeeDTO>)serial.getobjectFromFile(EMPLLISTFILENAME); // 파일에 저장된 리스트를 불러옴.

		if (emplList == null) {
			System.out.println("~~~ 로그인 할 사원 계정이 없습니다!!!");
			System.out.println("~~~ 사원 등록을 먼저 해주세요!!!");
			return null;
		}else {

			System.out.println("\n== 로그인 하기 ==");
			System.out.print("▶  아이디: ");
			String id = sc.nextLine();

			System.out.print("▶  암호: ");
			String pw = sc.nextLine();


			for (EmployeeDTO empl : emplList) {
				if (empl.getId().equals(id) && empl.getPw().equals(pw)) {
					System.out.println(">>> 로그인 성공!!! <<<");
					return empl;
				}
			}
			System.out.println(">>> 로그인 실패!!! <<<");
			return null;
		}

	}


	// 로그인 후 사원관리 메뉴 메서드
	@Override
	public void employeeMenu(Scanner sc, EmployeeDTO loginEmp) {

		if (loginEmp == null) { // 만일 로그인 한 계정이 없으면 
			return;				// 그냥 빠져나간다.
		}else {					// 로그인 한 계정이 있으면 아래 메뉴를 진행.

			while(true) {
				System.out.println("\n>>>> 사원관리 Menu [" + loginEmp.getName() + "님 로그인중..] <<<<");
				System.out.println("1.내정보 보기  2.내정보 변경하기 3.모든사원정보보기 4.사원검색하기 5.사원사직시키기 6.나가기");
				System.out.print("=> 메뉴번호 선택 : ");

				String choice = sc.nextLine();

				switch (choice) {
				case "1":
					System.out.println("\n== 내정보 ==");
					System.out.println(loginEmp.toString());
					break;
				case "2":
					updateMyInfo(loginEmp, sc);
					break;
				case "3":
					showAllEmployee();
					break;
				case "4":
					searchEmployeeMenu(sc, loginEmp);
					break;
				case "5":
					deleteEmployee(loginEmp, sc);
					break;
				case "6":
					return;
				default:
					System.out.println("~~~~ 존재하지 않는 메뉴번호 입니다.!!\n");							
					break;
				} // switch~case 문 end

			} // while문 end
		} // else문 end	
	} // 로그인 후 사원관리 메뉴 메서드 end


	// 사원관리 메뉴 중 내정보 변경하기 메서드
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeDTO updateMyInfo(EmployeeDTO loginEmp, Scanner sc) {

		System.out.println("\n== 내정보 변경하기 ==");
		System.out.println(loginEmp.toString());
		System.out.println();

		System.out.print("▶ 암호변경: "); 
		String newPw = sc.nextLine();

		System.out.print("▶ 주소변경: ");
		String newAddr = sc.nextLine();

		System.out.print("▶ 직급변경: ");
		String newPosi = sc.nextLine();

		String newSal = null; // 입력받은 급여 저장할 변수
		int nNewSal = 0; // 입력받은 급여를 int형으로 형변환하여 저장할 변수

		do {
			System.out.print("▶ 급여변경: ");
			newSal = sc.nextLine();

			try {
				nNewSal = Integer.parseInt(newSal); // 입력받은 급여를 int형으로 형변환
				break;
			} catch (Exception e) { // 형변환 중 예외발생 시
				System.out.println("~~~~ 오류 : 급여는 숫자로만 입력하세요!!!\n"); 
			}

		}while(true); // 변경할 급여가 숫자로 제대로 입력되면 빠져나옴. 

		do { // 변경할 정보 입력이 끝나면 변경여부 확인 후 변경하기.

			System.out.print("▷ 변경하시겠습니까?[Y/N] => ");
			String yesOrNo = sc.nextLine(); // 변경 여부를 변수에 입력 받음. 

			if (yesOrNo.equalsIgnoreCase("y")) { // 변경 여부 yes일 때

				if (!newPw.trim().isEmpty()) { // 새로 입력한 암호가 빈칸이 아닐 때 
					loginEmp.setPw(newPw);
				}

				if (!newAddr.trim().isEmpty()) { // 새로 입력한 주소가 빈칸이 아닐 때
					loginEmp.setAddress(newAddr);
				}

				if (!newPosi.trim().isEmpty()) { // 새로 입력한 직급이 빈칸이 아닐 때
					loginEmp.setPosition(newPosi);
				}

				if (!newSal.trim().isEmpty()) { // 새로 입력한 급여가 빈칸이 아닐 때
					loginEmp.setSalary(nNewSal);
				}

				List<EmployeeDTO> emplList = (ArrayList<EmployeeDTO>)serial.getobjectFromFile(EMPLLISTFILENAME); // 파일에 저장되어있는 사원 리스트를 불러옴. 

				int i = 0; // 사원 리스트 중 로그인 중인 사원 계정이 몇번째 인덱스에 있는지를 저장하는 변수. 
				for (; i < emplList.size(); i++) { // 사원 리스트를 for문으로 돌리며 
					if (emplList.get(i).getId().equals(loginEmp.getId())) {  // loginEmp의 id와 일치하는 객체(계정)를 찾으면 
						emplList.remove(i); // 기존에 있던 객체(계정)를 삭제하고
						break;
					}
				}
				emplList.add(i, loginEmp); // 기존 계정이 있던 i번째 위치에  loginEmp 계정을 넣어준다.
				/*	
				for (EmployeeDTO empl : emplList) { // 사원 리스트를 for문으로 돌리며 
					if (empl.getId().equals(loginEmp.getId())) { // loginEmp의 id와 일치하는 객체(계정)를 찾으면 
						emplList.remove(empl); // 기존에 있던 객체(계정)를 삭제하고
						break;	
					} // loginEmp 계정을 찾았을 때 if문 end
				} // for문 end
				emplList.add(loginEmp); // 새로 변경된 (객체)계정을 사원 리스트에 저장한다. 
				 */				
				int n = serial.objectToFileSave(emplList, EMPLLISTFILENAME); // 변경된 사원 리스트를 파일에 저장.
				if (n == 1) {
					System.out.println(">>> 내정보 변경 성공!! <<<\n"); // 파일 저장이 정상으로 진행됐을 때
				}else {
					System.out.println(">>> 내정보 변경 실패!! <<<\n"); // 파일 저장이 정상으로 진행되지 않았을 때
				}
				break;										 // 정보 및 파일 변경 반복문 탈출 
			}else if(yesOrNo.equalsIgnoreCase("n")) { // 변경 여부 no일 때 
				System.out.println(">>> 내정보 변경을 취소하셨습니다. <<<\n"); // 정보 변경 없이
				break;												 // 정보 및 파일 반복문 탈출
			}else { // 변경 여부 입력 잘 못 했을 때
				System.out.println("~~~ Y 또는 N을 입력하셔야합니다.\n");	// 다시 변경 여부를 입력하도록 돌아감.
			}

		}while(true);

		return loginEmp;	// 마지막으로 저장된 계정 리턴 
	} // 사원관리 메뉴 중 내정보 변경하기 메서드 end


	// 사원관리 메뉴 중 모든 사원 정보보기 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void showAllEmployee() {

		List<EmployeeDTO> emplList = (ArrayList<EmployeeDTO>)serial.getobjectFromFile(EMPLLISTFILENAME);

		System.out.println("\n>>> 모든사원 정보 출력 <<<");
		System.out.println("===============================================================================================================");
		System.out.println("아이디\t    암호\t사원명\t    생년월일\t나이\t      주소\t\t직급\t      급여\t\t부서번호\t부서명\t부서위치");
		System.out.println("===============================================================================================================");

		for (EmployeeDTO empl  : emplList) {
			System.out.println(empl.getId() + "\t" + empl.printPw() +  "\t" + empl.getName() + "\t" + empl.getbDay() + "\t" + empl.getAge() + "세\t" + empl.getAddress() 
			+ "\t" + empl.getPosition() + "\t" + empl.getSalaryComma(empl.getSalary()) + "\t  " + empl.getDeptDto().getDeptNo() + "\t" + empl.getDeptDto().getDeptName() + "\t   " + empl.getDeptDto().getDeptLoc());
		}

	} // 사원관리 메뉴 중 모든 사원 정보보기 메서드 end

	// 사원관리 메뉴 중 사원사직시키기 메서드 (사장님으로 로그인 했을때만 가능하도록.) 
	@SuppressWarnings("unchecked")
	@Override
	public void deleteEmployee(EmployeeDTO loginEmp, Scanner sc) {
		
		if (!loginEmp.getPosition().equals("사장")) { // 로그인한 계정의 직급이 사장이 아니면
			System.out.println("~~~ 권한이 없습니다.!!!");
			return;
		}else {										 // 로그인한 계정의 직급이 사장이면	
			showAllEmployee();
			String name = null;
			do {
				System.out.print("\n▶ 사직시킬 직원 이름: ");
				name = sc.nextLine(); 
				if (name.trim().isEmpty()) {
					System.out.println("사직시킬 직원 이름을 입력하세요!!!");
				}else {
					break;
				}
				
			}while(true);
			
			do {	
				System.out.println("정말 사직시키겠습니까?? [Y/N]:");
				String yesOrNo = sc.nextLine();
				
				if (yesOrNo.equalsIgnoreCase("y")) {
					List<EmployeeDTO> emplList = (ArrayList<EmployeeDTO>)serial.getobjectFromFile(EMPLLISTFILENAME);
					
					boolean exist = false; // 검색한 사원이 있는지 없는지를 알려주는 변수
					
					for (EmployeeDTO empl : emplList) {
						if (empl.getName().equals(name)) { // 검색한 사원이 사원 리스트에 있었을 때
							emplList.remove(empl); // 검색한 사원 계정(객체) 삭제
							exist = true; // 검색한 사원이 존재했으므로 true로 바꿔줌.
							break; // for문을 탈출
						}
					} // for문 end
					
					if (!exist) {
						System.out.println("검색한 " + name + " 사원은 존재하지 않습니다.");
						break; // 사직 여부를 묻는 while문을 탈출
					}
					
					int n = serial.objectToFileSave(emplList, EMPLLISTFILENAME); // 변경된 리스트를 파일에 저장
					if (n == 1) { // 정상 저장 완료 시
						System.out.println(">>> " + name + " 사원 사직 완료 <<<");
					}else {		  // 정상 저장 실패 시 
						System.out.println(">>> " + name + " 사원 사직 실패 <<<");
					}
					break; // 사직 여부를 묻는 while문을 탈출
				}else if (yesOrNo.equalsIgnoreCase("N")) {
					System.out.println(">>> 사원 사직시키기를 취소하셨습니다. <<<\n");
					break; // 사직 여부를 묻는 while문을 탈출
				}else {
					System.out.println("~~~ Y 또는 N을 입력하셔야합니다.\n");	// 다시 변경 여부를 입력하도록 돌아감.
				}
			}while(true);
			
		} // else문(로그인 계정이 사장일 때) end
	} // 사원관리 메뉴 중 사원사직시키기 메서드 end
	
	
	// 사원관리 메뉴 중 4번 사원검색 메뉴 
	@SuppressWarnings("unchecked")
	@Override
	public void searchEmployeeMenu(Scanner sc, EmployeeDTO loginEmp) {


		if (loginEmp == null) { // 만일 로그인 한 계정이 없으면 
			return;				// 그냥 빠져나간다.
		}else {					// 로그인 한 계정이 있으면 아래 메뉴를 진행.
			List<EmployeeDTO> emplList = (ArrayList<EmployeeDTO>)serial.getobjectFromFile(EMPLLISTFILENAME);

			String choice = null;
			do {
				System.out.println("\n>>>> 사원검색 Menu [" + loginEmp.getName() + "님 로그인중..] <<<<");
				System.out.println("1.사원명 검색  2.연령대 검색 3.직급 검색 4.급여범위 검색 5.부서명 검색 6.나가기");
				System.out.print("=> 메뉴번호 선택 : ");
				choice = sc.nextLine();

				switch (choice) {
				case "1":
					searchEmployeeByName(sc, emplList);
					break;
				case "2":
					searchEmployeeByAge(sc, emplList);
					break;
				case "3":
					searchEmployeeByPosition(sc, emplList);
					break;
				case "4":
					searchEmployeeBySalary(sc, emplList);
					break;
				case "5":
					searchEmployeeByDname(sc, emplList);
					break;
				case "6": // 6을 선택하면 while조건문에서 빠져나가짐.
					break;
				default:
					System.out.println("~~~~ 존재하지 않는 메뉴번호입니다.!!");
					break;
				}

			} while(!choice.equals("6"));

		} // else문 end

	} // 사원관리 메뉴 중 4번 사원검색 메뉴 end

	// 사원 검색 메뉴 중 사원명으로 검색 메서드
	@Override
	public void searchEmployeeByName(Scanner sc, List<EmployeeDTO> emplList) {

		String name = null;
		do {
			System.out.print("\n▶ 검색할 사원명: ");
			name = sc.nextLine();

			if (name.trim().isEmpty()) { // 입력한 사원명이 공백일 때
				System.out.println("~~~ 검색할 사원명을 입력하세요!!!");
			}else { // 입력한 사원명이 공백이 아닐 때
				break;
			} // else문 end
		}while(true);
		
		List<EmployeeDTO> answerList = new ArrayList<EmployeeDTO>(); // 검색 조건에 부합하는 계정(EmployeeDTO 객체)들을 넣을 ArrayList생성

		for (EmployeeDTO empl : emplList) {
			if (empl.getName().equals(name)) { // 검색한 사원명이 리스트에 존재할 때
				answerList.add(empl); // 새로운 리스트에 해당 사원 정보 저장.
			}
		} // for문 end

		if (answerList.isEmpty()) { // 검색 조건에 부합하는 사람이없어서 리스트가 비어있을 때
			System.out.println(">>> 검색하신 " + name + " 는(은) 존재하지 않습니다. <<<");
		}else {						// 검색 조건에 부합하는 사람이있어서 리스트가 채워져 있을 때
			printEmployee("사원명", answerList);
		}
	} // 사원 검색 메뉴 중 사원명으로 검색 메서드 end
	
	// 사원 검색 메뉴 중 연령대로 검색 메서드
	@Override
	public void searchEmployeeByAge(Scanner sc, List<EmployeeDTO> emplList) {


		int nAge = 0;

		do {
			System.out.print("\n▶ 검색할 연령대(예 20대 검색은 20으로 입력): ");
			String age = sc.nextLine();

			try {
				nAge = Integer.parseInt(age); // 입력한 연령대 변수를 int형으로 형변환
				break;
			} catch (Exception e) { // 형변환에서 예외가 발생하면 
				System.out.println("~~~ 연령대는 숫자로만 입력하세요!!\n");
			}
		} while(true);

		List<EmployeeDTO> answerList = new ArrayList<EmployeeDTO>(); // 검색 조건에 부합하는 계정(EmployeeDTO 객체)들을 넣을 ArrayList생성
		
		for (EmployeeDTO empl : emplList) {
			if (empl.getAge() >= nAge && empl.getAge() < (nAge+10)) {
				answerList.add(empl);
			}
		}

		if (answerList.isEmpty()) { // 검색 조건에 부합하는 사람이없어서 리스트가 비어있을 때
			System.out.println(">>> 검색하신 연령대" + nAge + " 는(은) 존재하지 않습니다. <<<");
		}else {						// 검색 조건에 부합하는 사람이있어서 리스트가 채워져 있을 때
			printEmployee("연령대", answerList);
		}


	} // 사원 검색 메뉴 중 연령대로 검색 메서드 end


	// 사원 검색 메뉴 중 직급으로 검색 메서드
	@Override
	public void searchEmployeeByPosition(Scanner sc, List<EmployeeDTO> emplList) {


		String position = null;
		do {
			System.out.print("\n▶ 검색할 직급명: ");
			position = sc.nextLine();

			if (position.trim().isEmpty()) { // 공백 입력 시
				System.out.println("~~~ 검색할 직급명을 입력하세요!!");
			}else { // 공백 입력이 아닐 시
				break;
			}
		} while(true);

		List<EmployeeDTO> answerList = new ArrayList<EmployeeDTO>(); // 검색 조건에 부합하는 계정(EmployeeDTO 객체)들을 넣을 ArrayList생성	
		
		for (EmployeeDTO empl : emplList) {
			if (empl.getPosition().equals(position)) {
				answerList.add(empl);
			}
		}

		if (answerList.isEmpty()) { // 검색 조건에 부합하는 사람이없어서 리스트가 비어있을 때
			System.out.println(">>> 검색하신 직급은 존재하지 않습니다. <<<");
		}else {						// 검색 조건에 부합하는 사람이있어서 리스트가 채워져 있을 때
			printEmployee("직급", answerList);
		}

	} // 사원 검색 메뉴 중 직급으로 검색 메서드 end

	// 사원검색 메뉴 중 급여범위 검색 메서드
	@Override
	public void searchEmployeeBySalary(Scanner sc, List<EmployeeDTO> emplList) {

		int nMinSalary = 0, nMaxSalary = 0;
		
		do {
			try {
				System.out.print("\n▶ 검색할 급여 최소값: ");
				String minsalary = sc.nextLine();
				nMinSalary = Integer.parseInt(minsalary);
				
				System.out.print("▶ 검색할 급여 최대값: ");
				String maxsalary = sc.nextLine();
				nMaxSalary = Integer.parseInt(maxsalary);
				
				break; // 급여 최소값 최대값 형변환이 모두 완료되면 반복문 탈출
			} catch (Exception e) { // 급여 최소값 최대값 형변환중 예외 발생하면 
				System.out.println("~~~ 급여는 숫자로만 입력하세요!!");
			}
			
		}while(true);
		
		List<EmployeeDTO> answerList = new ArrayList<EmployeeDTO>(); // 검색 조건에 부합하는 계정(EmployeeDTO 객체)들을 넣을 ArrayList생성
		
		for (EmployeeDTO empl : emplList) {
			if (empl.getSalary() >= nMinSalary && empl.getSalary() <= nMaxSalary) {
				answerList.add(empl);
			}
		}
		if (answerList.isEmpty()) { // 검색 조건에 부합하는 사람이없어서 리스트가 비어있을 때
			System.out.println(">>> 검색하신 급여범위에 해당하는 사원은 존재하지 않습니다. <<<");
		}else {						// 검색 조건에 부합하는 사람이있어서 리스트가 채워져 있을 때

/*			DecimalFormat df = new DecimalFormat("#,###");
			
			System.out.println("\n>>> 급여범위 검색[" + df.format(nMinSalary) +"원 ~ "+ df.format(nMaxSalary) + "원]<<<");
			System.out.println("===============================================================================================================");
			System.out.println("아이디\t암호\t사원명\t생년월일\t\t나이\t주소\t\t직급\t급여\t\t부서번호\t부서명\t부서위치");
			System.out.println("===============================================================================================================");

			for (EmployeeDTO empl : answerList) {
				System.out.println(empl.getId() + "\t" + empl.printPw() +  "\t" + empl.getName() + "\t" + empl.getbDay() + "\t" + empl.getAge() + "세\t" + empl.getAddress() 
				+ "\t" + empl.getPosition() + "\t" + empl.getSalaryComma(empl.getSalary()) + "\t" + empl.getDeptDto().getDeptNo() + "\t" + empl.getDeptDto().getDeptName() + "\t" + empl.getDeptDto().getDeptLoc());
			}*/
			
			DecimalFormat df = new DecimalFormat("#,###");

			printEmployee("급여범위 [" + df.format(nMinSalary) + "원 ~ " + df.format(nMaxSalary) + "원]", answerList);
		}
		
	} // 사원검색 메뉴 중 급여범위 검색 메서드 end


	// 사원검색 메뉴 중 부서명으로 검색 메서드
	@Override
	public void searchEmployeeByDname(Scanner sc, List<EmployeeDTO> emplList) {

		String dName = null;
		do {
			System.out.print("\n▶ 검색할 부서명: ");
			dName = sc.nextLine();
			
			if (dName.trim().isEmpty()) {
				System.out.println("~~~ 검색할 부서명을 입력하세요!!");
			}else {
				break;
			}
		} while(true);

		List<EmployeeDTO> answerList = new ArrayList<EmployeeDTO>(); // 검색 조건에 부합하는 계정(EmployeeDTO 객체)들을 넣을 ArrayList생성

		for (EmployeeDTO empl : emplList) {
			if (empl.getDeptDto().getDeptName().equals(dName)) { // 리스트에 있는 사원들 중 검색한 부서명에 있는 사람이 있으면
				answerList.add(empl);
			}
		}
		
		if (answerList.isEmpty()) { // 검색 조건에 부합하는 사람이없어서 리스트가 비어있을 때
			System.out.println(">>> 검색하신 " + dName + " 는(은) 존재하지 않습니다. <<<");
		}else { 					// 검색 조건에 부합하는 사람이있어서 리스트가 채워져 있을 때
			printEmployee("부서명", answerList);
		}
		
	} // 사원검색 메뉴 중 부서명으로 검색 메서드 end

	// 사원 검색 결과 출력 메서드
	@Override
	public void printEmployee(String title, List<EmployeeDTO> empList) {

			System.out.println("\n>>> " + title + " 검색 <<<");
			System.out.println("===============================================================================================================");
			System.out.println("아이디\t    암호\t사원명\t    생년월일\t나이\t      주소\t\t직급\t      급여\t\t부서번호\t부서명\t부서위치");
			System.out.println("===============================================================================================================");
			for (EmployeeDTO empl  : empList) {
				System.out.println(empl.getId() + "\t" + empl.printPw() +  "\t" + empl.getName() + "\t" + empl.getbDay() + "\t" + empl.getAge() + "세\t" + empl.getAddress() 
				+ "\t" + empl.getPosition() + "\t" + empl.getSalaryComma(empl.getSalary()) + "\t  " + empl.getDeptDto().getDeptNo() + "\t" + empl.getDeptDto().getDeptName() + "\t   " + empl.getDeptDto().getDeptLoc());
			}
	} // 사원 검색 결과 출력 메서드 end
	
	
	
}
