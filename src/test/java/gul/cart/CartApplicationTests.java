package gul.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CartApplicationTests {

	@Mock
	private CartRepository cartRepository;
	@InjectMocks
	private CartServiceImp cartServiceImp;

	@Test
	void contextLoads() {
	}

	@Test
	void getAndSetCarttId() {
		Long id = 1L;
		Cart cart = new Cart();
		cart.setId(id);
		assertEquals(id, cart.getId());
	}

	@Test
	void getAndSetCartName() {
		String name = "cart 1";
		Cart cart = new Cart();
		cart.setName(name);
		assertEquals(name, cart.getName());
	}

	@Test
	void getAndSetCartImage() {
		String image = "cart 1 image";
		Cart cart = new Cart();
		cart.setImage(image);
		assertEquals(image, cart.getImage());
	}

	@Test
	void getAndSetCartQuantity() {
		int quantity = 23;
		Cart cart = new Cart();
		cart.setQuantity(quantity);
		assertEquals(quantity, cart.getQuantity());
	}

	@Test
	void getAndSetCartPrice() {
		Double price = 238990D;
		Cart cart = new Cart();
		cart.setPrice(price);
		assertEquals(price, cart.getPrice());
	}

	@Test
	void getAndSetCartTotalPrice() {
		Double totalPrice = 238994346670D;
		Cart cart = new Cart();
		cart.setTotalPrice(totalPrice);
		assertEquals(totalPrice, cart.getTotalPrice());
	}

	@Test
	void getAllArgsConstructorOfCart() {
		Long id = 1L;
		String name = "cart 1";
		String image = "cart 1 image";
		int quantity = 23;
		Double price = 238990D;
		Double totalPrice = 238994346670D;
		Cart cart = new Cart(id, name, image, quantity, price, totalPrice);
		assertEquals(id, cart.getId());
		assertEquals(name, cart.getName());
		assertEquals(image, cart.getImage());
		assertEquals(quantity, cart.getQuantity());
		assertEquals(price, cart.getPrice());
		assertEquals(totalPrice, cart.getTotalPrice());
	}

	@Test
	void cartBuilder() {
		Long id = 1L;
		String name = "cart 1";
		String image = "cart 1 image";
		int quantity = 23;
		Double price = 238990D;
		Double totalPrice = 238994346670D;
		Cart cart = Cart.builder()
				.id(id)
				.name(name)
				.image(image)
				.quantity(quantity)
				.price(price)
				.totalPrice(totalPrice)
				.build();
		assertEquals(id, cart.getId());
		assertEquals(name, cart.getName());
		assertEquals(image, cart.getImage());
		assertEquals(quantity, cart.getQuantity());
		assertEquals(price, cart.getPrice());
		assertEquals(totalPrice, cart.getTotalPrice());
	}

	@Test
	void canSaveCart() {
		Long id = 1L;
		String name = "cart 1";
		String image = "cart 1 image";
		int quantity = 23;
		Double price = 238990D;
		Double totalPrice = 238994346670D;
		Cart cart = Cart.builder()
				.id(id)
				.name(name)
				.image(image)
				.quantity(quantity)
				.price(price)
				.totalPrice(totalPrice)
				.build();
		given(cartRepository.findByname(name)).willReturn(Optional.empty());
		given(cartRepository.save(cart)).willReturn(cart);
		Cart saveCart = cartServiceImp.saveCart(cart);
		assertNotNull(saveCart);

	}

	@Test
	void canGetACart() {
		Long id = 1L;
		String name = "cart 1";
		String image = "cart 1 image";
		int quantity = 23;
		Double price = 238990D;
		Double totalPrice = 238994346670D;
		Cart cart = Cart.builder()
				.id(id)
				.name(name)
				.image(image)
				.quantity(quantity)
				.price(price)
				.totalPrice(totalPrice)
				.build();
		given(cartRepository.getReferenceById(id)).willReturn(cart);
		Cart gottonCart = cartServiceImp.getACart(cart.getId());
		assertNotNull(gottonCart);
		assertEquals(gottonCart.getId(), cart.getId());
		assertEquals(gottonCart.getName(), cart.getName());
		assertEquals(gottonCart.getImage(), cart.getImage());
		assertEquals(gottonCart.getPrice(), cart.getPrice());
		assertEquals(gottonCart.getQuantity(), cart.getQuantity());
		assertEquals(gottonCart.getTotalPrice(), cart.getTotalPrice());

	}
	@Test
	void canGetAllCart() {
		Cart cart1 = Cart.builder()
				.id(1L)
				.name("Cart 1")
				.image("Cart 1 image")
				.quantity(48)
				.price(7498502980D)
				.totalPrice(974975920D)
				.build();
		Cart cart2 = Cart.builder()
				.id(2L)
				.name("Cart 2")
				.image("Cart 2 image")
				.quantity(58)
				.price(74985080D)
				.totalPrice(9775920D)
				.build();
		List<Cart> carts = List.of(cart1, cart2);
		given(cartRepository.findAll()).willReturn(List.of(cart1, cart2));
		List<Cart> cart = cartServiceImp.getAllCarts();
		assertNotNull(cart);
		assertEquals(cart.size(), 2);	
	}
	@Test
	void canDeleteACart(){
		Long id = 1L;
		willDoNothing().given(cartRepository).deleteById(id);
		cartServiceImp.deleteACart(id);
		verify(cartRepository, times(1)).deleteById(id);
	}

	@Test
	void canDeleteAllCart(){
		willDoNothing().given(cartRepository).deleteAll();
		cartServiceImp.deleteAllCart();
		verify(cartRepository, times(1)).deleteAll();
	}
	
}
