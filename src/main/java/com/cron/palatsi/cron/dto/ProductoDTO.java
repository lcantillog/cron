
package com.cron.palatsi.cron.dto;


import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "product"
})
@Generated("jsonschema2pojo")
@Builder
public class ProductoDTO {

    @JsonProperty("product")
    private ProductJsonDTO product;

    @JsonProperty("product")
    public ProductJsonDTO getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(ProductJsonDTO product) {
        this.product = product;
    }

}
