import java.util.Random;

public class Sensor {

    private static final double MIN_VOLTAGE = 0.0;
    private static final double MAX_VOLTAGE = 5.0;
    private static final int INTERVAL_MS  = 500;

    private final Random random = new Random();

    public double readVoltage() {
        return MIN_VOLTAGE + (MAX_VOLTAGE - MIN_VOLTAGE) * random.nextDouble();
    }

    public void run() {
        System.out.println("Sensor started. Reading voltage every " + INTERVAL_MS + "ms...\n");

        for(int i = 0; i < 100; i++) {//needs a while loop but there is no implementation to stop it currently
            double voltage = readVoltage();
            System.out.printf("Voltage: %.3f V%n", voltage);

            try {
                Thread.sleep(INTERVAL_MS);
            } catch (InterruptedException e) {
                System.out.println("Sensor interrupted. Shutting down.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public static void main(String[] args) {
        new Sensor().run();
    }
}
