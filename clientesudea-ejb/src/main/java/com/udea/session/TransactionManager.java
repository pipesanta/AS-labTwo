/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.session;

import com.udea.modelo.Transactions;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author
 */
@Stateless
public class TransactionManager implements TransactionManagerLocal {

    @PersistenceContext(unitName = "com.udea_clientesudea-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    
    /*
    @Resource
    private javax.transaction.UserTransaction utx;
    */
     
    @Override
    public void addTransaction(Transactions T) {
        
        String queryStr = "INSERT INTO TRANSACTIONS (TX_USER_NAME, TX_USER_EMAIL, TX_CARD_NUMBER, "
                + "TX_CARD_TYPE, TX_CARD_CVC, TX_QR_CODE, TX_CARD_EXPIRATION, TX_VALUE) "
                + "VALUES("
                + "'"+ T.getTxUserName() +"', "
                + "'"+ T.getTxUserEmail() +"', "
                + "'"+ T.getTxCardNumber() +"', "
                + "'"+ T.getTxCardType() +"', "
                + "'"+ T.getTxCardCvc() +"', "
                + "'"+ T.getTxQrCode() +"', "
                + "'"+ T.getTxCardExpiration() +"', "
                + + T.getTxValue()
                +")";
      
        System.out.println("QUERY ==> " + queryStr);
        
        em.createNativeQuery(queryStr).executeUpdate();

    }

    @Override
    public List<Transactions> getAllTransactions() {
        Query query = em.createNamedQuery("Transactions.findAll");
        return query.getResultList();
    }

}
