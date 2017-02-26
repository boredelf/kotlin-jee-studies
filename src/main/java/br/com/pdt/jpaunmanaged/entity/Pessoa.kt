package br.com.pdt.jpaunmanaged.entity

import javax.persistence.*
import javax.persistence.CascadeType.PERSIST
import javax.validation.constraints.Min

@Entity
@Table(name = "PESSOA")
data class Pessoa(
    @Id
    var id: Int?,

    var nome: String?,

    @field:Min(1)
    var idade: Int?,

    @OneToMany(cascade = arrayOf(PERSIST), orphanRemoval = true)
    var telefones: List<Telefone> = emptyList()
) {
    constructor() : this(null, null, null)

    @PrePersist
    fun prePerstist(): Unit = Unit

    @PostPersist
    fun postPersist(): Unit = Unit

}
