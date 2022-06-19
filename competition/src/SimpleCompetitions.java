import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleCompetitions implements Serializable {

    private boolean testFlag;
    private DataProvider dataProvider;
    private List<Competition> competitionList;
    private Competition activeCompetition;
    Scanner scanner = new Scanner(System.in);

    public boolean getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(boolean testFlag) {
        this.testFlag = testFlag;
    }

    public List<Competition> getCompetitionList() {
        return competitionList;
    }

    public void setCompetitionList(List<Competition> competitionList) {
        this.competitionList = competitionList;
    }

    private void mainMenu(){
        System.out.println("Please select an option. Type 5 to exit.\n" +
                "1. Create a new competition\n" +
                "2. Add new entries\n" +
                "3. Draw winners\n" +
                "4. Get a summary report\n" +
                "5. Exit");
        String command = scanner.nextLine().toLowerCase();
        try{
            Integer.parseInt(command);
        }catch (Exception e){
            System.out.println("A number is expected. Please try again.");
        }
        switch (command){
            case "1":
                if (activeCompetition == null){
                    addNewCompetition();
                }else{
                    System.out.println("There is an active competition. SimpleCompetitions " +
                            "does not support concurrent competitions!");
                }
                break;
            case "2":
                if (activeCompetition == null){
                    System.out.println("There is no active competition. Please create one!");
                }else{
                    billToEntry();
                }
                break;
            case "3":
                if (activeCompetition == null){
                    System.out.println("There is no active competition. Please create one!");
                }else{
                    activeCompetition.drawWinners();
                }
                break;
            case "4":
                report();
                break;
            case "5":
                exit();
                break;
            default:
                System.out.println("Unsupported option. Please try again!");
        }
    }

    private void initSetting() throws Exception{
        boolean initFlag = true;
        while (initFlag){
            System.out.println("Load competitions from file? (Y/N)?");
            String loadComp = scanner.nextLine().toLowerCase();
            if ("y".equals(loadComp)){
                loadSimpleCompetitions();
                initFlag = false;
            }else if ("n".equals(loadComp)){
                createNewSimpleCompetitions();
                initFlag = false;
            }else{
                System.out.println("Unsupported option. Please try again!");
            }
        }
    }

    private void loadSimpleCompetitions() {
        System.out.println("File name:");
        String fileName = scanner.nextLine();

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            SimpleCompetitions loaded = (SimpleCompetitions)objectInputStream.readObject();

            // get loaded info
            setTestFlag(loaded.getTestFlag());
            setCompetitionList(loaded.getCompetitionList());
        } catch (Exception e) {
            // TODO: finish error message
            e.printStackTrace();
        }
    }

    private void createNewSimpleCompetitions(){
        // create new list of competitions
        competitionList = new ArrayList<>();
        boolean flag = true;
        while (flag){
            System.out.println("Which mode would you like to run? (Type T for Testing, and N for Normal mode):");
            String mode = scanner.nextLine().toLowerCase();
            if ("t".equals(mode)){
                // test mode and exit loop
                setTestFlag(true);
                flag = false;
            }else if ("n".equals(mode)){
                // normal mode and exit loop
                setTestFlag(false);
                flag = false;
            }else{
                // wrong input
                System.out.println("Invalid mode! Please choose again.");
            }
        }

    }

    private void setDataProvider(){
        System.out.println("Member file: ");
        String memberFile = scanner.nextLine();
        System.out.println("Bill file: ");
        String billFile = scanner.nextLine();

        //TODO:给DataProvider传数据
        try {
            dataProvider = new DataProvider(memberFile,billFile);
        } catch (Exception e) {
            //TODO: error message
            System.out.println("error message");
        }

    }

    private void addNewCompetition(){
        Competition competition = null;
        while (competition == null){
            System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
            String type = scanner.nextLine().toLowerCase();
            if ("l".equals(type)){
                competition = new LuckyNumbersCompetition();
            }else if ("r".equals(type)){
                competition = new RandomPickCompetition();
            }else{
                System.out.println("Invalid competition type! Please choose again.");
            }
        }
        System.out.println("Competition name:");
        String name = scanner.nextLine();
        competition.setName(name);
        if (competitionList == null){
            competition.setId(1);
        }else{
            competition.setId(competitionList.get(competitionList.size()-1).getId()+1);
        }
        System.out.println("A new competition has been created!");
        competition.shortInfo();
    }

    private void billToEntry(){
        boolean flag = true;
        while (flag){
            System.out.println("Bill ID: ");
            String enteredBillId = scanner.nextLine();
            Bill bill = null;

            if (enteredBillId.matches("^[0-9]{6}$")){
                bill = dataProvider.getBill(enteredBillId);
                if (bill == null){
                    System.out.println("This bill does not exist. Please try again.");
                }else if (bill.getMemberId() == null){
                    System.out.println("This bill has no member id. Please try again.");
                }else{
                    activeCompetition.addEntries(scanner);
                    flag = false;
                }

            }else{
                System.out.println("Invalid bill id! It must be a 6-digit number. Please try again.");
            }
        }

    }
    private void report(){

    }

    private void exit(){

    }

    public static int run() throws Exception{
        boolean running = true;
        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
        // create an object of SimpleCompetitions and record competitions and info with it
        SimpleCompetitions sc = new SimpleCompetitions();
        sc.initSetting();
        if (sc.competitionList == null){
            return 0;
        }
        sc.setDataProvider();
        if (sc.dataProvider == null){
            return 0;
        }
        while (running){
            sc.mainMenu();

        }
        return 1;
    }

    public static void main(String[] args) throws Exception{
        run();
    }

}
