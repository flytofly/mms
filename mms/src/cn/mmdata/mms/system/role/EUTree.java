package cn.mmdata.mms.system.role;

import java.util.List;

public interface EUTree<T extends EUTree<T>> {
	public String getId();

	public List<T> getChildren();
}
