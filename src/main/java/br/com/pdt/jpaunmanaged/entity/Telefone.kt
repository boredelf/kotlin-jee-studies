package br.com.pdt.jpaunmanaged.entity

import javax.persistence.*

@Entity
@Table(name = "TELEFONE")
data class Telefone private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEFONE_SEQ_GEN")
    @SequenceGenerator(name = "TELEFONE_SEQ_GEN", sequenceName = "TELEFONE_SEQ")
    var id: Int?,

    var numero: String
) {
    constructor(numero: String) : this(null, numero)
}