/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Contato;

/**
 *
 * @author Richard Raff
 */
public class ContatoJpaController implements Serializable {

    public ContatoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contato contato) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contato contato) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contato = em.merge(contato);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contato.getIdcontato();
                if (findContato(id) == null) {
                    throw new NonexistentEntityException("The contato with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try { //em é entitymanager
            em = getEntityManager();
            em.getTransaction().begin();
            Contato contato;
            try {
                contato = em.getReference(Contato.class, id);
                contato.getIdcontato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contato with id " + id + " no longer exists.", enfe);
            }
            // em.remove(contato);
            contato.setVisibilidade(0);
            em.merge(contato);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contato> findContatoEntities() {
        return findContatoEntities(true, -1, -1);
    }

    public List<Contato> findContatoEntities(int maxResults, int firstResult) {
        return findContatoEntities(false, maxResults, firstResult);
    }

    private List<Contato> findContatoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contato.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Contato findContato(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contato.class, id);
        } finally {
            em.close();
        }
    }

    public int getContatoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contato> rt = cq.from(Contato.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public List<Contato> findContatoByName(String nome) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery("SELECT c FROM Contato c WHERE c.nome = :nome");
        q.setParameter("nome", nome);
        List result = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return result;
    }

}
