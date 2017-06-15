package cn.mmdata.mms.system.role;

import java.util.ArrayList;
import java.util.List;

public class EUTreeModule implements EUTree<EUTreeModule> {

	private String id;
	private String text;
	private String pid;
	private List<EUTreeModule> children=new ArrayList<EUTreeModule>();
	private boolean checked;
	private String moduleLayerMark;
	public String getModuleLayerMark() {
		return moduleLayerMark;
	}
	public void setModuleLayerMark(String moduleLayerMark) {
		this.moduleLayerMark = moduleLayerMark;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<EUTreeModule> getChildren() {
		return children;
	}
	public void setChildren(List<EUTreeModule> children) {
		this.children = children;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EUTreeModule other = (EUTreeModule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	



}
