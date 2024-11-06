package main.java.trade.model;

import java.math.BigDecimal;

import main.java.trade.model.enums.SecurityType;
import main.java.trade.model.enums.TransactionType;

public class Trade {
    private final String timestamp;
    private final SecurityType securityType;
    private final TransactionType transactionType;
    private final BigDecimal quantity;
    private final BigDecimal price;

    /**
     * Constructs an immutable Trade object.
     *
     * @param timestamp the timestamp of the trade
     * @param securityType the type of security
     * @param transactionType the type of transaction
     * @param quantity the quantity of the trade
     * @param price the price per unit of the trade
     */
    
    public Trade(String timestamp, SecurityType securityType, TransactionType transactionType, BigDecimal quantity, BigDecimal price) {
        this.timestamp = timestamp;
        this.securityType = securityType;
        this.transactionType = transactionType;
        this.quantity = quantity;
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return quantity.multiply(price);
    }

    public SecurityType getSecurityType() {
        return securityType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }
}
