package pl.sda.finalapp.app.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.finalapp.app.categories.domain.CategoryService;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products (Model model){
        model.addAttribute("productTypesList", ProductType.values());
        model.addAttribute("categoriesList", categoryService.findAll());
        return "productsPage";
    }

}