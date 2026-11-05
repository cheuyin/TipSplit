import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

class TipSplit {
    static void main() {
        BigDecimal[] inputBill = TipSplit.getBill();
        BigDecimal subtotal = inputBill[0];
        BigDecimal tax = inputBill[1];
        BigDecimal tipPct = inputBill[2];
        BigDecimal numPeople = inputBill[3];

        BigDecimal tip = TipSplit.calculateTip(subtotal, tipPct);
        BigDecimal total = TipSplit.calculateTotal(subtotal, tax, tip);

        TipSplit.printFinalBill(subtotal, tax, tip, total);
    }

//    private static void calculateSplits() {
//        BigDecimal totalCopy = new BigDecimal(Double.toString(this.tip));
//        BigDecimal[] splits = new BigDecimal[this.numPeople];
//        double splitPerPerson = this.tip / this.numPeople;
//        for (int i = 0; i < numPeople - 1; i++) {
//            BigDecimal bd = new BigDecimal(Double.toString(splitPerPerson));
//            bd = bd.setScale(2, RoundingMode.HALF_UP);
//            splits[i] = bd;
//            totalCopy = totalCopy.subtract(bd);
//        }
//        totalCopy = totalCopy.setScale(2, RoundingMode.HALF_UP);
//        splits[splits.length - 1] = totalCopy;
//        this.splits = splits;
//    }

    private static void printFinalBill(BigDecimal subtotal, BigDecimal tax, BigDecimal tip, BigDecimal total) {
        System.out.println("Subtotal: " + subtotal);
        System.out.println("Tax: " + tax);
        System.out.println("Tip: " + tip);
        System.out.println("Total: " + total);
    }

//    private static void printSplits() {
//        for (int i = 0; i < splits.length; i++) {
//            int personId = i + 1;
//            System.out.println("Person " + personId + ": " + splits[i]);
//        }
//    }

    private static BigDecimal[] getBill() {
        Scanner scanner = new Scanner(System.in);
        double subtotalRaw = TipSplit.getValidDouble(scanner, "Subtotal: ");
        BigDecimal subtotal = new BigDecimal(Double.toString(subtotalRaw));

        double taxRaw = TipSplit.getValidDouble(scanner, "Tax: ");
        BigDecimal tax = new BigDecimal(Double.toString(taxRaw));

        double tipPctRaw = TipSplit.getValidDouble(scanner, "Tip %: ");
        BigDecimal tipPct = new BigDecimal(Double.toString(tipPctRaw));

        int numPeopleRaw = TipSplit.getValidInt(scanner, "People: ");
        BigDecimal numPeople = new BigDecimal(Integer.toString(numPeopleRaw));

        return new BigDecimal[]{subtotal, tax, tipPct, numPeople};
    }

    private static BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal tax, BigDecimal tip) {
        return subtotal.add(tax).add(tip);
    }

    private static BigDecimal calculateTip(BigDecimal subtotal, BigDecimal tipPct) {
        return subtotal.multiply(tipPct.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
    }

    private static double getValidDouble(Scanner scanner, String prompt) {
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

    private static int getValidInt(Scanner scanner, String prompt) {
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