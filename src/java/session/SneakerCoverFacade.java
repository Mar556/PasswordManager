/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.AbstractFacade;
import entity.Sneaker;
import entity.SneakerCover;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Deniss
 */
@Stateless
public class SneakerCoverFacade extends AbstractFacade<SneakerCover> {

    @PersistenceContext(unitName = "WebBootsShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SneakerCoverFacade() {
        super(SneakerCover.class);
    }

    public SneakerCover findCoverBySneaker(Sneaker sneaker) {
        try {
            return (SneakerCover) em.createQuery("SELECT bc FROM SneakerCover bc WHERE bc.sneaker=:sneaker")
                    .setParameter("sneaker", sneaker).getSingleResult();
        } catch (Exception e) {
            return new SneakerCover();
        }
    }
    
}
