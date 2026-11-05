import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

class TipSplit {
    double subtotal;
    double tax;
    double tipPct;
    int numPeople;
    double tip;
    double total;
    BigDecimal[] splits;

    static void main() {
        TipSplit splitter = new TipSplit();
        splitter.getBill();
        splitter.tip = splitter.calculateTip();
        splitter.total = splitter.calculateTotal();
        splitter.printFinalBill();
        splitter.calculateSplits();
        splitter.printSplits();

    }

    public void calculateSplits() {
        BigDecimal totalCopy = new BigDecimal(Double.toString(this.tip));
        BigDecimal[] splits = new BigDecimal[this.numPeople];
        double splitPerPerson = this.tip / this.numPeople;
        for (int i = 0; i < numPeople - 1; i++) {
            BigDecimal bd = new BigDecimal(Double.toString(splitPerPerson));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            splits[i] = bd;
            totalCopy = totalCopy.subtract(bd);
        }
        totalCopy = totalCopy.setScale(2, RoundingMode.HALF_UP);
        splits[splits.length - 1] = totalCopy;
        this.splits = splits;
    }

    public void printFinalBill() {
        System.out.println("Subtotal: " + this.subtotal);
        System.out.println("Tax: " + this.tax);
        System.out.println("Tip: " + this.tip);
        System.out.println("Total: " + this.total);
    }

    public void printSplits() {
        for (int i = 0; i < splits.length; i++) {
            int personId = i + 1;
            System.out.println("Person " + personId + ": " + splits[i]);
        }
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