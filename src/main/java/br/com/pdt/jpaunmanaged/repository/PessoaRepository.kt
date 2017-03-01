package br.com.pdt.jpaunmanaged.repository

import br.com.pdt.jpaunmanaged.entity.Pessoa

class PessoaRepository : Repository<Pessoa, Int>(Pessoa::class)