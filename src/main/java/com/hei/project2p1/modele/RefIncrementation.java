package com.hei.project2p1.modele;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"ref_incrementetion\"")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RefIncrementation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private int inccremeentaionEmployee;
}
