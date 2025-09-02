package org.example.crm_back.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`groups`")
@DynamicUpdate
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    public Group(String name) {
        this.name = name;
    }
}
