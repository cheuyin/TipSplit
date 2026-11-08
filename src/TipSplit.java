import java.util.Arrays;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

class TipSplit {
    static void main() {
        BigDecimal[] inputBill = TipSplit.getBill();
        BigDecimal subtotal = inputBill[0].setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = inputBill[1].setScale(2, RoundingMode.HALF_UP);
        BigDecimal tipPct = inputBill[2].setScale(2, RoundingMode.HALF_UP);
        BigDecimal numPeople = inputBill[3].setScale(2, RoundingMode.HALF_UP);

        BigDecimal tip = TipSplit.calculateTip(subtotal, tipPct);
        BigDecimal total = TipSplit.calculateTotal(subtotal, tax, tip);

        System.out.println();
        TipSplit.printFinalBill(subtotal, tax, tip, total);

        BigDecimal[] splits = TipSplit.calculateSplits(total, numPeople);

        System.out.println();
        TipSplit.printSplits(splits);
    }

    private static BigDecimal[] calculateSplits(BigDecimal total, BigDecimal numPeople) {
        BigDecimal totalRemaining = total.setScale(2, RoundingMode.HALF_UP);
        BigDecimal[] splits = new BigDecimal[numPeople.intValue()];
        BigDecimal splitPerPerson = totalRemaining.divide(numPeople, 2, RoundingMode.HALF_DOWN);
        for (int i = 0; i < numPeople.intValue() - 1; i++) {
            splits[i] = splitPerPerson;
            totalRemaining = totalRemaining.subtract(splitPerPerson);
        }
        splits[splits.length - 1] = totalRemaining;
        return splits;
    }

    private static void printFinalBill(BigDecimal subtotal, BigDecimal tax, BigDecimal tip, BigDecimal total) {
        int WIDTH = 20;
        printWithPadding("Subtotal:", WIDTH);
        System.out.println(subtotal);

        printWithPadding("Tax:", WIDTH);
        System.out.println(tax);

        printWithPadding("Tip:", WIDTH);
        System.out.println(tip);

        printWithPadding("Total:", WIDTH);
        System.out.println(total);
    }

    private static void printSplits(BigDecimal[] splits) {
        int WIDTH = 20;
        for (int i = 0; i < splits.length; i++) {
            int personId = i + 1;
            printWithPadding("Person " + personId + ": ", WIDTH);
            System.out.println(splits[i]);
        }
    }

    private static void printWithPadding(String text, int width) {
        String formatSpecifier = "%-" + width + "s";
        String res = String.format(formatSpecifier, text);
        System.out.print(res);
    }

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
        return subtotal.multiply(tipPct.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
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