package top.rainyrun.mall.common.pojo;

import java.io.Serializable;
import java.util.List;

public class EasyUITreeNode implements Serializable {
	Long id;
	String text;
	String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "EasyUITreeNode [id=" + id + ", text=" + text + ", state=" + state + "]";
	}
	
}
