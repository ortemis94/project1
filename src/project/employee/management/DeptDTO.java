package project.employee.management;

import java.io.Serializable;

public class DeptDTO implements Serializable{

	// DTO(Data Transfer Object) ==> 데이터전송객체
	
	private static final long serialVersionUID = -4243660468555022538L;

	private int deptNo;			// 부서번호
	private String deptName;	// 부서명
	private String deptLoc;		// 부서위치
	
	public DeptDTO() {}
	
	public DeptDTO(int deptNo, String deptName, String deptLoc) {
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.deptLoc = deptLoc;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptLoc() {
		return deptLoc;
	}

	public void setDeptLoc(String deptLoc) {
		this.deptLoc = deptLoc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "1. 부서번호 : " + deptNo + " 2. 부서명 : " + deptName + " 3. 부서위치 " + deptLoc + "\n";
	}

	
}
