import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;
public class MomoMachine {
    static private String[] nameOfProduct = {"Coke", "Pepsi", "Soda"};
    static private int[] priceofProduct = {10000, 10000, 20000};
    static private int[] moneyAccepted = {10000,20000,50000,100000,200000};
    static int moneyInMachine = 0;
    static int bill = 0;
    static int coke = 0;
    static int pepsi = 0;
    static int soda = 0;
    static int moneyPromotion = 0;
    static Date today = new Date();
    static boolean increase = false;

    //for testing
    /*----------------*/
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private static Date getNextDate(Date date)
    {
        return new Date(date.getTime() + MILLIS_IN_A_DAY);
    }
    /*---------------
    *
    *
    *
    * */
    /* HELP FUNCTION*/
    /*reset machine after finish one*/
    private  static void resetMachine(){
        moneyInMachine = coke = pepsi = soda = bill = 0;
    }
    /*reset param*/
    private static void resetDay(Date now, boolean inc){
        resetMachine();
        today = now;
        moneyPromotion = 0;
        increase = inc;
    }
    /*Get day of date*/
    private static int getDay(Date date){
        ; // your date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    /*If that is a new day check if moneyPromotion previous day is less than 50k or not to increase to 50%*/
    private static void checkForNewDay(){
        int tod = getDay(today);
        Date now = new Date();
        int moment = getDay(now);
        // if day is different the day is the next day
        if(moment != tod){ // if now is the next day of 'today'
            if(moneyPromotion < 50000){
                resetDay(now, true);
            }
            else {
                resetDay(now, false);
            }
        }
    }
    // show parameter in machine
    private static void print(){
        System.out.println("Money in Machine = " + moneyInMachine);
        System.out.println("Money in Promotion = " + moneyPromotion);
        System.out.println("Today : " + today);
        System.out.println(coke + " " + pepsi + " " + soda);
    }
    private static void checkForPromotion(){
        if(moneyPromotion >= 50000) return;
        if(coke >= 3){
            System.out.println("You have 10% to get a same product for free");
            if(randomFreeProduct(increase) && (moneyPromotion <= 40000)){
                System.out.println("Congratulations! You got a free Product of Coke");
                moneyPromotion += 10000;
            }
            else {
                System.out.println("You didn't get a free product of Coke! Good luck");
            }

        }
        if(pepsi >= 3){
            System.out.println("You have 10% to get a same product for free");
            if(randomFreeProduct(increase) && (moneyPromotion <= 40000)){
                System.out.println("Congratulations! You got a free product of Pepsi");
                moneyPromotion += 10000;
            }
            else {
                System.out.println("You didn't get a free product of Pepsi! Good luck");
            }

        }
        if(soda >= 3){
            System.out.println("You have 10% to get a same product for free");
            if(randomFreeProduct(increase) && (moneyPromotion <= 30000)){
                System.out.println("Congratulations! You got a free Product of Soda");
                moneyPromotion += 10000;
            }
            else {
                System.out.println("You didn't get a free product of Soda! Good luck");
            }

        }
    }
    /*random promotion scheme*/
    private static boolean randomFreeProduct(boolean inc){
        double random = Math.random();
        //System.out.println("value random = " + random);
        if (inc) {
            if(random < 0.5 && random >= 0.4) return true;
        }
        else {
            if(random < 0.5 && random >= 0) return true;
        }
        return false;
    }
    /*Check if enough money to purchase return true else return fasle*/
    private static boolean calculateMoney(){
        bill = coke * 10000 + pepsi * 10000 + soda * 20000;
        if (moneyInMachine >= bill) return true;
        return false;
    }
    /*Calculate return money by total of accepted money*/
    private static int calculateReturnMoney(int excessMoney){
        int returnMoney = 0;
        while (excessMoney >= 10000){
            //System.out.println("excessMoney = " + excessMoney);
            if (excessMoney >= 200000){
                excessMoney -= 200000;
                returnMoney += 200000;
            }
            else if(excessMoney >= 100000){
                excessMoney -= 100000;
                returnMoney += 100000;
            }
            else if(excessMoney >= 50000){
                excessMoney -= 50000;
                returnMoney += 50000;
            }
            else if(excessMoney >= 20000) {
                excessMoney-= 20000;
                returnMoney += 20000;
            }
            else if(excessMoney >= 10000){
                excessMoney-= 100000;
                returnMoney += 10000;
            }
        }
        return returnMoney;
    }
    /*Check if money is accepted return true else return false*/
    private static boolean checkMoneyInput(int money){
        for(int money_ : moneyAccepted){
            if (money_ == money) return true;
        }
        return false;
    }
    /*User canceling*/
    private static void backMoney(){
        resetMachine();
        System.out.println("Please wait for a minute to get your money!");
    }
    /*Show current item*/
    private static void currentItem(){
        System.out.println("You had " + coke + " Coke");

        System.out.println("You had " + pepsi + " Pepsi");

        System.out.println("You had " + soda + " Soda");
    }
    /*---------------------------------------------------------------------------
    *
    *
    *
    *
    * */
    /*This function to show machine' s item */
    private static void showItem(){
        // use for show item product
        for (int i = 0; i < 3; i++) {
            System.out.println(nameOfProduct[i] + "\t: " + priceofProduct[i]);
        }

    }
    /*Require user input their money*/
    private static void userMoney(boolean fromCal){
        Scanner scan = new Scanner(System.in);
        int userMoney = 0;
        do{
            System.out.println("Please input your money!");
            userMoney = scan.nextInt();
        }while(!checkMoneyInput(userMoney));

        moneyInMachine += userMoney; // add money in machine
        if(fromCal) {
            // if call from makebill when it is not enough money
            //System.out.println("Can make bill " + calculateMoney());
            makeBill(calculateMoney());
        }
        else{
            // from begin
            userSelect();
        }
    }
    /*Require user's choice*/
    private static void userSelect(){
        Scanner scan = new Scanner(System.in);
        int userSelect;
        System.out.println("Please select what you want!");
        System.out.println("Press 1 to change Coke\nPress 2 to change Pepsi\nPress 3 to change Soda.");
        do{
            System.out.println("Press 0 to finish your selection!\nPress -1 to exit and refund your money!");

            userSelect = scan.nextInt();
            if(userSelect == 0 || userSelect == -1){
                break;
            }
            if(userSelect != 1 && userSelect != 2 && userSelect != 3){
                System.out.println("Please select from 1-> 3");
                continue;
            }
            System.out.println("How many do you wanna change? with '-' to increase");
            int numberOfItem = scan.nextInt();
            if(userSelect == 1) {
                coke += numberOfItem;
                if (coke < 0 )coke=0;
            }
            else if(userSelect == 2) {
                pepsi+= numberOfItem;
                if(pepsi < 0) pepsi= 0;
            }
            else if(userSelect == 3) {
                soda+=numberOfItem;
                if(soda < 0) soda = 0;
            }
            currentItem();
        }while(1==1);
        if(userSelect == -1 ) {
            // user canceling
            backMoney();
            return;
        }
        releaseUserChoice();

    }
    /* Release user's choice return 1 if user wanna change and return 0 if not*/
    private static void releaseUserChoice(){
        currentItem();
        System.out.println("Do you wanna change your selection!?\nPress 1 to change or Press 2 to skip this step!");
        Scanner scan = new Scanner(System.in);
        int select = scan.nextInt();
        if (select == 1){
            // if user wanna change something
            userSelect();
        }
        else if(select == 2) {
            // user wanna check out
            makeBill(calculateMoney());
        }
    }
    /*Make bill to purchase*/
    private static void makeBill (boolean check){
        Scanner scan = new Scanner(System.in);
        //System.out.println("boolean check = " + check);
        if(check){
            // refund money
            int excessMoney = moneyInMachine - bill;
            //System.out.println("excessMoney = " + excessMoney);
            int returnMoney = calculateReturnMoney(excessMoney);
            //System.out.println("return money = " + returnMoney);
            System.out.println("You can get " + returnMoney + " return money!" );
            System.out.println("Purchasing by press 1 or press -1 to exit()");
            int select = scan.nextInt();
            if(select == 1){
                // refund money by
                moneyInMachine = 0;
                // Checking for promotion
                checkForPromotion();
                System.out.println("Take your money! See you again!");
                resetMachine();
                return;

            }else if(select == -1) {
                backMoney();
                return;
            }
        }
        else{
            System.out.println("Do not enough money!\nPress 1 to input more money\nPress 2 to change something\nPress -1 to refund your money");
            int choice = scan.nextInt();
            if(choice == -1) backMoney();
            else if(choice == 1) userMoney(true);
            else if(choice == 2) userSelect();
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println(today);
        // Do not turn off machine :))
        while(true){

            System.out.println("Press Any Key To Continue...");
            new java.util.Scanner(System.in).nextLine();
            //print(); //uncomment to check after a transaction or canceling
            checkForNewDay();
            showItem();// show item
            userMoney(false);
        }

    }
}
