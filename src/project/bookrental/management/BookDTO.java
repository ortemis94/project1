package project.bookrental.management;

import java.io.Serializable;
// 개별도서
public class BookDTO implements Serializable{

	private static final long serialVersionUID = -4748319992166562644L;
	
	private String bId; 	// 개별도서번호 (ex) -001,-002)
	private ISBN isbn;		//  
	
	public BookDTO() {}

	public BookDTO(String bId, ISBN isbn) {
		super();
		this.bId = bId;
		this.isbn = isbn;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public ISBN getIsbn() {
		return isbn;
	}

	public void setIsbn(ISBN isbn) {
		this.isbn = isbn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

	
	
}
