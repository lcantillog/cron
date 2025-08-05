
package com.cron.palatsi.cron.dto;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "variants"
})
@Generated("jsonschema2pojo")
@Builder
public class ProductJsonDTO {

    @JsonProperty("id")
    private String id;
    @JsonProperty("variants")
    private List<VariantJsonDTO> variants;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("variants")
    public List<VariantJsonDTO> getVariants() {
        return variants;
    }

    @JsonProperty("variants")
    public void setVariants(List<VariantJsonDTO> variants) {
        this.variants = variants;
    }
}
