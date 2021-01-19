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
public class UserAddress implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键id
     */

      private Integer id;

      /**
     * 用户主键
     */
      private Integer userId;

      /**
     * 地址
     */
      private String address;

      /**
     * 备注
     */
      private String remark;

      /**
     * 是否是默认地址（1:是 0否）
     */
      private Integer isdefault;

      /**
     * 创建时间
     */

      private LocalDateTime createTime;


      private LocalDateTime updateTime;


}
