import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Observer {
    void update(double price);
}

// Concrete Observer: Trader
class Trader implements Observer {
    private String name;
    private TradingStrategy strategy;

    public Trader(String name, TradingStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    @Override
    public void update(double price) {
        strategy.trade(price, name);
    }
}

// Subject interface
interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(double price);
}

// Concrete Subject: Stock
class Stock implements Subject {
    private double price;
    private List<Observer> observers;

    public Stock() {
        this.observers = new ArrayList<>();
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers(price);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(double price) {
        for (Observer observer : observers) {
            observer.update(price);
        }
    }
}

// Strategy interface
interface TradingStrategy {
    void trade(double price, String traderName);
}

// Concrete Strategy: AggressiveTradingStrategy
class AggressiveTradingStrategy implements TradingStrategy {
    @Override
    public void trade(double price, String traderName) {
        System.out.println(traderName + " is trading aggressively at price: " + price);
    }
}

// Concrete Strategy: ConservativeTradingStrategy
class ConservativeTradingStrategy implements TradingStrategy {
    @Override
    public void trade(double price, String traderName) {
        System.out.println(traderName + " is trading conservatively at price: " + price);
    }
}

// Main class to demonstrate the pattern
public class StockTradingApp {

    public static void main(String[] args) {
        // Create a stock
        Stock stock = new Stock();

        // Create traders with different strategies
        Trader trader1 = new Trader("John", new AggressiveTradingStrategy());
        Trader trader2 = new Trader("Alice", new ConservativeTradingStrategy());

        // Register traders as observers to the stock
        stock.registerObserver(trader1);
        stock.registerObserver(trader2);

        // Simulate price changes
        stock.setPrice(100.0);
        stock.setPrice(105.0);
        stock.setPrice(95.0);

        // Unregister a trader
        stock.removeObserver(trader2);

        // Simulate more price changes
        stock.setPrice(110.0);
    }
}
