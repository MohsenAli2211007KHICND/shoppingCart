package gul.cart;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart createNewProduct(@RequestBody Cart cart){
        return cartService.saveCart(cart);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cart> getACart(@PathVariable Long id ){
        Cart cart = cartService.getACart(id);
        if(cart.getId() != null){
            return ResponseEntity.status(HttpStatus.OK).body(cart);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/all")
    public List<Cart> getAllCart(){
        return cartService.getAllCarts();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") Long id){
        cartService.deleteACart(id);
        return new ResponseEntity<String>("Cart deleted successfully!", HttpStatus.OK);
    }
    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllCart(){
        cartService.deleteAllCart();;
        return new ResponseEntity<String>("Carts deleted successfully!", HttpStatus.OK);
    }

}
