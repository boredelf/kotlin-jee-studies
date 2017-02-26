package br.com.pdt.jpaunmanaged.entity

import javax.persistence.*
import javax.persistence.CascadeType.PERSIST
import javax.persistence.GenerationType.SEQUENCE
import javax.validation.constraints.Min

@Entity
@Table(name = "PESSOA")
data class Pessoa private constructor(
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "PESSOA_SEQ_GEN")
    @SequenceGenerator(name = "PESSOA_SEQ_GEN", sequenceName = "PESSOA_SEQ")
    var id: Int?,

    var nome: String?,

    @field:Min(1)
    var idade: Int?,

    @OneToMany(cascade = arrayOf(PERSIST), orphanRemoval = true)
    var telefones: List<Telefone> = emptyList()
) {
    constructor() : this(null, null, null)
    constructor(nome: String, idade: Int) : this(null, nome, idade)
    constructor(nome: String, idade: Int, telefones: List<Telefone>) : this(null, nome, idade, telefones)
}
