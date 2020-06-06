package com.baka.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baka.models.Product;
import com.baka.services.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	//Form for adding product
	@GetMapping("/add")
	public String productForm(Model model) {
		
		model.addAttribute("product", new Product());
		return "addProduct";
	}
	
	//Add new product
	@PostMapping("/add")
	public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAtt) {
		
		if(bindingResult.hasErrors()) {
			
			return "addProduct";
		}
		productService.create(product);
			
		//Uploading image
		MultipartFile productImage = product.getImage();
		try {
			byte[] bytes = productImage.getBytes();
			String name = product.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
			stream.write(bytes);
			stream.close();
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		redirectAtt.addFlashAttribute("created", true);
		return "redirect:/product/productList";
		
		
	}
	
	//Show list of products
	@GetMapping("/productList")
	public String productList(Model model) {
		
		List<Product> products = productService.findAll();
		model.addAttribute("products", products);
		return "productList";
	}
	//Show product info
	@GetMapping("/productInfo")
	public String productInfo(@RequestParam Long id, Model model) {
		
		Product product = productService.findById(id).orElse(new Product());
		model.addAttribute("product", product);
		return "productInfo";
	}
	
	//Update product get method
	@GetMapping("/editProduct")
	public String editProductForm(@RequestParam Long id, Model model) {
		
		Product product = productService.findById(id).orElse(new Product());
		model.addAttribute("product", product);
		return "editProduct";
	}
	//Update product post method
	@PostMapping("/editProduct")
	public String editProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, HttpServletRequest req, RedirectAttributes redirectAtt) {
		
		if(bindingResult.hasErrors()) {
			
			return "editProduct";
		}
		
		productService.create(product);
		MultipartFile image = product.getImage();
		if(!image.isEmpty()) {
			
			try {
				byte[] bytes = image.getBytes();
				String name = product.getId() + ".png";
				String path = "src/main/resources/static/image/product/" + name;
				Files.delete(Paths.get(path));
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(path)));
				stream.write(bytes);
				stream.close();
				
			} catch(Exception ex) {
				
				ex.printStackTrace();
			}
		}
		redirectAtt.addFlashAttribute("updated", true);
		return "redirect:/product/productInfo?id=" + product.getId();
	}
	
	//Delete product by id
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam(value = "id", required = false) Long id, RedirectAttributes redirectAtt) {
		
		productService.deleteById(id);
		redirectAtt.addFlashAttribute("deleted", true);
		return "redirect:/product/productList";
	}
	
	
}

