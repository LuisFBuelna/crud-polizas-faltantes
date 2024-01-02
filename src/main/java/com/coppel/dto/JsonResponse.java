package com.coppel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse {

    private Meta meta;
    private Object data;


    @lombok.Data
    public static class Meta {
        private String status;

        public Meta(String status) {
            this.status = status;
        }
    }
}
