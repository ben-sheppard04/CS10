public class PracticeProducer extends Thread{
    private PracticeMessageBox box;
    private int numberToSend;
    private String name;

    public PracticeProducer(String name, PracticeMessageBox box, int numberToSend) {
        //TODO: your code here
        this.box = box;
        this.numberToSend = numberToSend;
        this.name = name;
    }

    //Wait for a while then puts a message. Puts "EOF" when # messages have been put
    public void run() {
        //TODO: your code here
        try{
            for(int i = 1; i <= numberToSend; i++) {
                Thread.sleep((int)(Math.random() * 5000));
                box.put("Product " + i);
                System.out.println("Put Product " + i + " in the box");
            }
            box.put("EOF");
        }
        catch(Exception e) {
            System.err.println(e);
        }

    }

}
