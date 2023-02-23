package gul.cart;

import org.junit.jupiter.api.Test;
import org.mockito.invocation.Invocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest
public class CartApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartService cartService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void canCreateACart() throws Exception {
        Cart cart = Cart.builder()
                .id(1L)
                .name("Cart 1")
                .image("Cart 1 image")
                .quantity(48)
                .price(7498502980D)
                .totalPrice(974975920D)
                .build();
        given(cartService.saveCart(any(Cart.class))).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/api/cart/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart)));
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(cart.getName())))
                .andExpect(jsonPath("$.price", is(cart.getPrice())))
                .andExpect(jsonPath("$.id", is(cart.getId().intValue())))
                .andExpect(jsonPath("$.image", is(cart.getImage())))
                .andExpect(jsonPath("$.quantity", is(cart.getQuantity())))
                .andExpect(jsonPath("$.totalPrice", is(cart.getTotalPrice())));
    }

    @Test
    public void canGetACart() throws Exception {
        Long cartId = 1L;
        Cart cart = Cart.builder()
                .id(cartId)
                .name("Cart 1")
                .image("Cart 1 image")
                .quantity(48)
                .price(7498502980D)
                .totalPrice(974975920D)
                .build();
        given(cartService.getACart(cartId)).willReturn(cart);
        ResultActions response = mockMvc.perform(get("/api/cart/{id}", cartId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartId)));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(cart.getId().intValue())))
                .andExpect(jsonPath("$.name", is(cart.getName())))
                .andExpect(jsonPath("$.price", is(cart.getPrice())))
                .andExpect(jsonPath("$.image", is(cart.getImage())))
                .andExpect(jsonPath("$.quantity", is(cart.getQuantity())))
                .andExpect(jsonPath("$.totalPrice", is(cart.getTotalPrice())));
    }

    @Test
    public void canGetAllCart() throws Exception {
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
                .quantity(68)
                .price(64567788D)
                .totalPrice(764474D)
                .build(); 
        List<Cart> carts = List.of(cart1, cart2);
        given(cartService.getAllCarts()).willReturn(carts);
        ResultActions response = mockMvc.perform(get("/api/cart/all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carts)));
        response.andExpect(status().isOk());
        response.andDo(print())
                .andExpect(jsonPath("$.size()", is(carts.size())));

    }
    @Test
    public void canDeleteACart() throws Exception {
        Long cartId = 1L;
        willDoNothing().given(cartService).deleteACart(cartId);

        ResultActions response = mockMvc.perform(delete("/api/cart/{id}", cartId));
        response.andExpect(status().isOk()).andDo(print());
        verify(cartService,times(1)).deleteACart(cartId);
    }
    @Test
    public void canDeleteAllCart() throws Exception {
        willDoNothing().given(cartService).deleteAllCart();;
        ResultActions response = mockMvc.perform(delete("/api/cart/all"));
        response.andExpect(status().isOk()).andDo(print());
        verify(cartService,times(1)).deleteAllCart();
    }
}
