package org.collabtask.helpers;

import org.jboss.resteasy.reactive.RestQuery;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    @RestQuery
    protected int page = 0;
    @RestQuery
    protected int size;
    public static int DEFAULT_SIZE = 25;

    public Pagination(int page) {
        this.page = page;
    }

    public int getSize() {
        return size == 0 ? DEFAULT_SIZE : size;
    }
}
