package pl.sda.finalapp.app.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sda.finalapp.app.categories.domain.CategoryService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String products(@RequestParam(required = false) String searchText,
                           @RequestParam(required = false) ProductType productType,
                           @RequestParam(required = false) Integer categoryId,
                           @RequestParam(required = false ,defaultValue = "1") Integer page,
                           @RequestParam(required = false, defaultValue = "5") Integer size,
                           Model model) {
        Page<ProductListDTO> products = productService.allProducts(searchText, productType, categoryId,page,size);
        model.addAttribute("productsPage", products);
        int totalPages = products.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("searchText", searchText);
        model.addAttribute("productType", productType);
        model.addAttribute("categoryId", categoryId);
        String s = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .scheme("http")
                .queryParam("searchText", searchText)
                .queryParam("productType", productType)
                .queryParam("categoryId", categoryId)
                .build().toUriString();
        model.addAttribute("urlBegin", s);
        model.addAttribute("productTypesList", ProductType.values());
        model.addAttribute("categoriesList", categoryService.findAll());
        return "productsPage";
    }

    @PostMapping(value = "/products")
    public String addProduct(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam String pictureUrl,
                             @RequestParam BigDecimal price,
                             @RequestParam ProductType productType,
                             @RequestParam Integer categoryId) {
        productService.addProduct(new ProductDTO(title, description, pictureUrl, price, productType, categoryId));
        return "redirect:/products";
    }

    @GetMapping("/products/{id}")
    public String editForm(@PathVariable(name = "id") Integer productId, Model model){
        model.addAttribute("product", productService.findProductById(productId));
        model.addAttribute("productTypesList", ProductType.values());
        model.addAttribute("categoriesList", categoryService.findAll());
        return "productEditPage";
    }

    @PostMapping("/products/{id}")
    public String editProduct (@ModelAttribute ProductDTO product, @PathVariable(name = "id") Integer productId){
        productService.editProduct(product);
        return "redirect:/products";
    }
}