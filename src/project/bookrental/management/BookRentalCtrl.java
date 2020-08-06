package project.bookrental.management;

import java.io.File;
import java.util.*;

public class BookRentalCtrl implements InterBookRentalCtrl {

	private final String ISBNLISTFILENAME = "C:/iotestdata/project/bookrental/isbnlist.dat";
	private final String BOOKLISTFILENAME = "C:/iotestdata/project/bookrental/booklist.dat";
	private final String LIBLISTFILENAME = "C:/iotestdata/project/bookrental/liblist.dat";
	private final String CUSLISTFILENAME = "C:/iotestdata/project/bookrental/cuslist.dat";

	private BookRentalSerializable serial = new BookRentalSerializable();
	
	// 사서 전용 메뉴
	@Override
	public void librarianMenu(Scanner sc) {
		
		LibrarianDTO loginLib = null; // 로그인하고 있는 계정.
		String choice = null;
		
		do {

			if (loginLib != null) { // 로그인 중이면
				System.out.println("\n>>>> 사서 전용 메뉴 [" + loginLib.getLibId() + "로그인중..]<<<<");
			}else {					// 로그인 중이 아니면
				System.out.println("\n>>>> 사서 전용 메뉴 <<<<");
			}
			System.out.println("1.사서가입  2.로그인  3.로그아웃  4.도서정보등록  5.개별도서등록");
			System.out.println("6.도서대여해주기  7.대여중인도서조회  8.도서반납해주기  9.나가기");
			System.out.print("=> 메뉴번호선택 : ");
			choice = sc.nextLine();
			
			switch (choice) {
			case "1": // 사서 가입 
				libRegister(sc);
				break;
			case "2": // 사서 로그인
				if (loginLib == null) loginLib = libLogin(sc);
				else System.out.println("이미 로그인 중입니다.");
				break;
			case "3": // 사서 로그아웃
				loginLib = null;
				break;
			case "4": // 도서정보등록
				if (loginLib != null) isbnRegister(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "5": // 개별도서등록
				if (loginLib != null) bookIdRegister(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "6": // 도서대여해주기
				if (loginLib != null) bookRent(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "7": // 대여중인도서조회
				if (loginLib != null) searchRentedBook();
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "8": // 도서반납해주기
				if (loginLib != null) returnBook(sc);
				else System.out.println("사서 로그인을 먼저 진행해야 합니다.");
				break;
			case "9": // 나가기 
				break;
			default:
				System.out.println("~~~~ 오류: 선택지에 없는 번호입니다. 다시 입력해주세요!!! ");
			}
			
		} while(!choice.equals("9"));
		
		
	} // 사서 전용 메뉴 end

	
	// 사서 가입 메서드
	@SuppressWarnings("unchecked")
	@Override
	public void libRegister(Scanner sc) {
		
		LibrarianDTO lib = new LibrarianDTO(); // 새로운 사서 객체를 생성
		
		System.out.println("\n== 사서가입하기 ==");
		
/*		do { // id와 pw가 제대로 입력되어질 때까지 반복할 부분.
			System.out.print("▶ 사서 ID: ");
			String id = sc.nextLine();
			
			System.out.print("▶ 암호: ");
			String pw = sc.nextLine();
			
			
			if (id.trim().isEmpty() || pw.trim().isEmpty()) { // 입력한 id와 pw중 하나라도 공백이 있다면
				System.out.println("ID와 암호는 공백이 안됩니다!!!");
				System.out.println("반드시 입력해주세요!!!\n");
			}else if(!idCheck(id)) { // id와 pw를 모두 입력했으나 이미 존재하는 id라면
				System.out.println("이미 존재하는 ID입니다 다시 입력해주세요!!!\n");
			}else { // 중복 id도 없다면
				lib.setLibId(id); // 입력한 id를 새로운 사서 객체의 id에 저장.
				lib.setLibPw(pw); // 입력한 pw를 새로운 사서 객체의 pw에 저장.
				break; // 반복문 탈출.
			}
		} while(true);*/
	
		do{
			String id = emptyCheck("사서ID", sc);
			String pw = emptyCheck("암호", sc);
			if (!idCheck(id)) {
				System.out.println("이미 존재하는 ID입니다 다시 입력해주세요!!!\n");
			}else {
				lib.setLibId(id); // 입력한 id를 새로운 사서 객체의 id에 저장.
				lib.setLibPw(pw); // 입력한 pw를 새로운 사서 객체의 pw에 저장.
				break; // 반복문 탈출.
			}
			
		}while(true);
		
		
		File file = new File(LIBLISTFILENAME); // 파일 객체 생성
		
		List<LibrarianDTO> libList = null; // 파일에 저장할 사서 리스트 선언
		
		if (!file.exists()) { // 만일 기존 파일이 없다면
			libList = new ArrayList<LibrarianDTO>(); // libList에 ArrayList를 새로 할당해줌. 
			libList.add(lib); // 새로운 사서 객체를 사서 리스트(libList)에 넣어줌. 
		}else {	// 기존 파일이 있다면 
			libList = (ArrayList<LibrarianDTO>)serial.getobjectFromFile(LIBLISTFILENAME); // 파일에 있는 Object 객체를 불러와 ArrayList로 형변환 하여 libList에 넣어줌.
			libList.add(lib); // 새로운 사서 객체를 사서 리스트(libList)에 넣어줌.
		}
		
		int n = serial.objectToFileSave(libList, LIBLISTFILENAME); // 사서 객체(계정)를 새로 저장한 libList를 다시 파일에 저장함. 
		
		if (n == 1) { // 만일 파일에 저장이 정상적으로 완료되면
			System.out.println(">>> 사서 등록 성공!! <<<");
		}else if(n == 0){ // 파일에 저장이 정상적으로 완료되지 못하면
			System.out.println(">>> 사서 등록 실패 <<<");
		}
		
	} // 사서 가입 메서드 end
	
	
	// 사서 회원가입시 id 중복 확인 메서드
	@SuppressWarnings("unchecked")
	boolean idCheck(String id) {
		
		boolean flag = true; // 회원가입이 가능한지 여부확인. 최조 가입시에는 중복될 id가 없으므로 true가 되게 함. 
		
		List<LibrarianDTO> libList = (ArrayList<LibrarianDTO>)serial.getobjectFromFile(LIBLISTFILENAME); // 파일에 저장되어있는 사서 리스트를 불러옴.
		if (libList != null) { // 사서리스트가 비어있지 않다면 
			for (LibrarianDTO lib : libList) { // 사서리스트에 있는 계정을 하나하나 확인하며 
				if (id.equals(lib.getLibId())) { // 만일 입력한 id가 이미 리스트에 존재한다면
					flag = false; // 회원가입이 가능하지 못하게 flag 변수를 false로 저장.
				}
			}
		}
		
		return flag;
	}
	// 사서 회원가입시 id 중복 확인 메서드 end
	
	
	// 사서 로그인 메서드
	@SuppressWarnings("unchecked")
	@Override
	public LibrarianDTO libLogin(Scanner sc) {

		LibrarianDTO loginLib = null; // 로그인 계정

		List<LibrarianDTO> libList = (ArrayList<LibrarianDTO>)serial.getobjectFromFile(LIBLISTFILENAME);

		if (libList == null) { // 만일 불러온 파일에 리스트가 없다면
			System.out.println("로그인 할 계정이 없습니다. 사서 가입을 해주세요!!!");
		}else {				   // 파일에 리스트가 있다면
			System.out.println("\n== 로그인 하기 ==");
			System.out.print("▶ 사서 아이디: ");
			String id = sc.nextLine();
			
			System.out.print("▶ 사서 암호: ");
			String pw = sc.nextLine();
			
			for (LibrarianDTO lib : libList) { // 리스트에 있는 사서DTO를 하나씩 확인하며 
				if (lib.getLibId().equals(id) && lib.getLibPw().equals(pw)) { // 입력한 id와 암호가 모두 일치하면
					System.out.println(">>> 로그인 성공!!! <<<"); 
					loginLib = lib;// 해당 DTO를 로그인 계정으로 넣어줌. 
					return loginLib; // 반복문 나감
				} // if문 end
			} // for문 end
			
			System.out.println("로그인에 실패하였습니다."); // id와 pw가 일치하는게 없다면
		} // else문 end
		
		return loginLib;
	} // 사서 로그인 메서드 end

	
	// 도서정보 등록 메서드
	@Override
	public void isbnRegister(Scanner sc) {
		
		ISBN isbn = new ISBN(); // 새로운 도서객체 생성 
		
		System.out.println("\n== 도서정보 등록하기 ==");
	/*	do {
			System.out.print("▶ 국제표준도서번호(ISBN): ");
			String is = sc.nextLine();
			if (is.trim().isEmpty()) { // 공백을 입력하면
				System.out.println("~~~ 국제표준도서번호(ISBN)를 입력하세요!!");
			}else { // 공백이 아니면
				isbn.setIsbn(is);
				break; // 반복문 탈출
			}
		}while(true);*/
		
		isbn.setIsbn(emptyCheck("국제표준도서번호(ISBN)", sc));
		
		isbn.setbKind(emptyCheck("도서분류카테고리", sc));
		
		isbn.setbName(emptyCheck("도서명", sc));
		
		isbn.setbAuthor(emptyCheck("작가명", sc));
		
		isbn.setbCompany(emptyCheck("출판사", sc));
		
		do {
			String price = emptyCheck("가격", sc);
			try {
				int nPrice = Integer.parseInt(price);
				isbn.setbPrice(nPrice);
				break;
			} catch (Exception e) {
				System.out.println("~~~~ 오류 : 가격은 숫자로만 입력하세요!!!");
			}
			
		}while(true);
		
		
		
		
	} // 도서정보 등록 메서드 end
	
	// 개별도서 등록 메서드
	@Override
	public void bookIdRegister(Scanner sc) {
		// TODO Auto-generated method stub

	} // 개별도서 등록 메서드 end
	
	// 책 대여해주기 메서드
	@Override
	public void bookRent(Scanner sc) {
		// TODO Auto-generated method stub

	} // 책 대여해주기 메서드 end
	
	// 대여중인 책 조회 메서드
	@Override
	public void searchRentedBook() {
		// TODO Auto-generated method stub

	} // 대여중인 책 조회 메서드 end
	
	// 책 반납 메서드
	@Override
	public void returnBook(Scanner sc) {
		// TODO Auto-generated method stub
	
	} // 책 반납 메서드 end
	
	// 일반 회원 메뉴 
	@Override
	public void customerMenu(Scanner sc) {
	} // 일반 회원 메뉴  end

	// 일반 회원 가입 메서드 
	@Override
	public void CusRegister(Scanner sc) {
		// TODO Auto-generated method stub

	} // 일반 회원 가입 메서드  end
	
	// 일반 회원 로그인 메서드
	@Override
	public CustomerDTO cusLogin(Scanner sc) {
		// TODO Auto-generated method stub
		return null;
	} // 일반 회원 로그인 메서드 end
	
	// 도서검색하기 메서드
	@Override
	public void searchBook(Scanner sc) {
		// TODO Auto-generated method stub

	} // 도서검색하기 메서드 end
	
	// 나의 대여 현황 보기 메서드
	@Override
	public void myRentedBook(CustomerDTO loginCus) {
		// TODO Auto-generated method stub

	} // 나의 대여 현황 보기 메서드 end

	// 공백 확인 메서드
	String emptyCheck(String title, Scanner sc) {
		
		do {
			System.out.print("▶ " + title + ": ");
			String string = sc.nextLine();
			if (string.trim().isEmpty()) { // 공백을 입력하면
				System.out.println("~~~ " + title +"을(를) 입력하세요!!\n");
			}else { // 공백이 아니면
				return string; // 해당 문자열 리턴
			}
		}while(true);
		
	}

}
