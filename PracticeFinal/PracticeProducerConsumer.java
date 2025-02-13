    public class PracticeProducerConsumer {
        public static final int numMessages = 20;  // how many messages to send
        private PracticeProducer producer;
        private PracticeConsumer consumer1;
        private PracticeConsumer consumer2;

        public PracticeProducerConsumer() {
            PracticeMessageBox box = new PracticeMessageBox();
            producer = new PracticeProducer("producer 1", box,numMessages);
            consumer1 = new PracticeConsumer("consumer 1", box, 200);
            consumer2 = new PracticeConsumer("consumer 2", box, 350);
        }

        //Just starts the producer and consumer running
        public void communicate() {
            producer.start();
            consumer1.start();
            consumer2.start();
        }

        public static void main(String[] args) {
            new PracticeProducerConsumer().communicate();
        }
    }
