package org.gayathri.services.credit.rest;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.gayathri.services.credit.domain.AccountEntity;
import org.gayathri.services.credit.service.AccountService;

@Path("/accounts")
@Named
public class AccountResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private AccountService accountService;
    
    /**
     * Get the complete list of Account Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /accounts
     * @return List of AccountEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountEntity> getAllAccounts() {
        return accountService.findAllAccountEntities();
    }
    
    /**
     * Get the number of Account Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /accounts/count
     * @return Number of AccountEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return accountService.countAllEntries();
    }
    
    /**
     * Get a Account Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /accounts/3
     * @param id
     * @return A Account Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public AccountEntity getAccountById(@PathParam("id") Long id) {
        return accountService.find(id);
    }
    
    /**
     * Create a Account Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New AccountEntity (JSON) <br/>
     * Example URL: /accounts
     * @param account
     * @return A AccountEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountEntity addAccount(AccountEntity account) {
        return accountService.save(account);
    }
    
    /**
     * Update an existing Account Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated AccountEntity (JSON) <br/>
     * Example URL: /accounts
     * @param account
     * @return A AccountEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountEntity updateAccount(AccountEntity account) {
        return accountService.update(account);
    }
    
    /**
     * Delete an existing Account Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /accounts/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deleteAccount(@PathParam("id") Long id) {
        AccountEntity account = accountService.find(id);
        accountService.delete(account);
    }
    
}
