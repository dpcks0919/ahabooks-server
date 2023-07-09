package com.waywalkers.kbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class KeyDto {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static final class AppleKey{
        private String identityToken;
        private String nonceToken;
    }
}
