package ra.presentation;

import java.util.Scanner;

public class ShopManagement {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		CategoryManagement categoryManagement = new CategoryManagement();
		ProductManagement productManagement = new ProductManagement();
		do {
			System.out.println("=========================== MENU ===========================");
			System.out.println("1. Quản lý danh mục");
			System.out.println("2. Quản lý sản phẩm");
			System.out.println("3. Thoát");
			System.out.println("============================================================");
			System.out.println("Lựa chọn của bạn: ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:
					categoryManagement.menuCategory(scanner);
					break;
				case 2:
					productManagement.menuProduct(scanner);
					break;
				case 3:
					System.exit(0);
					break;
				default:
					System.err.println("Vui lòng nhập lại từ 1 -> 3");
			}
		} while (true);
	}
}
