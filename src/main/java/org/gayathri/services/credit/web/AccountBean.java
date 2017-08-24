package org.gayathri.services.credit.web;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.gayathri.services.credit.domain.AccountEntity;
import org.gayathri.services.credit.service.AccountService;
import org.gayathri.services.credit.service.security.SecurityWrapper;
import org.gayathri.services.credit.web.generic.GenericLazyDataModel;
import org.gayathri.services.credit.web.util.MessageFactory;

@Named("accountBean")
@ViewScoped
public class AccountBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(AccountBean.class.getName());
    
    private GenericLazyDataModel<AccountEntity> lazyModel;
    
    private AccountEntity account;
    
    @Inject
    private AccountService accountService;
    
    public void prepareNewAccount() {
        reset();
        this.account = new AccountEntity();
        // set any default values now, if you need
        // Example: this.account.setAnything("test");
    }

    public GenericLazyDataModel<AccountEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(accountService);
        }
        return this.lazyModel;
    }
    
    public String persist() {

        if (account.getId() == null && !isPermitted("account:create")) {
            return "accessDenied";
        } else if (account.getId() != null && !isPermitted(account, "account:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (account.getId() != null) {
                account = accountService.update(account);
                message = "message_successfully_updated";
            } else {
                account = accountService.save(account);
                message = "message_successfully_created";
            }
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(account, "account:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            accountService.delete(account);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));
        
        return null;
    }
    
    public void onDialogOpen(AccountEntity account) {
        reset();
        this.account = account;
    }
    
    public void reset() {
        account = null;

    }

    public AccountEntity getAccount() {
        if (this.account == null) {
            prepareNewAccount();
        }
        return this.account;
    }
    
    public void setAccount(AccountEntity account) {
        this.account = account;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(AccountEntity account, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
