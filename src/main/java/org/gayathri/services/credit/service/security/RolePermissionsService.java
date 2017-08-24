package org.gayathri.services.credit.service.security;

import java.io.Serializable;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.gayathri.services.credit.domain.security.RolePermission;
import org.gayathri.services.credit.service.BaseService;

@Named
public class RolePermissionsService extends BaseService<RolePermission> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public RolePermissionsService(){
        super(RolePermission.class);
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM RolePermission o", Long.class).getSingleResult();
    }

}
