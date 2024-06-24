package ra.business.feature;

public interface IBaseFeature<T,E> {
	void addOrUpdate(T t);
	void removeById(E id);
	int findIndexById(E id);
}
