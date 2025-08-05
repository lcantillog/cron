
package com.cron.palatsi.cron.dto;

import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "price",
    "compare_at_price",
    "sku",
    "inventory_quantity"
})
@Generated("jsonschema2pojo")
@Builder
public class VariantJsonDTO {

    @JsonProperty("price")
    private Double price;
    @JsonProperty("compare_at_price")
    private Double compareAtPrice;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("inventory_quantity")
    private Integer inventoryQuantity;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonProperty("price")
    public Double getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Double price) {
        this.price = price;
    }

    @JsonProperty("compare_at_price")
    public Double getCompareAtPrice() {
        return compareAtPrice;
    }

    @JsonProperty("compare_at_price")
    public void setCompareAtPrice(Double compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

    @JsonProperty("sku")
    public String getSku() {
        return sku;
    }

    @JsonProperty("sku")
    public void setSku(String sku) {
        this.sku = sku;
    }

    @JsonProperty("inventory_quantity")
    public Integer getInventoryQuantity() {
        return inventoryQuantity;
    }

    @JsonProperty("inventory_quantity")
    public void setInventoryQuantity(Integer inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
