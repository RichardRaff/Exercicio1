/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Richard Raff
 */
@Entity
@Table(name = "contato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contato.findAll", query = "SELECT c FROM Contato c")
    , @NamedQuery(name = "Contato.findByIdcontato", query = "SELECT c FROM Contato c WHERE c.idcontato = :idcontato")
    , @NamedQuery(name = "Contato.findByNome", query = "SELECT c FROM Contato c WHERE c.nome = :nome")
    , @NamedQuery(name = "Contato.findBySobrenome", query = "SELECT c FROM Contato c WHERE c.sobrenome = :sobrenome")
    , @NamedQuery(name = "Contato.findByEmail", query = "SELECT c FROM Contato c WHERE c.email = :email")
    , @NamedQuery(name = "Contato.findByTelefone", query = "SELECT c FROM Contato c WHERE c.telefone = :telefone")})
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontato")
    private Integer idcontato;
    @Column(name = "nome")
    private String nome;
    @Column(name = "sobrenome")
    private String sobrenome;
    @Column(name = "email")
    private String email;
    @Column(name = "telefone")
    private String telefone;
    @Column(name="visibilidade")
    private int visibilidade;

    public Contato() {
    }

    public Contato(Integer idcontato) {
        this.idcontato = idcontato;
    }

    public Integer getIdcontato() {
        return idcontato;
    }

    public void setIdcontato(Integer idcontato) {
        this.idcontato = idcontato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontato != null ? idcontato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contato)) {
            return false;
        }
        Contato other = (Contato) object;
        if ((this.idcontato == null && other.idcontato != null) || (this.idcontato != null && !this.idcontato.equals(other.idcontato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Contato[ idcontato=" + idcontato + " ]";
    }

    public int getVisibilidade() {
        return visibilidade;
    }

    public void setVisibilidade(int visibilidade) {
        this.visibilidade = visibilidade;
    }
    
}
