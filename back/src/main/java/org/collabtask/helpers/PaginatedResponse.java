package org.collabtask.helpers;

public class PaginatedResponse<T> {
    private Pagination page;
    private T data;
    private int totalPage;

    public PaginatedResponse(Pagination page, T data, int totalPage) {
        this.page = page;
        this.data = data;
        this.totalPage = totalPage;
    }

    public Pagination getPage() {
        return page;
    }

    public void setPage(Pagination page) {
        this.page = page;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "PaginatedResponse{" +
                "page=" + page +
                ", data=" + data +
                ", totalPage=" + totalPage +
                '}';
    }
}
