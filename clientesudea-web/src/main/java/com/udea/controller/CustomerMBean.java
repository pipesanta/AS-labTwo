/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.udea.controller;

import com.udea.modelo.FormError;
import com.udea.modelo.Transactions;
import com.udea.session.TransactionManagerLocal;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Usuario
 */
public class CustomerMBean implements Serializable {

    @EJB
    private TransactionManagerLocal transactionManager;

    private List<Transactions> allTransactions; //para visualizar en la tabla

    private Transactions transaction = new Transactions(); //para mostrar, actualizar o insertar en el formulario

    ArrayList<FormError> formErrors = new ArrayList<FormError>();

    public CustomerMBean() {
        transaction.setTxValue(500);

    }

    //retorna una lista de clientes para mostrar en un datatable de JSF
    public List<Transactions> getAllTransactions() {
        if ((allTransactions == null) || (allTransactions.isEmpty())) {
            refresh();
        }
        return allTransactions;
    }

    private void refresh() {
        allTransactions = transactionManager.getAllTransactions();
    }

    
    public String list() {
        System.out.println("###LIST###");
        return "LIST";
    }

    public String newTransaction() {
        System.out.println("###NEW_TX###");
        transaction = new Transactions();
        transaction.setTxValue(0);
        return "NEW_TX";
    }

    public String create() {

        if (!formErrors.isEmpty()) {
            System.out.println("HAY UN ERRO CON LASVALIDACIONES");
            return null;
        }

        transaction.buildQrCode();

        System.out.println("----------- SE VA A INSERTAR ------");
        transaction.printInformation();
        transactionManager.addTransaction(transaction);
        return "CREATED";

    }

    // validar la tipo de tarjeta
    public String identifyCArdType(String cardNumber) {
        /**
         * • American Express: Rango del 11111 al 22222 • Diners: Rango del
         * 33334 al 44444 • Visa: Rango del 55555 al 66666 • Mastercard: Rango
         * de 77777 al 88888
         */
        int cardAsInt;

        if (cardNumber.length() < 5) {
            return null;
        }

        try {
            cardAsInt = Integer.parseInt(cardNumber.substring(0, 5));
            System.out.println("###" + cardAsInt);

        } catch (Exception e) {
            System.out.println("ERROR_INVALID");
            return null;
        }

        if (cardAsInt >= 11111 && cardAsInt <= 22222) {
            return "American_Express".toUpperCase();
        }

        if (cardAsInt >= 33334 && cardAsInt <= 44444) {
            return "Diners".toUpperCase();
        }

        if (cardAsInt >= 55555 && cardAsInt <= 66666) {
            return "Visa".toUpperCase();
        }

        if (cardAsInt >= 77777 && cardAsInt <= 88888) {
            return "Mastercard".toUpperCase();
        }

        return null;

    }

    // validar la fecha de vencimiento de la tarjeta
    public Boolean checkDate(String yy, String mm) {
        String sDate1 = "01/" + mm + "/" + yy;

        long nowDateMillis = new Date().getTime();
        long cardDateMillis = 0;

        try {
            Date cardDate = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
            cardDateMillis = cardDate.getTime();

        } catch (ParseException ex) {
            Logger.getLogger(CustomerMBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (cardDateMillis > nowDateMillis);

    }

    // LISTENERS
    public void oncardNumberChange() {
        System.out.println("oncardNumberChange ==> " + transaction.getTxCardNumber());
        String cardType = identifyCArdType(transaction.getTxCardNumber());
        transaction.setTxCardType(cardType);
        String ERROR_KEY = "INVALID_CARD_NUMBER";
        if (cardType == null) {
            FormError error = new FormError(ERROR_KEY, "EL NUMERO DE LA TARJETA NO ES VALIDO");
            this.insertErrorForm(error);
        } else {
            removeErrorForm(ERROR_KEY);
        }
    }

    public void onCardExpirationChange() {

        String expStr = transaction.getTxCardExpiration();

        System.out.println("onCardExpirationChange ==> " + expStr);

        String mm;
        String yy;

        if (expStr.length() == 7) {
            mm = expStr.substring(0, 2);
            yy = expStr.substring(3, 7);

            System.out.println("MM: " + mm);
            System.out.println("YYYY: " + yy);

            String ERROR_KEY = "EXPIRATION_DATE";
            if (!checkDate(yy, mm)) {
                FormError err = new FormError(ERROR_KEY, "TARJETA EXPIRADA");
                insertErrorForm(err);
            } else {
                this.removeErrorForm(ERROR_KEY);
            }

        }

    }

    public void onTransactionAmountChange() {
        int amount = transaction.getTxValue();
        String ERROR_KEY = "INVALID_AMOUNT";
        String errorMsg = "Valor min: 500; Max: 10.000";
        if (amount < 500 || amount > 10000) {
            FormError error = new FormError(ERROR_KEY, errorMsg);
            insertErrorForm(error);
        } else {
            removeErrorForm(ERROR_KEY);
        }

    }

    public void onCvcCardChange() {
        String cvc = transaction.getTxCardCvc();
        String ERROR_KEY = "INVALID_CVC";
        String errorMsg = "El CVC debe tener tres digitos";
        if (cvc.length() != 3) {
            FormError error = new FormError(ERROR_KEY, errorMsg);
            insertErrorForm(error);
        } else {
            removeErrorForm(ERROR_KEY);
        }

    }

    public void insertErrorForm(FormError e) {
        System.out.println("insertErrorForm");
        FormError exist = findFormErrorByKey(e.getKey());
        if (exist == null) {
            formErrors.add(e);
        }
        printErrors();

    }

    public void printErrors() {
        String list = "[";
        for (FormError e : formErrors) {
            list += e.getKey() + ", ";
        }
        list += "]";
        System.out.println(list);
    }

    public void removeErrorForm(String key) {
        System.out.println("removeErrorForm(" + key + ")");
        ArrayList<FormError> result = new ArrayList<>();
        for (FormError e : formErrors) {
            if (!(e.getKey().equals(key))) {
                result.add(e);
            }
        }
        formErrors = result;
        printErrors();
    }

    public FormError findFormErrorByKey(String key) {
        for (FormError e : formErrors) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }

    // GETTERS AND SETTERS
    public Transactions getDetails() {
        return transaction;
    }

    public List<FormError> getFormErrors() {
        return formErrors;
    }

    public Transactions getTransaction() {
        return transaction;
    }

}
