package org.gayathri.services.credit.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name="Account")
@Table(name="\"ACCOUNT\"")
@XmlRootElement
public class AccountEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    @Column(length = 100, name="\"name\"")
    @NotNull
    private String name;

    @Size(max = 50)
    @Column(length = 50, name="\"surname\"")
    private String surname;

    @Size(max = 30)
    @Column(length = 30, name="\"aadharid\"")
    private String aadharid;

    @Size(max = 200)
    @Column(length = 200, name="\"address\"")
    private String address;

    @Size(max = 50)
    @Column(length = 50, name="\"city\"")
    private String city;

    @Column(name="\"pincode\"")
    @Digits(integer = 4, fraction = 0)
    private Integer pincode;

    @Digits(integer = 10,  fraction = 2)
    @Column(precision = 12, scale = 2, name="\"loanamount\"")
    @NotNull
    private BigDecimal loanamount;

    @Digits(integer = 5,  fraction = 2)
    @Column(precision = 7, scale = 2, name="\"interestrateperannum\"")
    @NotNull
    private BigDecimal interestrateperannum;

    @Column(name="\"compoundingperiodinmonths\"")
    @Digits(integer = 4, fraction = 0)
    @NotNull
    private Integer compoundingperiodinmonths;

    @Column(name = "ENTRY_CREATED_BY")
    private String createdBy;

    @Column(name = "ENTRY_CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "ENTRY_MODIFIED_BY")
    private String modifiedBy;

    @Column(name = "ENTRY_MODIFIED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAadharid() {
        return this.aadharid;
    }

    public void setAadharid(String aadharid) {
        this.aadharid = aadharid;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPincode() {
        return this.pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public BigDecimal getLoanamount() {
        return this.loanamount;
    }

    public void setLoanamount(BigDecimal loanamount) {
        this.loanamount = loanamount;
    }

    public BigDecimal getInterestrateperannum() {
        return this.interestrateperannum;
    }

    public void setInterestrateperannum(BigDecimal interestrateperannum) {
        this.interestrateperannum = interestrateperannum;
    }

    public Integer getCompoundingperiodinmonths() {
        return this.compoundingperiodinmonths;
    }

    public void setCompoundingperiodinmonths(Integer compoundingperiodinmonths) {
        this.compoundingperiodinmonths = compoundingperiodinmonths;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }
    
    public void updateAuditInformation(String username) {
        if (this.getId() != null) {
            modifiedAt = new Date();
            modifiedBy = username;
        } else {
            createdAt = new Date();
            modifiedAt = createdAt;
            createdBy = username;
            modifiedBy = createdBy;
        }
    }
    
}
