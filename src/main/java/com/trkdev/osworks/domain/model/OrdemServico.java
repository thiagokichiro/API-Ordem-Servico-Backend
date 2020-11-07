package com.trkdev.osworks.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.trkdev.osworks.api.model.Comentario;
import com.trkdev.osworks.domain.exception.NegocioException;

@Entity
public class OrdemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne // Muitas OS para um Cliente
	private Cliente cliente;

	private String descricao;
	private BigDecimal preco;

	@Enumerated(EnumType.STRING)
	private StatusOrdemServico status;

	private OffsetDateTime dataAbertura;
	private OffsetDateTime dataFinalizada;

	@OneToMany(mappedBy = "ordemServico") // Uma Ordem de Serviço tem (muitos) uma lista de Comentários
	private List<Comentario> comentarios = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public StatusOrdemServico getStatus() {
		return status;
	}

	public void setStatus(StatusOrdemServico status) {
		this.status = status;
	}

	public OffsetDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(OffsetDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public OffsetDateTime getDataFinalizada() {
		return dataFinalizada;
	}

	public void setDataFinalizada(OffsetDateTime dataFinalizada) {
		this.dataFinalizada = dataFinalizada;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean podeSerFinalizada() {
		return StatusOrdemServico.ABERTA.equals(getStatus());
	}

	public boolean NaopodeSerFinalizada() {
		return !podeSerFinalizada();
	}

	public void finalizar() {
		if (NaopodeSerFinalizada()) {
			throw new NegocioException("Ordem de Serviço não pode ser finalizada");
		}
		
		setDataFinalizada(OffsetDateTime.now());
		getDataFinalizada();
		setStatus(StatusOrdemServico.FINALIZADA);
		

	}

}
