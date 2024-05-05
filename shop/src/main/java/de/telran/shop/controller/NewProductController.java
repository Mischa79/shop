package de.telran.shop.controller;

import de.telran.shop.entity.Product;
import de.telran.shop.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewProductController {

    @Autowired
    ProductsRepository productsRepository;

    // http://localhost:8080/create -> create-product.html
    @GetMapping("/create")
    public String create(Product product, Model model)
    {
        model.addAttribute("product", product);
        return "create-product";
    }
}
