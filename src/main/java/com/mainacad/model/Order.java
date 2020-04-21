package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;
    private Integer itemId;
    private Integer cartId;
    private Integer amount;

    public Order(int itemId, int cartId, Integer amount) {
        this.itemId = itemId;
        this.cartId = cartId;
        this.amount = amount;
    }
}
