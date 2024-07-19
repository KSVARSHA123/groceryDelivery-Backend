package com.grocerydelivery.backend.Controllers;

import com.grocerydelivery.backend.Models.ProductModel;
import com.grocerydelivery.backend.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ProductControl {

    @Autowired
    ProductService productService;

    @GetMapping("/getAllproduct")
    public List<ProductModel> getAll(){
        return productService.getAllProduct();
    }

    @PostMapping("/addProduct")
    public void addProduct(@RequestBody ProductModel productModel){
        productService.addProduct(productModel);
    }

    @PutMapping("/updateProductDescPrice/{PRODUCTID}")
    public void updateProduct(@PathVariable Long PRODUCTID, @RequestParam(required = false) String PRODUCTDESCRIPTION,
                              @RequestParam(required = false) Float PRICE){
        if(PRODUCTDESCRIPTION == null){
            productService.updateProductP(PRODUCTID,PRICE);
        }
        else if (PRICE == null){
            productService.updateProductD(PRODUCTID,PRODUCTDESCRIPTION);
        }
        else{
            productService.updateProductDP(PRODUCTID,PRODUCTDESCRIPTION,PRICE);
        }
    }

    @PutMapping("/updateProductStockDate/{PRODUCTID}")
    public void updateProductStockDate(@PathVariable Long PRODUCTID, @RequestParam Long STOCK ,
                              @RequestParam LocalDate M_DATE,@RequestParam LocalDate EXP_DATE){
        productService.updateProductStockDate(PRODUCTID,STOCK,M_DATE,EXP_DATE);
    }

    @DeleteMapping("/deleteProduct/{PRODUCTID}")
    public void removeProduct(@PathVariable Long PRODUCTID){
        productService.removeProduct(PRODUCTID);
    }

    @PutMapping("/addStock/{PRODUCTID}")
    public void addStock(@PathVariable Long PRODUCTID,@RequestParam Long STOCK){
        productService.addStock(PRODUCTID,STOCK);
    }
    @PutMapping("/removeStock/{PRODUCTID}")
    public void removeStock(@PathVariable Long PRODUCTID,@RequestParam Long STOCK){
        productService.removeStock(PRODUCTID,STOCK);
    }



}
