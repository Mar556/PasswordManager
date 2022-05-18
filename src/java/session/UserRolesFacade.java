/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Role;
import entity.User;
import entity.UserRoles;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Deniss
 */
@Stateless
public class UserRolesFacade extends AbstractFacade<UserRoles> {
    @EJB private RoleFacade roleFacade;

    @PersistenceContext(unitName = "WebBootsShopPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserRolesFacade() {
        super(UserRoles.class);
    }
    
    public boolean isRole(String roleName, User authUser) {
        List<String> listRoleNames = em.createQuery("SELECT ur.role.roleName FROM UserRoles ur WHERE ur.user = :authUser")
                .setParameter("authUser", authUser)
                .getResultList();
        if(listRoleNames.contains(roleName)){
            return true;
        }else{
            return false;
        }
    }
    
    public String getTopRole(User user) {
        List<String> listRoleNames = em.createQuery("SELECT ur.role.roleName FROM UserRoles ur WHERE ur.user = :user")
                .setParameter("user", user)
                .getResultList();
        if(listRoleNames.contains("ADMINISTRATOR"))return "ADMINISTRATOR";
        if(listRoleNames.contains("MANAGER"))return "MANAGER";
        if(listRoleNames.contains("BUYER"))return "BUYER";
        return null;
    }

    public void setRoleToUser(Role role, User user) {
        removeAllUserRoles(user);
        UserRoles userRoles = null;
        if ("ADMINISTRATOR".equals(role.getRoleName())) {
            Role roleBUYER = roleFacade.findByRoleName("BUYER");
            userRoles = new UserRoles();
            userRoles.setRole(roleBUYER);
            userRoles.setUser(user);
            this.create(userRoles);
            Role roleMANAGER = roleFacade.findByRoleName("MANAGER");
            userRoles = new UserRoles();
            userRoles.setRole(roleMANAGER);
            userRoles.setUser(user);
            this.create(userRoles);
            Role roleADMINISRTATOR = roleFacade.findByRoleName("ADMINISTRATOR");
            userRoles = new UserRoles();
            userRoles.setRole(roleADMINISRTATOR);
            userRoles.setUser(user);
            this.create(userRoles);
        }
        if ("MANAGER".equals(role.getRoleName())) {
            Role roleBUYER = roleFacade.findByRoleName("BUYER");
            userRoles = new UserRoles();
            userRoles.setRole(roleBUYER);
            userRoles.setUser(user);
            this.create(userRoles);
            Role roleMANAGER = roleFacade.findByRoleName("MANAGER");
            userRoles = new UserRoles();
            userRoles.setRole(roleMANAGER);
            userRoles.setUser(user);
            this.create(userRoles);
        }
        if ("BUYER".equals(role.getRoleName())) {
            Role roleBUYER = roleFacade.findByRoleName("BUYER");
            userRoles = new UserRoles();
            userRoles.setRole(roleBUYER);
            userRoles.setUser(user);
            this.create(userRoles);
        }
        
    }
    
    private void removeAllUserRoles(User user){
        em.createQuery("DELETE FROM UserRoles ur WHERE ur.user = :user").setParameter("user", user).executeUpdate();
    }
}
