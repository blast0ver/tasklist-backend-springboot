package com.romansholokh.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class TaskSearchValues
{
    private String title;
    private Integer completed;
    private Long priorityId;
    private Long categoryId;
//    Pagination
    private Integer pageNumber;
    private Integer pageSize;
//    Sorting
    private String sortColumn;
    private String sortDirection;
}