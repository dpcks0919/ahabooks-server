package com.waywalkers.kbook.domain.key;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Key {

    @JsonProperty("kty")
    private String kty;
    @JsonProperty("kid")
    private String kid;
    @JsonProperty("use")
    private String use;
    @JsonProperty("alg")
    private String alg;
    @JsonProperty("n")
    private String n;
    @JsonProperty("e")
    private String e;
}