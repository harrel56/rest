package server.rest.resources.pagination;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PaginationWrapper<T extends Serializable> implements Serializable {

    private final int currentPage;
    private final int pageSize;
    private final int totalPages;
    private final long totalResults;
    private final List<T> results;

    private PaginationWrapper(List<T> results, PaginationParams params, long totalCount) {
        this.results = results;
        this.totalResults = totalCount;
        this.currentPage = params.page;
        this.pageSize = params.size;
        this.totalPages = (int) Math.ceil(totalCount / (double) params.size);
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public long getTotalResults() {
        return this.totalResults;
    }

    public List<T> getResults() {
        return this.results;
    }

    public static <T extends Serializable> PaginationWrapper<T> wrap(List<T> results, PaginationParams params, long totalCount) {
        return new PaginationWrapper<>(results, params, totalCount);
    }
}
