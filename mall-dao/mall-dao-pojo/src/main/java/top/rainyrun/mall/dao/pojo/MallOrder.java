package top.rainyrun.mall.dao.pojo;

import java.util.Date;

public class MallOrder {
    private String orderId;

    private String payment;

    private Integer status;

    private Date createTime;

    private Date paymentTime;

    private Long userId;
    
    public MallOrder() {
    	super();
    }
    
    public MallOrder(MallOrder order) {
    	super();
    	this.orderId = order.orderId;
    	this.payment = order.payment;
    	this.status = order.status;
    	this.createTime = order.createTime;
    	this.paymentTime = order.paymentTime;
    	this.userId = order.userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}