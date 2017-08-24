package org.gayathri.services.credit.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.gayathri.services.credit.domain.AccountEntity;
import org.gayathri.services.credit.service.security.SecurityWrapper;
import org.primefaces.model.SortOrder;

@Named
public class AccountService extends BaseService<AccountEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public AccountService(){
        super(AccountEntity.class);
    }
    
    @Transactional
    public List<AccountEntity> findAllAccountEntities() {
        
        return entityManager.createQuery("SELECT o FROM Account o ", AccountEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Account o", Long.class).getSingleResult();
    }
    
    @Override
    @Transactional
    public AccountEntity save(AccountEntity account) {
        String username = SecurityWrapper.getUsername();
        
        account.updateAuditInformation(username);
        
        return super.save(account);
    }
    
    @Override
    @Transactional
    public AccountEntity update(AccountEntity account) {
        String username = SecurityWrapper.getUsername();
        account.updateAuditInformation(username);
        return super.update(account);
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(AccountEntity account) {

        /* This is called before a Account is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<AccountEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        
        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Account o");
        
        String nextConnective = " WHERE";
        
        Map<String, Object> queryParameters = new HashMap<>();
        
        if (filters != null && !filters.isEmpty()) {
            
            nextConnective += " ( ";
            
            for(String filterProperty : filters.keySet()) {
                
                if (filters.get(filterProperty) == null) {
                    continue;
                }
                
                switch (filterProperty) {
                
                case "name":
                    query.append(nextConnective).append(" o.name LIKE :name");
                    queryParameters.put("name", "%" + filters.get(filterProperty) + "%");
                    break;

                case "surname":
                    query.append(nextConnective).append(" o.surname LIKE :surname");
                    queryParameters.put("surname", "%" + filters.get(filterProperty) + "%");
                    break;

                case "aadharid":
                    query.append(nextConnective).append(" o.aadharid LIKE :aadharid");
                    queryParameters.put("aadharid", "%" + filters.get(filterProperty) + "%");
                    break;

                case "address":
                    query.append(nextConnective).append(" o.address LIKE :address");
                    queryParameters.put("address", "%" + filters.get(filterProperty) + "%");
                    break;

                case "city":
                    query.append(nextConnective).append(" o.city LIKE :city");
                    queryParameters.put("city", "%" + filters.get(filterProperty) + "%");
                    break;

                case "pincode":
                    query.append(nextConnective).append(" o.pincode = :pincode");
                    queryParameters.put("pincode", new Integer(filters.get(filterProperty).toString()));
                    break;

                case "loanamount":
                    query.append(nextConnective).append(" o.loanamount = :loanamount");
                    queryParameters.put("loanamount", new BigDecimal(filters.get(filterProperty).toString()));
                    break;

                case "interestrateperannum":
                    query.append(nextConnective).append(" o.interestrateperannum = :interestrateperannum");
                    queryParameters.put("interestrateperannum", new BigDecimal(filters.get(filterProperty).toString()));
                    break;

                case "compoundingperiodinmonths":
                    query.append(nextConnective).append(" o.compoundingperiodinmonths = :compoundingperiodinmonths");
                    queryParameters.put("compoundingperiodinmonths", new Integer(filters.get(filterProperty).toString()));
                    break;

                }
                
                nextConnective = " AND";
            }
            
            query.append(" ) ");
            nextConnective = " AND";
        }
        
        if (sortField != null && !sortField.isEmpty()) {
            query.append(" ORDER BY o.").append(sortField);
            query.append(SortOrder.DESCENDING.equals(sortOrder) ? " DESC" : " ASC");
        }
        
        TypedQuery<AccountEntity> q = this.entityManager.createQuery(query.toString(), this.getType());
        
        for(String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
