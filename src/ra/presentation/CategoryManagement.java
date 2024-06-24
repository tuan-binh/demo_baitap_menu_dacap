package ra.presentation;

import ra.business.entity.domain.Category;
import ra.business.feature.ICategoryFeature;
import ra.business.feature.impl.CategoryFeatureImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CategoryManagement {
	private final ICategoryFeature categoryFeature = new CategoryFeatureImpl();
	
	public void menuCategory(Scanner scanner) {
		boolean isLoop = true;
		do {
			System.out.println("======================= QUAN LY DANH MUC =======================");
			System.out.println("1. Danh sách danh mục");
			System.out.println("2. Thêm danh mục");
			System.out.println("3. Xóa danh mục");
			System.out.println("4. Tìm kiếm danh mục");
			System.out.println("5. Thoát");
			System.out.println("================================================================");
			System.out.println("Lựa chọn của bạn: ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:
					menuShowListCategory(scanner);
					break;
				case 2:
					addNewCategory(scanner);
					break;
				case 3:
					removeCategory(scanner);
					break;
				case 4:
					searchByCatalogName(scanner);
					break;
				case 5:
					isLoop = false;
					break;
				default:
					System.err.println("Vùi lòng lựa chọn từ 1 -> 5");
			}
			
		} while (isLoop);
	}
	
	public void searchByCatalogName(Scanner scanner) {
		System.out.println("Nhập vào tên muốn tìm kiếm: ");
		String search = scanner.nextLine().toLowerCase();
		List<Category> categories = CategoryFeatureImpl.categories.stream().filter(item -> item.getCatalogName().toLowerCase().contains(search)).collect(Collectors.toList());
		if (categories.isEmpty()) {
			System.err.println("Không tồn tại danh mục có tên là " + search);
			return;
		}
		categories.forEach(Category::displayData);
	}
	
	public void removeCategory(Scanner scanner) {
		System.out.println("Nhập vào id danh mục muốn xóa: ");
		int idDelete = Integer.parseInt(scanner.nextLine());
		categoryFeature.removeById(idDelete);
	}
	
	public void addNewCategory(Scanner scanner) {
		System.out.println("Bạn muốn thêm bao nhiêu danh mục: ");
		int n = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < n; i++) {
			Category category = new Category();
			category.inputData(scanner);
			categoryFeature.addOrUpdate(category);
		}
	}
	
	public void menuShowListCategory(Scanner scanner) {
		boolean isLoop = true;
		do {
			System.out.println("======================= QUAN LY DANH MUC =======================");
			System.out.println("1. Danh sách danh mục cây");
			System.out.println("2. Thông tin danh mục chi tiết");
			System.out.println("3. Quay lại");
			System.out.println("================================================================");
			System.out.println("Lựa chọn của bạn: ");
			int choice = Integer.parseInt(scanner.nextLine());
			switch (choice) {
				case 1:
					showListCategoryTree();
					break;
				case 2:
					showCategoryDetailByName(scanner);
					break;
				case 3:
					isLoop = false;
					break;
				default:
					System.err.println("Vui lòng lựa chọn từ 1 -> 3");
			}
		} while (isLoop);
	}
	
	public void showCategoryDetailByName(Scanner scanner) {
		System.out.println("Nhập vào tên danh mục muốn tìm kiếm: ");
		String catalogName = scanner.nextLine();
		Optional<Category> optionalCategory = CategoryFeatureImpl.categories.stream().filter(item -> item.getCatalogName().equals(catalogName)).findFirst();
		if (optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			System.out.println("Mã danh mục: " + category.getCatalogId() + " - Tên danh mục: " + category.getCatalogName());
			System.out.println("Mô tả: " + category.getDescriptions());
			System.out.println("Danh mục cha: " + category.getParentId() + " - Trạng thái: " + (category.isCatalogStatus() ? "Hoạt động" : "Không hoạt động"));
			List<Category> children = CategoryFeatureImpl.categories.stream().filter(item -> item.getParentId() == category.getCatalogId()).collect(Collectors.toList());
			if (!children.isEmpty()) {
				for (Category cate : children) {
					System.out.println("Mã danh mục: " + cate.getCatalogId() + " - Tên danh mục: " + cate.getCatalogName());
					System.out.println("Mô tả: " + cate.getDescriptions());
					System.out.println("Danh mục cha: " + cate.getParentId() + " - Trạng thái: " + (cate.isCatalogStatus() ? "Hoạt động" : "Không hoạt động"));
				}
			}
		} else {
			System.err.println("Không tồn tại danh mục này");
		}
	}
	
	public void showListCategoryTree() {
		listTree(0, "", "");
	}
	
	public void listTree(int parentId, String prefix, String numbering) {
		List<Category> subCategories = getCategoriesById(parentId);
		for (int i = 0; i < subCategories.size(); i++) {
			Category category = subCategories.get(i);
			String currentNumbering = numbering.isEmpty() ? "" + (i + 1) : numbering + "." + (i + 1);
			System.out.println(prefix + currentNumbering + ". " + category.getCatalogName());
			listTree(category.getCatalogId(), prefix + "  ", currentNumbering);
		}
	}
	
	public List<Category> getCategoriesById(int id) {
		return CategoryFeatureImpl.categories.stream().filter(item -> item.getParentId() == id).collect(Collectors.toList());
	}
}
