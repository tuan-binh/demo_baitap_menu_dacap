package ra.business.feature.impl;

import ra.business.entity.domain.Product;
import ra.business.feature.IProductFeature;

import java.util.ArrayList;
import java.util.List;

public class ProductFeatureImpl implements IProductFeature {
	public static List<Product> products = new ArrayList<>();
	
	@Override
	public void addOrUpdate(Product product) {
		int indexCheck = findIndexById(product.getProductId());
		if (indexCheck < 0) {
			products.add(product);
			System.out.println("Thêm mới sản phẩm thành công");
		} else {
			products.set(indexCheck, product);
			System.out.println("Cập nhật sản phẩm thành công");
		}
	}
	
	@Override
	public void removeById(String id) {
		int indexDelete = findIndexById(id);
		if (indexDelete < 0) {
			System.err.println("Không tồn tại sản phẩm có id = " + id);
			return;
		}
		products.remove(indexDelete);
		System.out.println("Xóa sản phẩm thành công");
	}
	
	@Override
	public int findIndexById(String id) {
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getProductId().equals(id)) {
				return i;
			}
		}
		return -1;
	}
}
