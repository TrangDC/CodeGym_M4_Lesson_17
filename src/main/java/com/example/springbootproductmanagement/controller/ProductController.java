package com.example.springbootproductmanagement.controller;

import com.example.springbootproductmanagement.model.Product;
import com.example.springbootproductmanagement.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping()
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        Iterable<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showFormAdd() {
        ModelAndView modelAndView = new ModelAndView("product/edit");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("title", "Add");
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Product product,
                         RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Product added successfully");
        return "redirect:/api/products";
    }

    @GetMapping("/update/{id}")
    public ModelAndView showFormUpdate(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("product/edit");
            modelAndView.addObject("product", product);
            modelAndView.addObject("title", "Update");
            return modelAndView;
        }
        return new ModelAndView("/error_404");
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute Product product,
                         RedirectAttributes redirectAttributes) {
        productService.save(product);
        redirectAttributes.addFlashAttribute("message", "Product updated successfully");
        return "redirect:/api/products";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showFormDelete(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("product/delete");
            modelAndView.addObject("product", product);
            modelAndView.addObject("title", "Delete");
            return modelAndView;
        }
        return new ModelAndView("/error_404");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        productService.remove(id);
        redirectAttributes.addFlashAttribute("message", "Delete product successfully");
        return "redirect:/api/products";
    }

}
