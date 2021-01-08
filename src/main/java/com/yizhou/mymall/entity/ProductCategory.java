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
public class ProductCategory implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * 主键
     */

      private Integer id;

      /**
     * 名称
     */
      private String name;

      /**
     * 父级目录id
     */
      private Integer parentId;

      /**
     * 级别(1:一级 2：二级 3：三级)
     */
      private Integer type;

  public ProductCategory(Integer id, String name, Integer parentId, Integer type) {
    this.id = id;
    this.name = name;
    this.parentId = parentId;
    this.type = type;
  }
}
