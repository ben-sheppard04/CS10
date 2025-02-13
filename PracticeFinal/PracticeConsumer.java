public class PracticeConsumer extends Thread{
    private PracticeMessageBox box;
    private int money;
    private String name;

    public PracticeConsumer(String name, PracticeMessageBox box, int money) {
        //TODO: your code here
        this.name = name;
        this.box = box;
        this.money = money;
    }

    //Takes messages from the box and prints them, until receiving EOF
    public void run() {
        //TODO: your code here
        try{
            String taken;
            while(!(taken = box.take()).equals("EOF") && money >=5) {
                Thread.sleep(5000);
                money -= 5;
                System.out.println(name + " took " + taken + " and has " + money + "dollars left.");
            }
            System.out.println("Sorry, no more money or nothing to buy.");
        }
        catch(Exception e) {
            System.err.println(e);
        }

    }

}
