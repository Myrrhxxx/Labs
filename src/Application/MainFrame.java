package Application;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private JFileChooser fileChooser = null;
    private JMenuItem resetGraphicsMenuItem;
    private GraphicsDisplay display = new GraphicsDisplay();
    private boolean fileLoaded = false;

    // Конструктор для главного окна приложения
    public MainFrame() {
        super("Обработка событий от мыши"); // Устанавливаем заголовок окна
        this.setSize(WIDTH, HEIGHT); // Устанавливаем размер окна
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2); // Центрируем окно
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Максимизируем окно
        JMenuBar menuBar = new JMenuBar(); // Создаем строку меню
        this.setJMenuBar(menuBar); // Устанавливаем строку меню для этого окна
        JMenu fileMenu = new JMenu("Файл"); // Создаем меню
        menuBar.add(fileMenu); // Добавляем меню в строку меню

        // Определяем действие для открытия файла с графиком
        Action openGraphicsAction = new AbstractAction("Открыть файл с графиком") {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File(".")); // Устанавливаем начальную директорию
                }
                MainFrame.this.fileChooser.showOpenDialog(MainFrame.this); // Показываем диалог выбора файла
                MainFrame.this.openGraphics(MainFrame.this.fileChooser.getSelectedFile()); // Открываем выбранный файл
            }
        };
        fileMenu.add(openGraphicsAction); // Добавляем действие открытия в меню

        // Определяем действие для отмены изменений графика
        Action resetGraphicsAction = new AbstractAction("Отменить все изменения") {
            public void actionPerformed(ActionEvent event) {
                MainFrame.this.display.reset(); // Сбрасываем графическое отображение
            }
        };
        this.resetGraphicsMenuItem = fileMenu.add(resetGraphicsAction); // Добавляем действие отмены в меню
        this.resetGraphicsMenuItem.setEnabled(false); // Изначально отключаем возможность отмены
        this.getContentPane().add(this.display, "Center"); // Добавляем графическое отображение в центр окна
    }

    // Метод для открытия графического файла и чтения его данных
    protected void openGraphics(File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            ArrayList<Double[]> graphicsData = new ArrayList<>(50); // Список для хранения данных графика

            // Чтение данных из файла
            while (in.available() > 0) {
                Double x = in.readDouble();
                Double y = in.readDouble();
                graphicsData.add(new Double[]{x, y}); // Добавляем координаты в список
            }

            // Если данные были прочитаны, обновляем отображение и включаем возможность отмены
            if (graphicsData.size() > 0) {
                this.fileLoaded = true;
                this.resetGraphicsMenuItem.setEnabled(true);
                this.display.displayGraphics(graphicsData); // Отображаем график
            }

        } catch (FileNotFoundException var6) {
            JOptionPane.showMessageDialog(this, "Указанный файл не найден", "Ошибка загрузки данных", JOptionPane.ERROR_MESSAGE); // Показываем сообщение об ошибке, если файл не найден
        } catch (IOException var7) {
            JOptionPane.showMessageDialog(this, "Ошибка чтения координат точек из файла", "Ошибка загрузки данных", JOptionPane.ERROR_MESSAGE); // Показываем сообщение об ошибке при чтении файла
        }
    }

    // Главный метод для запуска приложения
    public static void main(String[] args) {
        MainFrame frame = new MainFrame(); // Создаем главное окно
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Устанавливаем операцию закрытия по умолчанию
        frame.setVisible(true); // Делаем окно видимым
    }
}
