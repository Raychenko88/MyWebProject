package com.mainacad.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.mainacad.dao.CartDAO;
import com.mainacad.dao.OrderDAO;
import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Order;
import com.mainacad.model.User;

public class CartService {
    public static Cart updateStatus(Cart cart, Integer closed) {
        return CartDAO.updateStatus(cart, closed);
    }
    private static Logger logger = Logger.getLogger(CartService.class.getName());
    public static Cart addItem(User user, Item item) throws SQLException {
        Cart cart = CartDAO.getByUserAndOpenStatus(user);
        if (cart == null) {
            Long currentTime = new Date().getTime();
            cart = new Cart(0, user.getId(), currentTime);
            CartDAO.save(cart);
        }

        Boolean itemExistInCart = OrderDAO.isItemExistInCart(cart);
        Integer targetOrderId = OrderService.getByCartWithItem(cart, item);
        if (itemExistInCart) {
            Order order = OrderService.getById(targetOrderId);
            OrderService.updateAmount(order,
                    order.getAmount() + 1);  
        } else {
            Order order = new Order(item.getId(), cart.getId(), 1);
            OrderService.create(order);
        }

        return cart;
    }

    public static Cart getByUserAndOpenStatus(User user) {
        return CartDAO.getByUserAndOpenStatus(user);
    }

    public static Cart getById(Integer id) {
        return CartDAO.getById(id);
    }

    public static List<Cart> getAllByUserAndPeriod(User user) {
        return CartDAO.getAllByUserAndPeriod(user, 0L, Long.MAX_VALUE);
    }
}
//if (CartDAO.getById(order.getCartId()) != null && order.getItemId() == OrderDAO.findOrderByItem(order.getItemId()).getItemId()){
//		OrderDAO.updateAmount(order, order.getAmount() + OrderDAO.findOrderByItem(order.getItemId()).getAmount());
//
//		}else {
//
//		}