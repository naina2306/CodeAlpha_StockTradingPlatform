import java.util.*;

public class StockTradingPlatform {

    // Stock class
    static class Stock {
        String symbol;
        String name;
        double price;

        Stock(String symbol, String name, double price) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
        }

        void updatePrice(double newPrice) {
            this.price = newPrice;
        }
    }

    // User class
    static class User {
        String name;
        double balance;
        Map<String, Integer> portfolio;

        User(String name, double balance) {
            this.name = name;
            this.balance = balance;
            this.portfolio = new HashMap<>();
        }

        void buyStock(Stock stock, int quantity) {
            double cost = stock.price * quantity;
            if (balance >= cost) {
                balance -= cost;
                portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
                System.out.println("Bought " + quantity + " shares of " + stock.symbol);
            } else {
                System.out.println(" Not enough balance to buy.");
            }
        }

        void sellStock(Stock stock, int quantity) {
            int owned = portfolio.getOrDefault(stock.symbol, 0);
            if (owned >= quantity) {
                balance += stock.price * quantity;
                portfolio.put(stock.symbol, owned - quantity);
                System.out.println(" Sold " + quantity + " shares of " + stock.symbol);
            } else {
                System.out.println("You don't own enough shares to sell.");
            }
        }

        void showPortfolio(Map<String, Stock> marketStocks) {
            System.out.println("\n Portfolio of " + name);
            System.out.println("💰 Balance: ₹" + balance);
            if (portfolio.isEmpty()) {
                System.out.println("You have no stocks yet.");
                return;
            }
            double totalValue = balance;
            for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
                String symbol = entry.getKey();
                int quantity = entry.getValue();
                double currentPrice = marketStocks.get(symbol).price;
                System.out.println("• " + symbol + ": " + quantity + " shares (₹" + currentPrice + " each)");
                totalValue += quantity * currentPrice;
            }
            System.out.println("Total Portfolio Value: ₹" + totalValue);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Market stock data
        Map<String, Stock> marketStocks = new HashMap<>();
        marketStocks.put("TCS", new Stock("TCS", "Tata Consultancy Services", 3750));
        marketStocks.put("INFY", new Stock("INFY", "Infosys", 1450));
        marketStocks.put("RELI", new Stock("RELI", "Reliance Industries", 2700));
        marketStocks.put("WIPRO", new Stock("WIPRO", "Wipro Ltd", 430));
        marketStocks.put("HDFC", new Stock("HDFC", "HDFC Bank", 1680));

        // Create user
        System.out.print("Enter your name to start trading: ");
        String userName = sc.nextLine();
        User user = new User(userName, 10000); // Starting balance ₹10,000

        // Menu loop
        while (true) {
            System.out.println("\n=== 📉 Stock Trading Platform ===");
            System.out.println("1️⃣  View Market Data");
            System.out.println("2️⃣  Buy Stock");
            System.out.println("3️⃣  Sell Stock");
            System.out.println("4️⃣  View Portfolio");
            System.out.println("5️⃣  Exit");
            System.out.print("Choose an option (1-5): ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n📦 Available Stocks:");
                    for (Stock stock : marketStocks.values()) {
                        System.out.println(stock.symbol + " - " + stock.name + " - ₹" + stock.price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = sc.next().toUpperCase();
                    if (marketStocks.containsKey(buySymbol)) {
                        System.out.print("Enter quantity: ");
                        int qtyBuy = sc.nextInt();
                        user.buyStock(marketStocks.get(buySymbol), qtyBuy);
                    } else {
                        System.out.println(" Invalid stock symbol.");
                    }
                    break;

                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = sc.next().toUpperCase();
                    if (marketStocks.containsKey(sellSymbol)) {
                        System.out.print("Enter quantity: ");
                        int qtySell = sc.nextInt();
                        user.sellStock(marketStocks.get(sellSymbol), qtySell);
                    } else {
                        System.out.println(" Invalid stock symbol.");
                    }
                    break;

                case 4:
                    user.showPortfolio(marketStocks);
                    break;

                case 5:
                    System.out.println(" Exiting the platform. Happy Trading!");
                    sc.close();
                    return;

                default:
                    System.out.println"Invalid option. Try again.");
            }
        }
    }
}
