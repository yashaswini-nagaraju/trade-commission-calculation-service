package main.java.trade.strategy;

import java.math.BigDecimal;
import main.java.trade.model.Trade;

/**
 * The BONCommissionStrategy class implements the CommissionStrategy interface
 * & provides the logic to calculate the commission for a trade based on 
 * whether it is a buy or sell transaction.
 * 
 * The commission rates are as follows:
 *
 *  1. BUY transactions: 0.02%
 *  2. SELL transactions: 0.01%
 */
public class BONCommissionStrategy implements CommissionStrategy {
    private static final double BUY_COMMISSION_RATE = 0.0002;
    private static final double SELL_COMMISSION_RATE = 0.0001;

    @Override
    public double calculate(Trade trade) {
        if (trade == null || trade.getAmount() == null || trade.getTransactionType() == null) {
            throw new IllegalArgumentException("Trade, trade amount, and transaction type must not be null");
        }

        var tradeAmount = trade.getAmount();
        var commissionRate = switch (trade.getTransactionType()) {
            case BUY -> BigDecimal.valueOf(BUY_COMMISSION_RATE);
            case SELL -> BigDecimal.valueOf(SELL_COMMISSION_RATE);
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + trade.getTransactionType());
        };

        return tradeAmount.multiply(commissionRate).doubleValue();
    }
}
