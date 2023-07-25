package com.hei.project2p1.modele;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "\"company\"")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String name;
    private String Description;
    @Column(name = "logo", columnDefinition = "bytea")
    private byte[] logo;
    private String Slogan;
    private String address;
    private String email;
    private String stat;
    private String rcs;
    @OneToMany(mappedBy = "company")
    private List<PhoneNumber> phoneNumbers;
}
