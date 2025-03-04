package org.collabtask.helpers;

public class FindByUserIdPagination extends Pagination {
    protected String userId;

    public FindByUserIdPagination(int page, int size, String userId) {
        super(page, size);
        this.userId = userId;
    }

    public FindByUserIdPagination(int page, String userId) {
        super(page);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FindByUserId{" +
                "userId='" + userId + '\'' +
                ", page=" + getPage() +
                ", size=" + getSize() +
                '}';
    }
}
