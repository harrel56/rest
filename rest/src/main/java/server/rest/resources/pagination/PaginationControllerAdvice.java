package server.rest.resources.pagination;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@ControllerAdvice
public class PaginationControllerAdvice {

    @ModelAttribute
    public void handlePagination(Model model, @RequestParam(name = "page_number", required = false, defaultValue = "1") int page,
                                 @RequestParam(name = "page_size", required = false, defaultValue = "10") int size) {

        model.addAttribute("paginationParams", new PaginationParams(page, size));
    }
}
