package com.coppel.dto;

import com.coppel.entities.Empleado;
import com.coppel.entities.Inventario;
import com.coppel.entities.Polizas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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
