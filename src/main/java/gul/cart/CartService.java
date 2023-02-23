package gul.cart;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Configuration;

public interface CartService {
    Cart saveCart(Cart cart);
    Cart getACart(Long id);
    void deleteACart(Long id);
    List<Cart> getAllCarts();
    void deleteAllCart();
}
