import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameField extends JPanel implements ActionListener { // Панель на которой будет происходить основныедействия игры и имплиминтируется метод в котром будут происходит управления
    private final int size = 320; // Размер поля
    private final int DOT_SIZE = 16; // Размер ячейки в пикселях занимаймой змейкой и яблока
    private final int ALL_DOTS = 400; // Количество игровых едениц которые могут поместится в игровом поле
    private Image dot; // Переменная в которой храниться изображение змейки
    private Image apple; // Переменная в которой храниться изображение яюлока
    private int appleX; // Позиция яблока по оси Х
    private int appleY; // Позиция яблока по оси Y
    private int[] x = new int [ALL_DOTS]; // Массив для хранения всех позиций змекйки по оси Х
    private int[] y = new int[ALL_DOTS]; // Массив для хранения всех позиций змекйки по оси Y
    private int dots; // Размер змейки в данный момент времени
    private boolean left = false; // Для направление движения змейки
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame =true; // Проверка того не проиграли ли мы

    public GameField(){ // Игровое поле

        setBackground(Color.BLACK); // Установка заднего чёрного фона
        loadImages(); // Вызов метода для загрузки картинок
        intGame(); // Вызв конструктора
        addKeyListener(new FieldKeyListener());// Обработчик событий
        setFocusable(true);
        }

    public void intGame(){ // Метод инициализации в игре
        dots = 3; // Колличество начальных точек змейки
        for (int i = 0; i < dots; i++){
           x[i] = 48 - i * DOT_SIZE; // Начальная позиция змейки, а все остальные сдивагаются вправо
           y[i] = 48; // Начальная позиция по Y
        }
        // Таймер?? но для чего
        Timer timer; // 250 - частота с которой он будет "тикать "
        timer = new Timer(250, this);
        timer.start(); // Запуск таймера
        createApple(); // Метод для создания яблока
    }

    public void createApple() { // Метод создания яблока
        appleX = new Random().nextInt(20) * DOT_SIZE; // 20 16ти пиксельных квадратов может поместится на игровое поле, из этого выходит 20 равных позиций по Х и по Y
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages(){ // Загрузка картинок
       ImageIcon iia = new ImageIcon("Imagens/apple.png"); // Загрузка картинки
       apple = iia.getImage(); // Инициализация картирки
       ImageIcon iid = new ImageIcon("Imagens/snake.png");
       dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {// Переопределение метода painComapning который перерисовывает игровое поле
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple, appleX, appleY, this); // Рисование яблока
            String schet = "Cчёт: " + dots;
            g.setColor(Color.white);
            g.drawString(schet,255,25);
            for (int i = 0; i < dots; i++) { //Перерисовывание змейки, сколько точек столько и перерисовка
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        else {
            String str = "Game Over"; // Создание строки
           // Font f= new Font("Comics Sans", 14, Font.BOLD); // Шрифт ** Проблемы с выводом шрифта, но если оставить стандартный шрифт системы то все хорошо
            g.setColor(Color.white); // Цвет строки
           // g.setFont(f); // Задание шрифта
            g.drawString(str, 125, size/2-5); // Вывод строки
            g.drawString("Вы набрали - " + dots + " очков",100,size/2+10); // Вывод в конце игры он наброном количестве очков
        }
    }

    public void move (){ // В этом методе будет происходить логическая перерисовка точек
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left){
            x[0] -= DOT_SIZE;
        }
        if (right){
            x[0] += DOT_SIZE;
        }
        if (up){
            y[0] -= DOT_SIZE;
        }
        if (down){
            y[0] += DOT_SIZE;
        }
    }

    public void chekApple(){
        if(x[0] == appleX && y[0] == appleY){ // Проверка не столкнулась ли голова змейки с яблоком
            dots++;
            createApple();
        }
    }

    public void chekCollisions(){
        for(int i = dots; i > 0; i--){
            if(i > 4 && x[0] == x[i] && y [0] == x[i]){ // Проверка на то что змейка не столкнулась сама с собой
                inGame = false;
            }
        }
        if (x[0] > size){ // Проверка на то что змейка не вышла за игровое поле
            inGame = false;
        }
        if (x[0] < 0) { // Проверка на то что змейка не вышла за игровое поле
            inGame = false;
        }
        if (y[0] > size){ // Проверка на то что змейка не вышла за игровое поле
            inGame = false;
        }
        if (y[0] < 0){ // Проверка на то что змейка не вышла за игровое поле
            inGame = false;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            chekApple();
            chekCollisions();
            move();
        }
        repaint(); // Метод для перерисовки поля
    }

    class FieldKeyListener extends KeyAdapter{ // Класс для рассширения нажатия клавиш

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && ! right){ // Проверка того что змейка двигается влево, значит она не может двигаться вправо
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && ! left){ // Проверка того что змейка двигается вправо, значит она не может двигаться влево
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && ! down){ // Проверка того что змейка двигается вверх, значит она не может двигаться вниз
                up = true;
                left = false;
                right = false;
            }
            if (key == KeyEvent.VK_DOWN && ! up){ // Проверка того что змейка двигается вниз, значит она не может двигаться вверх
                down = true;
                left = false;
                right = false;
            }
        }
      }
    }

