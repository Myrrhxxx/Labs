package breakfast;

public class MainApplication {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Food[] breakfast = new Food[20];
        int itemsSoFar = 0;
        for (String arg: args) {
            String[] parts = arg.split("/");
            if (parts[0].equals("Cheese")) {
                breakfast[itemsSoFar] = new Cheese();
            } else if (parts[0].equals("Apple")) {
                breakfast[itemsSoFar] = new Apple(parts[1]);
            } else if (parts[0].equals("Milk")) {
                breakfast[itemsSoFar] = new Milk(parts[1]);
            }
            itemsSoFar++;
        }
        for (Food item: breakfast) {
            if (item != null) {
                item.consume();
            } else {
                break;
            }
        }
        System.out.println("Всего хорошего!");

        // Подсчет количества продуктов заданного типа
        int cheeseCount = countFoodType(breakfast, new Cheese());
        int appleCount = countFoodType(breakfast, new Apple("medium")); // Размер не важен, т.к. метод equals() сравнивает только тип
        int milkCount = countFoodType(breakfast, new Milk("1.5%")); // Жирность не важна, т.к. метод equals() сравнивает только тип

        System.out.println("Количество сыров: " + cheeseCount);
        System.out.println("Количество яблок: " + appleCount);
        System.out.println("Количество молока: " + milkCount);
    }

    private static int countFoodType(Food[] breakfast, Food food) {
        int count = 0;
        for (Food item : breakfast) {
            if (item != null && item.equals(food)) {
                count++;
            }
        }
        return count;
    }
}

