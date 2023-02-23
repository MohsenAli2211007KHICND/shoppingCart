package gul.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Service;
@Service
public class CartServiceImp implements CartService {

    private CartRepository cartRepository;
        


    public CartServiceImp(CartRepository cartRepository) {
            this.cartRepository = cartRepository;
        }



    public Cart saveCart(Cart cart) {
        Optional<Cart> savedCart = cartRepository.findByname(cart.getName());
        if(savedCart.isPresent()){
            throw new InvalidConfigurationPropertyValueException("Name", cart.getName(), "Cart name"+cart.getName()+" is already exist in database");
        }
        return cartRepository.save(cart);
    }
    public Cart getACart(Long id){
        return cartRepository.getReferenceById(id);
    }
    public void deleteACart(Long id){
        cartRepository.deleteById(id);
    }
    public List<Cart> getAllCarts(){
        return cartRepository.findAll();
    }
    public void deleteAllCart(){
        cartRepository.deleteAll();
    }
}
