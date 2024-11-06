package main.java.trade.strategy;

import main.java.trade.model.Trade;

/* The CommissionStrategy interface provides a method to calculate the commission
 * for a given trade. Implementations of this interface will define the specific
 * strategy for calculating the commission for BON, FX and STO trades.
 */
public interface CommissionStrategy {
    double calculate(Trade trade);
}
