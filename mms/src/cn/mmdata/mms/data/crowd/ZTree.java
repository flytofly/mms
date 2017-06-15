package cn.mmdata.mms.data.crowd;

import java.util.ArrayList;
import java.util.List;

public class ZTree {
	private String name;
	private String title;
	private String url;
	private List<ZTree> children=new ArrayList<ZTree>();
	private boolean checked;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<ZTree> getChildren() {
		return children;
	}

	public void setChildren(List<ZTree> children) {
		this.children = children;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "ZTree [name=" + name + ", title=" + title + ", url=" + url
				+ ", children=" + children + ", checked=" + checked + "]";
	}
}
