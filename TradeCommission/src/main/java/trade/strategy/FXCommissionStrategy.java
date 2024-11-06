package main.java.trade.strategy;

import main.java.trade.model.Trade;

/* FXCommissionStrategy is  strategy for calculating commission on FXtrades.
 * It implements the CommissionStrategy interface and provides specific commission calculation logic
 * for BUY and SELL transactions.
 * 
 * The commission rates and thresholds are definedd as follows:
 * - For BUY transactions, a commission rate of 0.0001 is applied to the trade amount.
 * - For SELL transactions:
 *   - If the trade amount exceeds $1,000,000, a high sell commission of $1,000 is applied.
 *   - If the trade amount exceeds $10,000 but is less than or equal to $1,000,000, a medium sell commission of $100 is applied.
 *   - If the trade amount is less than or equal to $10,000, no commission is applied.
 * 
 * If an unsupported transaction type is encountered, an IllegalArgumentException is thrown.
 */
public class FXCommissionStrategy implements CommissionStrategy {
    private static final double FX_BUY_COMMISSION_RATE = 0.0001;
    private static final double HIGH_SELL_COMMISSION = 1000;
    private static final double MEDIUM_SELL_COMMISSION = 100;
    private static final double HIGH_SELL_THRESHOLD = 1000000;
    private static final double MEDIUM_SELL_THRESHOLD = 10000;

    @Override
    public double calculate(Trade trade) {
        double tradeAmount = trade.getAmount().doubleValue();
        switch (trade.getTransactionType()) {
            case BUY:
                return tradeAmount * FX_BUY_COMMISSION_RATE;
            case SELL:
                if (tradeAmount > HIGH_SELL_THRESHOLD) {
                    return HIGH_SELL_COMMISSION;
                } else if (tradeAmount > MEDIUM_SELL_THRESHOLD) {
                    return MEDIUM_SELL_COMMISSION;
                } else {
                    return 0;
                }
            default:
                throw new IllegalArgumentException("Unsupported transaction type: " + trade.getTransactionType());
        }
    }
}
