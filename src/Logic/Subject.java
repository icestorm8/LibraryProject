package Logic;

public interface Subject {
    // this is the observers listen too (like changes in books, changes in library, changes in members)
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();

}
