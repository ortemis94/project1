package project.employee.management;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;

public class EmployeeDTO implements Serializable{

	private static final long serialVersionUID = 2534699851589679551L;

	private String id;
	private String pw;
	private String name;
	private String bDay;
	private String address;
	private String position;
	private int salary;
	private DeptDTO deptDto; 
//	private int partNo; 부서객체를 멤버변수로 가지기 때문에 부서번호를 따로 가질 필요가 없음. 

	public EmployeeDTO() {}
	
	public EmployeeDTO(String id, String pw, String name, String bDay, String address, String position, int salary,
			DeptDTO deptDto) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.bDay = bDay;
		this.address = address;
		this.position = position;
		this.salary = salary;
		this.deptDto = deptDto;
		//this.partNo = partNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getbDay() {
		return bDay;
	}

	public void setbDay(String bDay) {
		this.bDay = bDay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public void setDeptDto(DeptDTO deptDto) {
		this.deptDto = deptDto;
	}
	
	public DeptDTO getDeptDto() {
		return deptDto;
	}
	
/*	public int getPartNo() {
		return partNo;
	}

	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getAge() {
		
		/*
		    1998.08.10
		    birthday.substring(0, birthday.indexOf("."));
		 */
		Calendar c = Calendar.getInstance();
		int current = c.get(Calendar.YEAR);
		
		String year = bDay.substring(0, bDay.indexOf("."));
		
		
		int bYear= Integer.parseInt(year);
		
		return current- bYear;
	}
	 
	public String getSalaryComma(int salary) {
		
		DecimalFormat df = new DecimalFormat("#,###");
		
		return df.format(salary); 
	}
	
	public String printPw() { // 암호 출력 형식 메서드
		return pw.substring(0, 3) + "****";
	}

	
	@Override
	public String toString() {
		return "▷ 아이디: " + id 
				+ "\n▷ 암호: " + pw 
				+ "\n▷ 사원명: " + name 
				+ "\n▷ 생년월일: " + bDay 
				+"\n▷ 나이: " + this.getAge() + "세" 
				+ "\n▷ 주소: " + address
				+ "\n▷ 직급: " + position 
				+ "\n▷ 급여: " + this.getSalaryComma(salary) 
				+ "\n▷ 부서번호: " + this.deptDto.getDeptNo()
				+ "\n▷ 부서명: " + this.deptDto.getDeptName() 
				+ "\n▷ 부서위치: " + this.deptDto.getDeptLoc();
	}
	
	
	
}
