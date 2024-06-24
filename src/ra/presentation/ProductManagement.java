package ra.presentation;

import ra.business.entity.domain.Product;
import ra.business.feature.IProductFeature;
import ra.business.feature.impl.CategoryFeatureImpl;
import ra.business.feature.impl.ProductFeatureImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductManagement {
	private IProductFeature productFeature = new ProductFeatureImpl();
	
	public void menuProduct(Scanner scanner) {
		boolean isLoop = true;
		do {
			System.out.println("======================= QUAN LY SAN PHAM =======================");
			System.out.println("1. Thêm mới sản phẩm");
			System.out.println("2. Tính lợi nhuận sản phẩm");
			System.out.println("3. Hiển thị thông tin sản phẩm");
			System.out.println("4. Sắp xếp sản phẩm");
			System.out.println("5. Cập nhật thông tin sản phẩm");
			System.out.println("6. Cập nhật trạng thái sản phẩm");
			System.out.println("7. Quay lại");
			System.out.println("================================================================");
			System.out.println("Lựa chọn của bạn: ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:
					addNewProduct(scanner);
					break;
				case 2:
					calProfitAll();
					break;
				case 3:
					showProducts(scanner);
					break;
				case 4:
					sortProducts(scanner);
					break;
				case 5:
					updateProduct(scanner);
					break;
				case 6:
					updateStatusProduct(scanner);
					break;
				case 7:
					isLoop = false;
					break;
				default:
					System.err.println("Vui lòng nhập lại từ 1 -> 7");
			}
		} while (isLoop);
	}
	
	public void updateStatusProduct(Scanner scanner) {
		System.out.println("Nhập vào id sản phẩm muốn thay đổi: ");
		String idChoice = scanner.nextLine();
		Optional<Product> optionalProduct = ProductFeatureImpl.products.stream().filter(item -> item.getProductId() == idChoice).findFirst();
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setProductStatus(!product.isProductStatus());
			productFeature.addOrUpdate(product);
			System.out.println("Cập nhật trạng thái thành công");
		} else {
			System.err.println("Không tồn tại sản phẩm có id = " + idChoice);
		}
	}
	
	public void updateProduct(Scanner scanner) {
		System.out.println("Nhập vào mã sản phẩm cần cập nhật: ");
		String productId = scanner.nextLine();
		int indexUpdate = productFeature.findIndexById(productId);
		Product productUpdate = ProductFeatureImpl.products.get(indexUpdate);
		boolean isLoop = true;
		do {
			System.out.println("1. Cập nhật tên sản phẩm");
			System.out.println("2. Cập nhật tiêu đề sản phẩm");
			System.out.println("3. Cập nhật giá sản phẩm");
			System.out.println("4. Cập nhật mô tả sản phẩm");
			System.out.println("5. Cập nhật trạng thái sản phẩm");
			System.out.println("6. Cập nhật danh mục sản phẩm");
			System.out.println("7. Quay lại");
			System.out.println("Lựa chọn cập nhật: ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:
					System.out.printf("NHập vào tên mới (tên cũ : %10s): \n", productUpdate.getProductName());
					productUpdate.inputProductName(scanner);
					break;
				case 2:
					System.out.printf("Nhập vào tiêu đề mới (tiêu đề cũ: %10s): \n", productUpdate.getTitle());
					productUpdate.inputTitle(scanner);
					break;
				case 3:
					// cập nhật cả giá nhập và giá xuất
					System.out.printf("Nhập vào giá nhập mới (giá cũ: %3f): \n", productUpdate.getImportPrice());
					productUpdate.inputImportPrice(scanner);
					System.out.printf("Nhập vào giá xuất mới (giá cũ: %3f): \n", productUpdate.getExportPrice());
					productUpdate.inputExportPrice(scanner);
					productUpdate.calProfit();
					break;
				case 4:
					// cập nhật mô tả sản phẩm
					System.out.printf("Nhập vào mô tả sản phẩm (mô tả cũ %10s): \n", productUpdate.getDescriptions());
					productUpdate.inputDescriptions(scanner);
					break;
				case 5:
					// cập nhật trạng thái danh mục
					System.out.printf("Nhập vào trạng thái mới (trạng thái cũ: %10s): \n", productUpdate.getDescriptions());
					productUpdate.inputProductStatus(scanner);
					break;
				case 6:
					// cập nhật danh mục sản phẩm
					System.out.printf("Nhập vào danh mục mới (danh mục cũ: %10s): \n", productUpdate.getCategory().getCatalogName());
					productUpdate.inputCategory(scanner);
					break;
				case 7:
					isLoop = false;
					break;
				default:
					System.err.println("Vui lòng lựa chọn từ 1 -> 7");
			}
			productFeature.addOrUpdate(productUpdate);
		} while (isLoop);
	}
	
	public void sortProducts(Scanner scanner) {
		boolean isLoop = true;
		do {
			System.out.println("======================= SAP XEP SAN PHAM =======================");
			System.out.println("1. Sắp xếp sản phẩm theo gia xuat tăng dần");
			System.out.println("2. Sắp xếp sản phẩm theo lợi nhuận tăng dần");
			System.out.println("3. Quay lại");
			System.out.println("================================================================");
			System.out.println("Lựa chọn của bạn: ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:
					sortProductByExportPriceAsc();
					break;
				case 2:
					sortProductByProfitAsc();
					break;
				case 3:
					isLoop = false;
					break;
				default:
					System.err.println("Vui lòng lựa chọn lại từ 1 -> 3");
			}
			
		} while (isLoop);
	}
	
	public void sortProductByProfitAsc() {
		ProductFeatureImpl.products.sort(Comparator.comparingDouble(Product::getProfit));
	}
	
	public void sortProductByExportPriceAsc() {
		ProductFeatureImpl.products.sort(Comparator.comparingDouble(Product::getExportPrice));
	}
	
	public void showProducts(Scanner scanner) {
		boolean isLoop = true;
		do {
			System.out.println("======================= THONG TIN SAN PHAM =======================");
			System.out.println("1. Hiển thị sản phẩm theo danh mục");
			System.out.println("2. Hiển thị chi tiết sản phẩm");
			System.out.println("3. Quay lại");
			System.out.println("==================================================================");
			System.out.println("Lựa chọn của bạn: ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:
					showListProductByCatalog(scanner);
					break;
				case 2:
					showListProductByName(scanner);
					break;
				case 3:
					isLoop = false;
					break;
				default:
					System.err.println("Vui lòng nhập lại từ 1 -> 3");
			}
			
		} while (isLoop);
	}
	
	public void showListProductByName(Scanner scanner) {
		System.out.println("Nhập vào tên sản phẩm: ");
		String productName = scanner.nextLine().toLowerCase();
		Optional<Product> optionalProduct = ProductFeatureImpl.products.stream()
				  .filter(item -> item.getProductName().toLowerCase().equals(productName))
				  .findFirst();
		if (optionalProduct.isPresent()) {
			optionalProduct.get().displayData();
		} else {
			System.err.println("Không tồn tại sản phẩm có tên là " + productName);
		}
	}
	
	public void showListProductByCatalog(Scanner scanner) {
		System.out.println("Nhập vào tên danh mục: ");
		String catalogName = scanner.nextLine().toLowerCase();
		List<Product> products = ProductFeatureImpl.products.stream()
				  .filter(item -> item.getCategory().getCatalogName().toLowerCase().equals(catalogName))
				  .collect(Collectors.toList());
		if (products.isEmpty()) {
			System.err.println("Không tồn tại sản phẩm có danh mục là " + catalogName);
			return;
		}
		products.forEach(Product::displayData);
	}
	
	public void calProfitAll() {
		ProductFeatureImpl.products.forEach(Product::calProfit);
		System.out.println("Đã tính toán lợi nhuận xong");
	}
	
	public void addNewProduct(Scanner scanner) {
		System.out.println("Nhập vào số lượng muốn thêm: ");
		int n = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < n; i++) {
			Product product = new Product();
			product.inputData(scanner);
			productFeature.addOrUpdate(product);
		}
	}
}
