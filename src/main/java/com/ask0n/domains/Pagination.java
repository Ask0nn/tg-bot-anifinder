package com.ask0n.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> {
    private Integer currentPage;
    private Integer count;
    private List<T> documents;
    private Integer lastPage;
}
