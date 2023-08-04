package com.wrathyboo.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
   private Integer id;
   private String image;
   private String name;
   private Integer price;
   private Integer sale;
   private Integer quantity;
   private Integer total;
   private Integer cartId;
}
