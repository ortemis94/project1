package test.skill;

import java.text.DecimalFormat;

public class Book {

	private String category;		// 도서 카테고리	field, attribute, 속성
	private String bookName;		// 도서명			field, attribute, 속성
	private int bookPrice;			// 정가			field, attribute, 속성
	private int bookDiscountRate;	// 할인율			field, attribute, 속성
	
	public Book() {}
	
	public Book(String category, String bookName, int bookPrice, int bookDiscountRate) {

		this.category = category;
		this.bookName = bookName;
		this.bookPrice = bookPrice;
		this.bookDiscountRate = bookDiscountRate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(int bookPrice) {
		this.bookPrice = bookPrice;
	}

	public int getBookDiscountRate() {
		return bookDiscountRate;
	}

	public void setBookDiscountRate(int bookDiscountRate) {
		this.bookDiscountRate = bookDiscountRate;
	}
	
	////////////////////////////////////////////////////////////////
	
	public String getBookPriceComma() { // 정가 단위표시(,) 메서드
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(bookPrice);
	}
	
	public int getBookSalePrice() { // 할인가 계산 메서드
		
		// return (int)(bookPrice - bookPrice * (bookDiscountRate * 0.01));
		return (int)(bookPrice * (100 - bookDiscountRate) * 0.01);
	}

	public String getBookSalePriceComma() {  // 할인가 단위표시(,) 메서드
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(getBookSalePrice());
	}
	
	public int getTotalBookPrice(Book[] bookArray) { // 판매정가합 메서드
		int totalBookPrice = 0;
		
		for (Book book : bookArray) {
			totalBookPrice += book.getBookPrice();
		}
		
		return totalBookPrice;
	}
	
	public int getTotalSalePrice(Book[] bookArray) { // 판매세일가합 메서드
		int  totalSalePrice = 0;

		for (Book book : bookArray) {
			totalSalePrice += book.getBookSalePrice();
		}
		
		return totalSalePrice;
		
	}
	
}
