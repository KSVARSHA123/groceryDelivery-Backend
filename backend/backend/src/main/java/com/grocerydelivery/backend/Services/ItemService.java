package com.grocerydelivery.backend.Services;

import com.grocerydelivery.backend.Models.ItemModel;
import com.grocerydelivery.backend.Repositories.ItemRepository;
import com.grocerydelivery.backend.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductRepository productRepository;

    public List<ItemModel> getItemsByOrderIdCheckCart(Long ORDERID, Long USERID) {
        List<ItemModel> items = itemRepository.findByOrderId(ORDERID);
        for (ItemModel item : items) {
            Long QUANTITY=itemRepository.existsByUserIdAndProductIdWithoutOrderId(USERID, item.getPRODUCTID());
            if (QUANTITY>0) {
                itemRepository.updateQuantity(USERID,item.getPRODUCTID(),QUANTITY);
                itemRepository.deleteItem(USERID,item.getPRODUCTID());
            }
        }
        List<ItemModel> items1=itemRepository.findNoOrderid(USERID);
        for(ItemModel item : items1){
            itemRepository.updateOrderid(ORDERID,USERID);
        }
        return items;
    }

}