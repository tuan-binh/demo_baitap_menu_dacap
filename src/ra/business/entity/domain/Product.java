package ra.business.entity.domain;

import ra.business.entity.IProduct;
import ra.business.feature.impl.CategoryFeatureImpl;
import ra.business.feature.impl.ProductFeatureImpl;
import ra.utils.Message;

import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

public class Product implements IProduct {
	private String productId;
	private String productName;
	private String title;
	private float importPrice;
	private float exportPrice;
	private float profit;
	private String descriptions;
	private boolean productStatus;
	private Category category;
	
	public Product() {
	}
	
	public Product(String productId, String productName, String title, float importPrice, float exportPrice, float profit, String descriptions, boolean productStatus, Category category) {
		this.productId = productId;
		this.productName = productName;
		this.title = title;
		this.importPrice = importPrice;
		this.exportPrice = exportPrice;
		this.profit = profit;
		this.descriptions = descriptions;
		this.productStatus = productStatus;
		this.category = category;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public float getImportPrice() {
		return importPrice;
	}
	
	public void setImportPrice(float importPrice) {
		this.importPrice = importPrice;
	}
	
	public float getExportPrice() {
		return exportPrice;
	}
	
	public void setExportPrice(float exportPrice) {
		this.exportPrice = exportPrice;
	}
	
	public float getProfit() {
		return profit;
	}
	
	public void setProfit(float profit) {
		this.profit = profit;
	}
	
	public String getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	
	public boolean isProductStatus() {
		return productStatus;
	}
	
	public void setProductStatus(boolean productStatus) {
		this.productStatus = productStatus;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public void inputData(Scanner sc) {
		this.productId = idAutoIncrement();
		this.productName = inputProductName(sc);
		this.title = inputTitle(sc);
		this.importPrice = inputImportPrice(sc);
		this.exportPrice = inputExportPrice(sc);
		calProfit();
		this.descriptions = inputDescriptions(sc);
		this.productStatus = inputProductStatus(sc);
		this.category = inputCategory(sc);
	}
	
	public Category inputCategory(Scanner sc) {
		// hiển thị dạng menu đa cấp category
		for (Category c : CategoryFeatureImpl.categories) {
			System.out.printf("[ ID: %3d | Name: %10s ]\n", c.getCatalogId(), c.getCatalogName());
		}
		// lựa chọn category
		System.out.println("Nhập vào id danh mục muốn thêm: ");
		do {
			int idChoice = Integer.parseInt(sc.nextLine());
			Optional<Category> optionalCategory = CategoryFeatureImpl.categories.stream().filter(item -> item.getCatalogId() == idChoice).findFirst();
			if (optionalCategory.isPresent()) {
				return optionalCategory.get();
			} else {
				System.err.println("Không tồn tại danh mục có id = " + idChoice);
			}
		} while (true);
	}
	
	public boolean inputProductStatus(Scanner sc) {
		System.out.println("Nhập vào trạng thái sản phẩm (true-Hoạt động, false-Không hoạt động): ");
		do {
			String status = sc.nextLine().toLowerCase();
			if (status.trim().isEmpty()) {
				System.err.println(Message.ERROR_EMPTY);
			} else {
				if (status.equals("true") || status.equals("false")) {
					return Boolean.parseBoolean(status);
				} else {
					System.err.println(Message.ERROR_STATUS);
				}
			}
		} while (true);
	}
	
	public String inputDescriptions(Scanner sc) {
		System.out.println("Nhập vào mô tả sản phẩm: ");
		return sc.nextLine();
	}
	
	public float inputExportPrice(Scanner sc) {
		System.out.println("Nhập vào giá xuất sản phẩm: ");
		do {
			String price = sc.nextLine();
			if (price.trim().isEmpty()) {
				System.err.println(Message.ERROR_EMPTY);
			} else {
				if (Float.parseFloat(price) <= 0) {
					System.err.println(Message.ERROR_PRICE);
				} else {
					return Float.parseFloat(price);
				}
			}
		} while (true);
	}
	
	public float inputImportPrice(Scanner sc) {
		System.out.println("Nhập vào giá nhập sản phẩm: ");
		do {
			String price = sc.nextLine();
			if (price.trim().isEmpty()) {
				System.err.println(Message.ERROR_EMPTY);
			} else {
				if (Float.parseFloat(price) <= 0) {
					System.err.println(Message.ERROR_PRICE);
				} else {
					return Float.parseFloat(price);
				}
			}
		} while (true);
	}
	
	public String inputTitle(Scanner sc) {
		System.out.println("Nhập vào tiêu đề sản phẩm: ");
		do {
			String title = sc.nextLine();
			if (title.trim().isEmpty()) {
				System.err.println(Message.ERROR_EMPTY);
			} else {
				boolean isExist = ProductFeatureImpl.products.stream().anyMatch(product -> product.getTitle().equals(title));
				if (isExist) {
					System.err.println(Message.ERROR_TITLE_EXIST);
				} else {
					return title;
				}
			}
		} while (true);
	}
	
	public String inputProductName(Scanner sc) {
		System.out.println("Nhập vào tên sản phẩm: ");
		do {
			String productName = sc.nextLine();
			if (productName.trim().isEmpty()) {
				System.err.println(Message.ERROR_EMPTY);
			} else {
				boolean isExist = ProductFeatureImpl.products.stream().anyMatch(product -> product.getProductName().equals(productName));
				if (isExist) {
					System.err.println(Message.ERROR_NAME_EXIST);
				} else {
					return productName;
				}
			}
		} while (true);
	}
	
	public String idAutoIncrement() {
		String result = "C";
		Optional<Product> max = ProductFeatureImpl.products.stream().max(Comparator.comparing(Product::getProductId));
		if (max.isPresent()) {
			result += String.format("%03d", Integer.parseInt(max.get().getProductId().replaceAll("C", "0")) + 1);
		} else {
			result += "001";
		}
		return result;
		
	}
	
	@Override
	public void displayData() {
		System.out.printf(
				  "[ ID: %5s | Name: %10s | Title: %10s | ImportPrice: %5f | ExportPrice: %5f | Profit: %5f | Desc: %10s | Status: 10s | Category: %10s ]\n",
				  this.productId,
				  this.productName,
				  this.title,
				  this.importPrice,
				  this.exportPrice,
				  this.profit,
				  this.descriptions,
				  this.productStatus ? "Hoạt động" : "Không hoạt động",
				  this.category.getCatalogName()
		);
	}
	
	@Override
	public void calProfit() {
		this.profit = this.exportPrice - this.importPrice;
	}
}
