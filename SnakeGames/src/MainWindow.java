import javax.swing.*;

public class MainWindow extends JFrame { //Основной класс от которого наследуется каждый класс который хочет быть окном
    public MainWindow() { //Конструктор главного окна
        setTitle("Змейка"); //Название окна
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// Закрытие окна при нажатии на кремстки
        setSize(320,345); //Размер окна
        setLocation(400,400); // Расположение окна на дисплеии компьютера
        add(new GameField()); // Добавление класса в конструктор
        setVisible(true); //Видимость окна
    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow(); // Создание экземпляра окна
    }

}
