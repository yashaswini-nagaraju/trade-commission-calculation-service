package main.java.trade.service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import main.java.trade.model.Trade;
import main.java.trade.model.enums.SecurityType;
import main.java.trade.strategy.BONCommissionStrategy;
import main.java.trade.strategy.CommissionStrategy;
import main.java.trade.strategy.FXCommissionStrategy;
import main.java.trade.strategy.STOCommissionStrategy;

/**
 * A service class for calculating trade commissions based on different security types.
 * It supports multi-threaded commission calculations using an ExecutorService.
 */
public class TradeCommissionService {

    // A map of security types to their corresponding commission strategies.
    private final Map<SecurityType, CommissionStrategy> strategies = new EnumMap<>(SecurityType.class);

    //Executor service for handling commission calculations in parallel.
    private final ExecutorService executor;

    public TradeCommissionService() {
        this(10); // Default to a maximum of 10 threads
    }

    public TradeCommissionService(int threadPoolSize) {
        executor = Executors.newFixedThreadPool(threadPoolSize);
        strategies.put(SecurityType.STO, new STOCommissionStrategy());
        strategies.put(SecurityType.BON, new BONCommissionStrategy());
        strategies.put(SecurityType.FX, new FXCommissionStrategy());
    }

    //Calculates the commission for a given trade.
    public double calculateCommission(Trade trade) {
        SecurityType securityType = trade.getSecurityType();
        CommissionStrategy commissionStrategy = strategies.get(securityType);
        if (commissionStrategy != null) {
            return commissionStrategy.calculate(trade);
        }
        throw new IllegalArgumentException("TradeCommissionService: Unknown security type: " + securityType);
    }

    /**
     * Calculates the total commission for a list of trades.
     * This method uses multi-threading to calculate commissions in parallel.
     */
    public double calculateTotalCommission(List<Trade> trades) {
        List<Future<Double>> futures = trades.stream()
                .map(trade -> executor.submit(() -> calculateCommission(trade)))
                .collect(Collectors.toList());

        double totalCommission = futures.stream()
                .mapToDouble(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("Error calculating commission", e);
                    }
                }).sum();

        return totalCommission;
    }

    public void shutdownExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}