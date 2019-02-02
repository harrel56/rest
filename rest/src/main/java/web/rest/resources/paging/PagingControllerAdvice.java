package web.rest.resources.paging;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@ControllerAdvice
public class PagingControllerAdvice {

	@ModelAttribute
	public void handlePaging(Model model, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {

		model.addAttribute("pagingData", new PagingData(page, size));
	}
}
