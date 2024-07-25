package Logic;
// this is the observers listen too (like changes in books, changes in library, changes in members)
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
