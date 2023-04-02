package com.poc.store.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poc.store.model.database.Product;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "store_product_type")
@Table(name = "store_product_type")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_type_id")
    private Integer productTypeId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "product_name")
    private String productName;

    @OneToMany(mappedBy = "productType")
    @JsonIgnore
    private List<Product> products;
}
