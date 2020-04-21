package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Integer id;
    private Integer closed;
    private Integer userId;
    private Long creationTime;

    public Cart(Integer closed, Integer userId, Long creationTime) {
        this.closed = closed;
        this.userId = userId;
        this.creationTime = creationTime;
    }
}
