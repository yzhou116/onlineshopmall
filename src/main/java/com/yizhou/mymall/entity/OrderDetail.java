package com.yizhou.mymall.entity;


import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yizhou
 * @since 2021-01-02
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class OrderDetail implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */

      private Integer id;

      /**
     * 订单主键
     */
      private Integer orderId;

      /**
     * 商品主键
     */
      private Integer productId;

      /**
     * 数量
     */
      private Integer quantity;

      /**
     * 消费
     */
      private Float cost;


}