package web.rest.resources.paging;

public class PagingData {

	/* Params provided by user */
	public final int page;
	public final int size;

	/* Calculated values for constructing query */
	public final int firstPos;
	public final int lastPos;

	PagingData(int page, int size) {
		this.page = page;
		this.size = size;

		this.firstPos = (page - 1) * size;
		this.lastPos = page * size;
	}
}
