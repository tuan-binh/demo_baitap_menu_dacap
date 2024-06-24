package ra.business.feature.impl;

import ra.business.entity.domain.Category;
import ra.business.feature.ICategoryFeature;

import java.util.ArrayList;
import java.util.List;

public class CategoryFeatureImpl implements ICategoryFeature {
	public static List<Category> categories = new ArrayList<>();
	static {
		Category categories1 = new Category(1,"Quan ao","hay",true,0);
		Category categories2 = new Category(2,"Quan ao nam","hay",true,1);
		Category categories3 = new Category(3,"Ao so mi","hay",true,2);
		Category categories4 = new Category(4,"Quan au","hay",true,2);
		Category categories5 = new Category(5,"Quan dai","hay",true,1);
		Category categories6 = new Category(6,"Giay dep","hay",true,0);
		Category categories7 = new Category(7,"Nike","hay",true,6);
		Category categories8 = new Category(8,"Adidas","hay",true,6);
		categories.add(categories1);
		categories.add(categories2);
		categories.add(categories3);
		categories.add(categories4);
		categories.add(categories5);
		categories.add(categories6);
		categories.add(categories7);
		categories.add(categories8);
	}
	
	@Override
	public void addOrUpdate(Category category) {
		int indexCheck = findIndexById(category.getCatalogId());
		if (indexCheck < 0) {
			// chức năng thêm mới
			categories.add(category);
			System.out.println("Thêm mới thành công");
		} else {
			// chức năng cập nhật thông tin
			categories.set(indexCheck, category);
			System.out.println("Cập nhật thành công");
		}
	}
	
	@Override
	public void removeById(Integer id) {
		int indexDelete = findIndexById(id);
		if (indexDelete < 0) {
			System.err.println("Không tồn tại danh mục có id = " + id);
			return;
		}
		Boolean hasProductInCategory = ProductFeatureImpl.products.stream().anyMatch(item -> item.getCategory().getCatalogId() == id);
		if(hasProductInCategory) {
			System.err.println("Vẫn còn sản phẩm thuộc category này");
		} else {
			categories.remove(indexDelete);
			System.out.println("Xóa danh mục thành công");
		}
	}
	
	@Override
	public int findIndexById(Integer id) {
		for (int i = 0; i < categories.size(); i++) {
			if (categories.get(i).getCatalogId() == id) {
				return i;
			}
		}
		return -1;
	}
}
