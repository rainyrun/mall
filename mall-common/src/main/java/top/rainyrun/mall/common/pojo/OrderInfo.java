package top.rainyrun.mall.common.pojo;

import java.io.Serializable;
import java.util.List;

import top.rainyrun.mall.dao.pojo.*;

public class OrderInfo extends MallOrder implements Serializable {
	private List<MallOrderItem> orderItems;//订单项
	
	public OrderInfo() {
		super();
	}
	
	public OrderInfo(MallOrder order) {
		super(order);
		this.orderItems = null;
	}
	
	public List<MallOrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<MallOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Override
	public String toString() {
		return "OrderInfo [orderItems=" + orderItems + "]";
	}
}
