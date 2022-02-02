package monster.helloworld.gdflbd.domain;

public class Order {
    private Integer id;
    private Integer orderId;
    private Integer userId;
    // private Integer billId; // 这里不需要 billId ，应该是 bill 表里面有 orderId
    private Float totalMoney;
    private Integer areaId;
    private Integer tradeSrc;
    private Integer payStatus;
    private Integer orderLifeCycle;
    private Integer orderStatus;
    private String createTime;
    private String payTime;
    private String modifiedTime;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", totalMoney=" + totalMoney +
                ", areaId=" + areaId +
                ", tradeSrc=" + tradeSrc +
                ", payStatus=" + payStatus +
                ", orderLifeCycle=" + orderLifeCycle +
                ", orderStatus=" + orderStatus +
                ", createTime='" + createTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", modifiedTime='" + modifiedTime + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getTradeSrc() {
        return tradeSrc;
    }

    public void setTradeSrc(Integer tradeSrc) {
        this.tradeSrc = tradeSrc;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public Integer getOrderLifeCycle() {
        return orderLifeCycle;
    }

    public void setOrderLifeCycle(Integer orderLifeCycle) {
        this.orderLifeCycle = orderLifeCycle;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
