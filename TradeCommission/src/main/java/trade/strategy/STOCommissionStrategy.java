package main.java.trade.strategy;

import main.java.trade.model.Trade;
import main.java.trade.model.enums.TransactionType;

/** Strategy for calculating commission for STO (Sell To Open) transactions.
 * 
 * The commission is calculated based on a fixed rate - 0.05 % applied to the trade amount.
 * If the transaction is a high-value sell (amount greater than $100000), 
 * an additional commission ($500) is added.
 */
public class STOCommissionStrategy implements CommissionStrategy {

    private static final double STO_COMMISSION_RATE = 0.0005;
    private static final double ADDITIONAL_SELL_COMMISSION = 500;
    private static final double HIGH_SELL_THRESHOLD = 100000;

    @Override
    public double calculate(Trade trade) {
        var tradeAmount = trade.getAmount().doubleValue();
        var commission = tradeAmount * STO_COMMISSION_RATE;

        // Check if the transaction is a high-value sell
        var highValueSell = trade.getTransactionType() == TransactionType.SELL && tradeAmount > HIGH_SELL_THRESHOLD;
        if (highValueSell) {
            // Adding additional commission for high-value sell transactions
            commission += ADDITIONAL_SELL_COMMISSION;
        }

        return commission;
    }
}
