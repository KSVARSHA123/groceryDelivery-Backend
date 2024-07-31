package com.grocerydelivery.backend.Services;

import com.grocerydelivery.backend.Models.ProductModel;
import com.grocerydelivery.backend.Repositories.ItemRepository;
import com.grocerydelivery.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
     private ProductRepository productRepository;
    @Autowired
    ItemRepository itemRepository;

    public List<ProductModel> getAllProduct(){
        return (List<ProductModel>) productRepository.findAll();
    }

    public ProductModel addProduct(ProductModel productModel){
        return productRepository.save(productModel);
    }

    public void updateProductP(Long PRODUCTID,Float PRICE ){
        productRepository.updateProductP(PRODUCTID,PRICE);
    }

    public void updateProductD(Long PRODUCTID,String PRODUCTDESCRIPTION){
        productRepository.updateProductD(PRODUCTID,PRODUCTDESCRIPTION);
    }

    public void updateProductDP(Long PRODUCTID,String PRODUCTDESCRIPTION,Float PRICE ){
        productRepository.updateProductDP(PRODUCTID,PRODUCTDESCRIPTION,PRICE);
    }

    public void updateProductStockDate(Long PRODUCTID,Long STOCK, LocalDate M_DATE,LocalDate EXP_DATE){
        productRepository.updateProductStockDate(PRODUCTID,STOCK,M_DATE,EXP_DATE);
    }

    public void removeProduct(Long PRODUCTID){
        productRepository.deleteById(PRODUCTID);
    }

    public void addStock(Long PRODUCTID,Long STOCK){
        productRepository.addStock(PRODUCTID,STOCK);
    }

    public void removeStock(Long PRODUCTID, Long STOCK){
        productRepository.removeStock(PRODUCTID,STOCK);
    }

    public void addToCart(Long USERID, Long PRODUCTID, Long QUANTITY) {
        Float PRICE = productRepository.getPRICE(PRODUCTID);
        if (itemRepository.existsByUserIdProductId(USERID, PRODUCTID)) {
            itemRepository.updateQuantity(USERID, PRODUCTID, QUANTITY);
        } else {
            itemRepository.saveItem(PRODUCTID, QUANTITY, PRICE, USERID);
        }
    }


}
