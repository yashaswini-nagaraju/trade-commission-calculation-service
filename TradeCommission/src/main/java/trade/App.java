package main.java.trade;

import java.math.BigDecimal;
import java.util.*;

import main.java.trade.model.Trade;
import main.java.trade.model.enums.SecurityType;
import main.java.trade.model.enums.TransactionType;
import main.java.trade.service.TradeCommissionService;

public class App {
    public static void main(String[] args) {
        // Sample data
        var trades = Arrays.asList(
                new Trade("01/01/2019 10:00:00", SecurityType.STO, TransactionType.BUY, BigDecimal.valueOf(1000), BigDecimal.valueOf(12)),
                new Trade("01/01/2019 11:00:00", SecurityType.BON, TransactionType.SELL, BigDecimal.valueOf(500), BigDecimal.valueOf(200)),
                new Trade("01/01/2019 12:00:00", SecurityType.FX, TransactionType.BUY, BigDecimal.valueOf(10000), BigDecimal.valueOf(1.5)),
                new Trade("01/01/2019 13:00:00", SecurityType.BON, TransactionType.SELL, BigDecimal.valueOf(2000), BigDecimal.valueOf(5)),
                new Trade("01/01/2019 14:00:00", SecurityType.STO, TransactionType.BUY, BigDecimal.valueOf(1500), BigDecimal.valueOf(10)),
                new Trade("01/01/2019 15:00:00", SecurityType.BON, TransactionType.SELL, BigDecimal.valueOf(700), BigDecimal.valueOf(250)),
                new Trade("01/01/2019 16:00:00", SecurityType.FX, TransactionType.BUY, BigDecimal.valueOf(20000), BigDecimal.valueOf(2)),
                new Trade("01/01/2019 17:00:00", SecurityType.STO, TransactionType.SELL, BigDecimal.valueOf(3000), BigDecimal.valueOf(8)),
                new Trade("01/01/2019 18:00:00", SecurityType.BON, TransactionType.BUY, BigDecimal.valueOf(1000), BigDecimal.valueOf(150))
        );

        TradeCommissionService service = new TradeCommissionService(10);
        var totalCommission = 0.0;

        // Calculate and print commission for each trade
        for (Trade trade : trades) {
            var commission = service.calculateCommission(trade);
            totalCommission += commission;
            System.out.printf("Timestamp of trade: %s, Commission: $%.2f%n", trade.getTimestamp(), commission);
        }

        // Print total commission for all trades
        System.out.printf("Total Commission for all trades: $%.2f%n", totalCommission);
    }
}
