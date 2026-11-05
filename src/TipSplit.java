import java.util.InputMismatchException;
import java.util.Scanner;

class TipSplit {
    double subtotal;
    double tax;
    double tipPct;
    int numPeople;
    double tip;
    double total;

    static void main() {
        TipSplit splitter = new TipSplit();
        splitter.getBill();
        splitter.tip = splitter.calculateTip();
        splitter.total = splitter.calculateTotal();
        splitter.print();

    }

    public void print() {
        printFinalBill();
    }

    public void printFinalBill() {
        System.out.println("Subtotal: " + this.subtotal);
        System.out.println("Tax: " + this.tax);
        System.out.println("Tip: " + this.tip);
        System.out.println("Total: " + this.subtotal);
    }

    public void getBill() {
        Scanner scanner = new Scanner(System.in);
        this.subtotal = TipSplit.getValidDouble(scanner, "Subtotal: ");
        this.tax = TipSplit.getValidDouble(scanner, "Tax: ");
        this.tipPct = TipSplit.getValidDouble(scanner, "Tip %: ");
        this.numPeople = TipSplit.getValidInt(scanner, "People: ");
    }

    public double calculateTotal() {
        return this.subtotal + this.tax + this.tip;
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