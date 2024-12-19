package breakfast;

public class Milk extends Food {
    private String fat;

    public Milk(String fat) {
        super("Молоко");
        this.fat = fat;
    }

    public void consume() {
        System.out.println(this + " выпито");
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String toString() {
        return super.toString() + " жирностью '" + fat + "'";
    }
}

