import java.util.InputMismatchException;
import java.util.Scanner;

class TipSplit {
    double subtotal;
    double tax;
    double tipPct;
    int numPeople;

    static void main() {
        TipSplit splitter = new TipSplit();
        splitter.getBill();
        double tip = splitter.calculateTip();
        System.out.println(tip);
    }

    public void getBill() {
        Scanner scanner = new Scanner(System.in);
        this.subtotal = TipSplit.getValidDouble(scanner, "Subtotal: ");
        this.tax = TipSplit.getValidDouble(scanner, "Tax: ");
        this.tipPct = TipSplit.getValidDouble(scanner, "Tip %: ");
        this.numPeople = TipSplit.getValidInt(scanner, "People: ");
    }

    public double calculateTip() {
        return this.subtotal * (tipPct / 100);
    }

    public static double getValidDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid double format. Please try again.");
                scanner.nextLine();
            }
        }
    }

    public static int getValidInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid int format. Please try again.");
                scanner.nextLine();
            }
        }
    }
}