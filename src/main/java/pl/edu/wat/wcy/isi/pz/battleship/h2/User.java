package pl.edu.wat.wcy.isi.pz.battleship.h2;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    String userName;

    @Column
    String password;
}
