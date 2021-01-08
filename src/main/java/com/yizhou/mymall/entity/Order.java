package com.yizhou.mymall.entity;


import java.time.LocalDateTime;

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
public class Order implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */

      private Integer id;

      /**
     * 用户主键
     */
      private Integer userId;

      /**
     * 用户名
     */
      private String loginName;

      /**
     * 用户地址
     */
      private String userAddress;

      /**
     * 总金额
     */
      private Float cost;

      /**
     * 订单号
     */
      private String serialnumber;

      /**
     * 创建时间
     */

      private LocalDateTime createTime;

      /**
     * 更新时间
     */

      private LocalDateTime updateTime;


}
