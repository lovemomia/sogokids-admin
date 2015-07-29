package cn.momia.admin.web.entity;

import java.util.List;

/**
 * Created by hoze on 15/6/15.
 */
public class Sku {

    private int id;
    private int productId;
    private String properties;
    private String prices;
    private int type;//0普通sku 1赠送类sku
    private int limit;//限购
    private int needRealName;//实名认证 0不需要 1需要
    private int stock;
    private int unlockedStock;
    private int lockedStock;
    private String startTime;
    private String endTime;
    private int onWeekend;
    private String onlineTime;
    private String offlineTime;
    private int status;
    private String addTime;
    private String updateTime;
    private SkuOther skuOther;
    private String desc;

    private String propertiesValue;
    private List<SkuPrice> skuPrices;
    private String producttitle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getNeedRealName() {
        return needRealName;
    }

    public void setNeedRealName(int needRealName) {
        this.needRealName = needRealName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getUnlockedStock() {
        return unlockedStock;
    }

    public void setUnlockedStock(int unlockedStock) {
        this.unlockedStock = unlockedStock;
    }

    public int getLockedStock() {
        return lockedStock;
    }

    public void setLockedStock(int lockedStock) {
        this.lockedStock = lockedStock;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getOnWeekend() {
        return onWeekend;
    }

    public void setOnWeekend(int onWeekend) {
        this.onWeekend = onWeekend;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public SkuOther getSkuOther() {
        return skuOther;
    }

    public void setSkuOther(SkuOther skuOther) {
        this.skuOther = skuOther;
    }

    public String getPropertiesValue() {
        return propertiesValue;
    }

    public void setPropertiesValue(String propertiesValue) {
        this.propertiesValue = propertiesValue;
    }

    public List<SkuPrice> getSkuPrices() {
        return skuPrices;
    }

    public void setSkuPrices(List<SkuPrice> skuPrices) {
        this.skuPrices = skuPrices;
    }

    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }
}
