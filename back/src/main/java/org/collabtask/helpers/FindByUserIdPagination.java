package org.collabtask.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FindByUserIdPagination extends Pagination {
    protected String userId;

    public FindByUserIdPagination(int page, String userId) {
        super(page);
        this.userId = userId;
    }
}
