import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
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
        BigDecimal baseSplit = totalRemaining.divide(numPeople, 2, RoundingMode.DOWN);

        // Allocate the base split to everyone
        for (int i = 0; i < numPeople.intValue(); i++) {
            splits[i] = baseSplit;
            totalRemaining = totalRemaining.subtract(baseSplit);
        }

        // Allocate the remaining amount to the first n people
        int remainingCents = totalRemaining.divide(new BigDecimal("0.01"), 0, RoundingMode.DOWN).intValue();
        for (int i = 0; i < remainingCents; i++) {
            splits[i] = splits[i].add(new BigDecimal("0.01"));
        }

        return splits;
    }

    private static void printFinalBill(BigDecimal subtotal, BigDecimal tax, BigDecimal tip, BigDecimal total) {
        int WIDTH = 15;
        printWithPadding("Subtotal", WIDTH, true);
        printWithPadding(subtotal.toString(), 10, false);
        System.out.println();

        printWithPadding("Tax", WIDTH, true);
        printWithPadding(tax.toString(), 10, false);
        System.out.println();

        printWithPadding("Tip", WIDTH, true);
        printWithPadding(tip.toString(), 10, false);
        System.out.println();

        printWithPadding("Total", WIDTH, true);
        printWithPadding(total.toString(), 10, false);
        System.out.println();
    }

    private static void printSplits(BigDecimal[] splits) {
        for (int i = 0; i < splits.length; i++) {
            int personId = i + 1;
            printWithPadding("Person " + personId + ": ", 15, true);
            printWithPadding(splits[i].toString(), 10, false);
            System.out.println();
        }
    }

    private static void printWithPadding(String text, int width, boolean padRight) {
        String formatSpecifier;
        if (padRight) {
            formatSpecifier = "%-" + width + "s";
        } else {
            formatSpecifier = "%" + width + "s";
        }
        String res = String.format(formatSpecifier, text);
        System.out.print(res);
    }

    private static BigDecimal[] getBill() {
        Scanner scanner = new Scanner(System.in);
        BigDecimal subtotal = TipSplit.getValidInput(scanner, "Subtotal: ", 2, new BigDecimal("0.01"), new BigDecimal("1000000"));
        BigDecimal tax = TipSplit.getValidInput(scanner, "Tax: ", 2, BigDecimal.ZERO, new BigDecimal("1000000"));
        BigDecimal tipPct = TipSplit.getValidInput(scanner, "Tip %: ", 2, BigDecimal.ZERO, new BigDecimal("100"));
        BigDecimal numPeople = TipSplit.getValidInput(scanner, "People: ", 0, BigDecimal.ONE, new BigDecimal("100"));

        return new BigDecimal[]{subtotal, tax, tipPct, numPeople};
    }

    private static BigDecimal calculateTotal(BigDecimal subtotal, BigDecimal tax, BigDecimal tip) {
        return subtotal.add(tax).add(tip);
    }

    private static BigDecimal calculateTip(BigDecimal subtotal, BigDecimal tipPct) {
        return subtotal.multiply(tipPct.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal getValidInput(Scanner scanner, String prompt, int numDecimals, BigDecimal min, BigDecimal max) {
        while (true) {
            System.out.print(prompt);
            String input = "";
            
            try {
                input = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println();
                System.out.println("Error: input ended early");
                System.exit(1);
            }

            BigDecimal value;

            // Check 1: Valid double format
            try {
                value = new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid number format");
                continue;
            }

            // Check 2: At most n decimal places
            if (value.scale() > numDecimals) {
                System.out.println("Error: value can have at most " + numDecimals + " decimal places");
                continue;
            }

            // Check 3:
            if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
                System.out.printf("Error: value must be between %.2f and %.2f. Please try again.%n", min, max);
                continue;
            }

            return value;
        }
    }
}