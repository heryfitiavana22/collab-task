import { z, type ZodTypeAny } from "zod";

export const PaginationSchema = z.object({
  page: z.number().nullable().optional(),
  size: z.number().nullable().optional(),
});

const PaginatedResponseSchema = z.object({
  page: z.object({
    page: z.number(),
    size: z.number(),
  }),
  totalPage: z.number(),
});

export function withPagination<T extends ZodTypeAny>(data: z.ZodArray<T>) {
  return PaginatedResponseSchema.merge(
    z.object({
      data,
    })
  );
}

export type Pagination<T = {}> = z.infer<typeof PaginationSchema> & T;

export type Paginated<T extends any[]> = z.infer<
  typeof PaginatedResponseSchema
> & {
  data: T;
};
