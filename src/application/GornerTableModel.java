package application;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    @Override
    public int getColumnCount() {
        return 3; // изменили на 3
    }

    @Override
    public int getRowCount() {
        return new Double(Math.ceil((to - from) / step)).intValue() + 1; // Вычислить количество точек между началом и концом отрезка исходя из шага табулирования
    }

    @Override
    public Object getValueAt(int row, int col) {
        double x = from + step * row; // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ * НОМЕР_СТРОКИ
        if (col == 0) {
            return x; // Если запрашивается значение 1-го столбца, то это X
        } else if (col == 1) {
            Double result = coefficients[0];
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i]; // Вычисление значения в точке по схеме Горнера
            }
            return result; // Значение многочлена
        } else {
            Double result = coefficients[0];
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
            }
            String resultString = result.toString();

            return isEdgeSymmetric(resultString); // Проверка на краевую симметрию
        }
    }

    private boolean isEdgeSymmetric(String resultString) {
        // Удаляем все символы, кроме цифр и точки
        String cleanedResult = resultString.replaceAll("[^\\d.]", "");

        // Проверяем, что строка содержит хотя бы одну точку и две цифры после нее
        if (!cleanedResult.contains(".") || cleanedResult.length() < 3) {
            return false;
        }

        // Разделяем строку на целую и дробную части
        String[] parts = cleanedResult.split("\\.");
        String integerPart = parts[0];
        String fractionalPart = parts[1].length() > 2 ? parts[1].substring(0, 2) : parts[1];

        // Объединяем целую и дробную части, оставив только первые две цифры дробной части
        String combinedResult = integerPart + fractionalPart;

        // Берем первую и последнюю цифры объединенного числа
        char firstDigit = combinedResult.charAt(0);
        char lastDigit = combinedResult.charAt(combinedResult.length() - 1);

        return firstDigit == lastDigit;
    }

    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            case 2:
                return "Краевая симметрия";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int col) {
        if (col == 2) {
            return Boolean.class; // В 3-м столбце находятся значения типа Boolean
        }
        return Double.class; // В 1-м и 2-м столбцах находятся значения типа Double
    }
}
