package br.com.pdt.jpaunmanaged.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TELEFONE")
data class Telefone(
    @Id var id: Int,
    var numero: String
)