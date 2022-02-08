package com.ask0n.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AniResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
    private String version = "1";
}
