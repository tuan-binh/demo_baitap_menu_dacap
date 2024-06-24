package ra.business.entity.domain;

import ra.business.entity.ICategory;
import ra.business.feature.impl.CategoryFeatureImpl;
import ra.utils.Message;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Category implements ICategory {
	private int catalogId;
	private String catalogName;
	private String descriptions;
	private boolean catalogStatus;
	private int parentId;
	
	public Category() {
	}
	
	public Category(int catalogId, String catalogName, String descriptions, boolean catalogStatus, int parentId) {
		this.catalogId = catalogId;
		this.catalogName = catalogName;
		this.descriptions = descriptions;
		this.catalogStatus = catalogStatus;
		this.parentId = parentId;
	}
	
	public int getCatalogId() {
		return catalogId;
	}
	
	public void setCatalogId(int catalogId) {
		this.catalogId = catalogId;
	}
	
	public String getCatalogName() {
		return catalogName;
	}
	
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	
	public String getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	
	public boolean isCatalogStatus() {
		return catalogStatus;
	}
	
	public void setCatalogStatus(boolean catalogStatus) {
		this.catalogStatus = catalogStatus;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	@Override
	public void inputData(Scanner sc) {
		this.catalogId = idAutoIncrement();
		this.catalogName = inputCatalogName(sc);
		this.descriptions = inputDescriptions(sc);
		this.catalogStatus = inputCatalogStatus(sc);
		this.parentId = inputParentId(sc);
	}
	
	public int inputParentId(Scanner sc) {
		// show ra list category
		List<Category> categories = CategoryFeatureImpl.categories;
		for (int i = 0; i < categories.size(); i++) {
			System.out.println("Mã danh mục: " + categories.get(i).getCatalogId() + " | Tên danh mục: " + categories.get(i).getCatalogName());
		}
		// lựa chọn category parent
		System.out.println("Nhập mã danh mục (Không nhập là danh mục gốc): ");
		String choiceId = sc.nextLine();
		if (choiceId.isEmpty()) {
			return 0;
		}
		do {
			boolean isExist = CategoryFeatureImpl.categories.stream().anyMatch(item -> item.getCatalogId() == Integer.parseInt(choiceId));
			if (isExist) {
				return Integer.parseInt(choiceId);
			} else {
				System.err.println("Không tồn tại danh mục đấy, vui lòng nhập lại");
			}
		} while (true);
	}
	
	public boolean inputCatalogStatus(Scanner sc) {
		System.out.println("Nhập vào trạng thái danh mục (true-hoạt động, false-không hoạt động): ");
		do {
			String catalogStatus = sc.nextLine().toLowerCase();
			if (catalogStatus.trim().isEmpty()) {
				System.err.println(Message.ERROR_EMPTY);
			} else {
				if (catalogStatus.equals("true") || catalogStatus.equals("false")) {
					return Boolean.parseBoolean(catalogStatus);
				} else {
					System.err.println(Message.ERROR_STATUS);
				}
			}
		} while (true);
	}
	
	public String inputDescriptions(Scanner sc) {
		System.out.println("Nhập vào mô tả danh mục: ");
		return sc.nextLine();
	}
	
	public String inputCatalogName(Scanner sc) {
		System.out.println("Nhập vào tên danh mục: ");
		do {
			String catalogName = sc.nextLine();
			if (catalogName.trim().isEmpty()) {
				System.err.println(Message.ERROR_EMPTY);
			} else {
				boolean isExist = CategoryFeatureImpl.categories
						  .stream().anyMatch(item -> item.getCatalogName().equals(catalogName));
				if (isExist) {
					System.err.println(Message.ERROR_NAME_EXIST);
				} else {
					return catalogName;
				}
			}
		} while (true);
	}
	
	public int idAutoIncrement() {
		int maxId = 0;
		Optional<Category> max = CategoryFeatureImpl.categories.stream().max(Comparator.comparingInt(Category::getCatalogId));
		return max.map(category -> category.getCatalogId() + 1).orElseGet(() -> maxId + 1);
	}
	
	@Override
	public void displayData() {
		System.out.printf(
				  "[ ID: %5d | Name: %10s | Desc: %10s | Status: %10s ]\n",
				  this.catalogId,
				  this.catalogName,
				  this.descriptions,
				  this.catalogStatus ? "Hoạt động" : "Không hoạt động"
		);
	}
}
