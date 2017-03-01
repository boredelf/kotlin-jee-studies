package br.com.pdt.jpaunmanaged.entity

import javax.persistence.*
import javax.validation.constraints.Pattern

@Entity
@Table(name = "TELEFONE")
data class Telefone private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TELEFONE_SEQ_GEN")
    @SequenceGenerator(name = "TELEFONE_SEQ_GEN", sequenceName = "TELEFONE_SEQ", allocationSize = 1)
    var id: Int?,

    @get:Pattern(regexp = "\\d{4,5}-\\d{4}")
    var numero: String
) {
    constructor(numero: String) : this(null, numero)
}