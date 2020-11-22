package pl.sda.finalapp.app.categories.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.sda.finalapp.app.categories.domain.CategoryService;

import java.util.List;

@Controller//klasyczny kontroler zwracający widoki
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")//   /categories?searchText=ewrew
    public String categoriesPage(@RequestParam(required = false) String searchText, Model model) {
        final List<CategoryTreeDTO> categories = categoryService.findCategories(searchText);
        List<CategoryDTO> parentsList = categoryService.findAll();
        model.addAttribute("categoriesData", categories);
        model.addAttribute("searchText", searchText);
        model.addAttribute("parentsList", parentsList);
        return "categoriesPage";
    }

    @PostMapping("/categories")
    public String addCategory(@RequestParam String categoryName, @RequestParam Integer parentId) {
        categoryService.addCategory(categoryName, parentId);
        return "redirect:/categories?searchText=" + categoryName;
    }

    @PostMapping("/moveCategory")//to jest ajax request
    @ResponseBody //to spowoduje zwrócenie jsona
    public ResponseEntity<String> moveCategory(@RequestParam Integer newParentId, @RequestParam Integer movedId){
        categoryService.moveCategory(newParentId, movedId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Udało się");
    }

}