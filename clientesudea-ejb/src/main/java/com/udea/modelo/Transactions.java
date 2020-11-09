/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "TRANSACTIONS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t")
    , @NamedQuery(name = "Transactions.findByTxId", query = "SELECT t FROM Transactions t WHERE t.txId = :txId")
    , @NamedQuery(name = "Transactions.findByTxUserName", query = "SELECT t FROM Transactions t WHERE t.txUserName = :txUserName")
    , @NamedQuery(name = "Transactions.findByTxUserEmail", query = "SELECT t FROM Transactions t WHERE t.txUserEmail = :txUserEmail")
    , @NamedQuery(name = "Transactions.findByTxCardNumber", query = "SELECT t FROM Transactions t WHERE t.txCardNumber = :txCardNumber")
    , @NamedQuery(name = "Transactions.findByTxCardType", query = "SELECT t FROM Transactions t WHERE t.txCardType = :txCardType")
    , @NamedQuery(name = "Transactions.findByTxCardCvc", query = "SELECT t FROM Transactions t WHERE t.txCardCvc = :txCardCvc")
    , @NamedQuery(name = "Transactions.findByTxQrCode", query = "SELECT t FROM Transactions t WHERE t.txQrCode = :txQrCode")
    , @NamedQuery(name = "Transactions.findByTxCardExpiration", query = "SELECT t FROM Transactions t WHERE t.txCardExpiration = :txCardExpiration")
    , @NamedQuery(name = "Transactions.findByTxValue", query = "SELECT t FROM Transactions t WHERE t.txValue = :txValue")})
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TX_ID")
    private Integer txId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TX_USER_NAME")
    private String txUserName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 26)
    @Column(name = "TX_USER_EMAIL")
    private String txUserEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TX_CARD_NUMBER")
    private String txCardNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TX_CARD_TYPE")
    private String txCardType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TX_CARD_CVC")
    private String txCardCvc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "TX_QR_CODE")
    private String txQrCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "TX_CARD_EXPIRATION")
    private String txCardExpiration;
    @Column(name = "TX_VALUE")
    private Integer txValue;

    public Transactions() {
    }

    public Transactions(Integer txId) {
        this.txId = txId;
    }

    public Transactions(Integer txId, String txUserName, String txUserEmail, String txCardNumber, String txCardType, String txCardCvc, String txQrCode, String txCardExpiration) {
        this.txId = txId;
        this.txUserName = txUserName;
        this.txUserEmail = txUserEmail;
        this.txCardNumber = txCardNumber;
        this.txCardType = txCardType;
        this.txCardCvc = txCardCvc;
        this.txQrCode = txQrCode;
        this.txCardExpiration = txCardExpiration;
    }

    public Integer getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
    }

    public String getTxUserName() {
        return txUserName;
    }

    public void setTxUserName(String txUserName) {
        this.txUserName = txUserName;
    }

    public String getTxUserEmail() {
        return txUserEmail;
    }

    public void setTxUserEmail(String txUserEmail) {
        this.txUserEmail = txUserEmail;
    }

    public String getTxCardNumber() {
        return txCardNumber;
    }

    public void setTxCardNumber(String txCardNumber) {
        this.txCardNumber = txCardNumber;
    }

    public String getTxCardType() {
        return txCardType;
    }

    public void setTxCardType(String txCardType) {
        this.txCardType = txCardType;
    }

    public String getTxCardCvc() {
        return txCardCvc;
    }

    public void setTxCardCvc(String txCardCvc) {
        this.txCardCvc = txCardCvc;
    }

    public String getTxQrCode() {
        return txQrCode;
    }

    public void setTxQrCode(String txQrCode) {
        this.txQrCode = txQrCode;
    }

    public String getTxCardExpiration() {
        return txCardExpiration;
    }

    public void setTxCardExpiration(String txCardExpiration) {
        this.txCardExpiration = txCardExpiration;
    }

    public Integer getTxValue() {
        return txValue;
    }

    public void setTxValue(Integer txValue) {
        this.txValue = txValue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (txId != null ? txId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.txId == null && other.txId != null) || (this.txId != null && !this.txId.equals(other.txId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.udea.modelo.Transactions[ txId=" + txId + " ]";
    }
    
    public void buildQrCode(){
        String dateStr = String.valueOf(new Date().getTime());
        this.txQrCode = txUserEmail + txValue.toString() + dateStr;
    }

    public void printInformation() {
        System.out.println("ID: " + txId);
        System.out.println("txUserName: " + txUserName);
        System.out.println("EMAIL: " + txUserEmail);
        System.out.println("CARD NUMBER: " + txCardNumber);
        System.out.println("CARD TYPE: " + txCardType);
        System.out.println("CARD CVC: " + txCardCvc);
        System.out.println("QR CODE: " + txQrCode);
        System.out.println("CARD EXPIRATION: " + txCardExpiration);
        System.out.println("VALUE: " + txValue);
    }

}
