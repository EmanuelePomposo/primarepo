package it.begear.betalent.masterdata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Persona implements Serializable {


    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name ="id", nullable = false)
    private Integer id;



    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Temporal(TemporalType.DATE)
    @Column(name = "ddn")
    private Date ddn;

    @Column( name = "eta")
    private  Integer eta;



}
