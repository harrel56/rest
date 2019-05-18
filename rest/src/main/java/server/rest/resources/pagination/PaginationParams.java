package server.rest.resources.pagination;

public class PaginationParams {

    /* Params provided by user */
    public final int page;
    public final int size;

    PaginationParams(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getFirstPos() {
        return (this.page - 1) * this.size;
    }
}
