package org.collabtask.helpers;

import org.jboss.resteasy.reactive.RestQuery;

public class Pagination {
    @RestQuery
    protected int page;
    @RestQuery
    protected int size;
    public static int DEFAULT_SIZE = 25;

    public Pagination() {
    }

    public Pagination(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public Pagination(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size == 0 ? DEFAULT_SIZE : size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
