package com.jumpstart.food_ordering_system.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private int statusCode;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> Response<T> success(T data, String message) {
        return Response.<T>builder()
                .statusCode(200)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}