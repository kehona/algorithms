package algorithm;

public class MinFixedMontPayment {

    public static void main(String[] args) {

            payingFixedMInMonth(1200.00, 0.18);
    }

    public static void payingFixedMInMonth(double outstandingBalance, double annualInterestRate) {

            for (int month = 1; month <= 12; month++) {

                    if (month == 11) {

                            double prevBalance;

                            double minMonthlyPayment = 10;
                            // minMonthlyPayment+=minMonthlyPayment;

                            prevBalance = outstandingBalance;

                            String newMonth = "Month : " + month;

                            double monthlyInterestRate = annualInterestRate / 12.00;

                            annualInterestRate = monthlyInterestRate * 12;

                            // double minMonthlyPayment = monthlyInterestRate * prevBalance;

                            double updatedBalancePerMonth = prevBalance * (1 + monthlyInterestRate) - minMonthlyPayment;

                            // double minMonthlyPayment = updatedBalancePerMonth -
                            // prevBalance *
                            // (1 + monthlyInterestRate);

                            System.out.println("Monthly payment to pay off debt in 1 year : " + minMonthlyPayment);

                            System.out.println("The number of month needed : " + month);

                            System.out.println("Balance :" + prevBalance);

                            prevBalance -= updatedBalancePerMonth;
                    }
                    // System.out.println(month);
            }

    }
}
