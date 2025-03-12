package org.collabtask.helpers;

import java.util.List;

import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private Pagination page;
    private List<T> data;
    private int totalPage;

    public static <E extends ConvertibleToClient<C>, C> Uni<PaginatedResponse<C>> paginate(
            Uni<List<E>> find,
            Uni<Long> total,
            Pagination pagination) {

        return Uni.combine().all().unis(find, total).asTuple()
                .map(tuple -> {
                    int totalPage = (int) Math.ceil(tuple.getItem2().doubleValue() / pagination.getSize());
                    return new PaginatedResponse<>(
                            new Pagination(pagination.getPage(), pagination.getSize()),
                            tuple.getItem1().stream().map(E::toClient).toList(),
                            totalPage);
                });
    }
}
