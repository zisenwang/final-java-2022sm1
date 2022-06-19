import java.util.List;
import java.util.Scanner;

public class Competition {
    private int id;
    private String name;
    private List<Entry> entryList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEntries(Scanner scanner){

    }

    public void drawWinners(){

    }

    public void shortInfo(){
        System.out.println("Competition ID: " + id + ", Competition Name: " + name + ", Type: " + getClass().getName());
    }

    public void report(){

    }
}
