package org.gayathri.services.credit.service.security;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.gayathri.services.credit.domain.security.RolePermission;
import org.gayathri.services.credit.domain.security.UserRole;

@Singleton
@Startup
public class RolePermissionsPublisher {

    private static final Logger logger = Logger.getLogger(RolePermissionsPublisher.class.getName());
    
    @Inject
    private RolePermissionsService rolePermissionService;
    
    @PostConstruct
    public void postConstruct() {

        if (rolePermissionService.countAllEntries() == 0) {

            rolePermissionService.save(new RolePermission(UserRole.Administrator, "account:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "account:read"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "account:update"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "account:delete"));
            
            rolePermissionService.save(new RolePermission(UserRole.Registered, "account:create"));
            
            rolePermissionService.save(new RolePermission(UserRole.Registered, "account:read"));
            
            rolePermissionService.save(new RolePermission(UserRole.Administrator, "user:*"));
            
            logger.info("Successfully created permissions for user roles.");
        }
    }
}
