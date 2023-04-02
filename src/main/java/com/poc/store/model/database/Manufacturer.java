package com.poc.store.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity(name = "store_manufacturer")
@Table(name = "store_manufacturer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manufacturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "manufacturer_id")
    private Integer manufacturerId;

    @Size(min = 1, max = 100)
    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "manufacturer_phone")
    private String manufacturerPhone;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "manufacturer_address")
    private String manufacturerAddress;

    @OneToMany(mappedBy = "productManufacturer")
    @JsonIgnore
    private List<Product> products;

}
