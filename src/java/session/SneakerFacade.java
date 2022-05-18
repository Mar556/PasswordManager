/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Sneaker;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Deniss
 */
@Stateless
public class SneakerFacade extends AbstractFacade<Sneaker> {

    @PersistenceContext(unitName = "WebBootsShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SneakerFacade() {
        super(Sneaker.class);
    }
    
}
