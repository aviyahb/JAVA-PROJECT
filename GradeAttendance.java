      import java.util.Scanner;

public class GradeAttendance {
    static final int SUBJECTS = 3, MIN_ATT = 75;                       // constants

    static int sum(int[] m, int n) {                                   // recursion
        return n == 0 ? 0 : m[n - 1] + sum(m, n - 1);                  // ternary
    }
    static double avg(int[] m) { return (double) sum(m, m.length) / m.length; }   // typecasting
    static double pct(int attended, int total) { return attended * 100.0 / total; }
    static double pct(boolean[] p) {                                   // method overloading
        int c = 0;
        for (boolean x : p) if (x) c++;                                // enhanced for
        return pct(c, p.length);
    }
    static char grade(double a) {                                      // if-else-if ladder
        if (a >= 90) return 'A'; else if (a >= 75) return 'B';
        else if (a >= 60) return 'C'; else if (a >= 40) return 'D'; else return 'F';
    }
    static String remark(char g) {                                     // switch-default
        switch (g) {
            case 'A': return "Excellent";
            case 'B': return "Good";
            case 'C': case 'D': return "Pass";
            default: return "Fail";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of students and classes held: ");
        int n = sc.nextInt(), days = sc.nextInt();
        int[] roll = new int[n];
        int[][] marks = new int[n][SUBJECTS];
        boolean[][] present = new boolean[n][days];

        for (int i = 0; i < n; i++) {                                  // nested loops fill 2D arrays
            System.out.print("\nRoll number: ");
            roll[i] = sc.nextInt();
            for (int j = 0; j < SUBJECTS; j++) {
                do {
                    System.out.print("Marks in subject " + (j + 1) + " (0-100): ");
                    marks[i][j] = sc.nextInt();
                } while (marks[i][j] < 0 || marks[i][j] > 100);        // do-while, logical ||
            }
            System.out.print("Attendance for " + days + " classes (1 = present, 0 = absent): ");
            for (int d = 0; d < days; d++) present[i][d] = sc.nextInt() == 1;
        }

        System.out.println("\nRoll  Avg    Grade Remark    Attendance");
        int shortage = 0;
        double total = 0;
        for (int i = 0; i < n; i++) {
            double a = avg(marks[i]), p = pct(present[i]);
            char g = grade(a);
            total += a;                                                // compound assignment
            if (p < MIN_ATT) shortage++;                               // counting
            System.out.printf("%-5d %6.2f  %-5c %-9s %5.1f%%%s%n",
                    roll[i], a, g, remark(g), p, p < MIN_ATT ? "  SHORTAGE" : "");
        }
        System.out.printf("Class average: %.2f | Attendance shortage: %d%n", total / n, shortage);

        for (int j = 0; j < SUBJECTS; j++) {                           // column sums of the 2D array
            int s = 0;
            for (int i = 0; i < n; i++) s += marks[i][j];
            System.out.printf("Subject %d average: %.1f%n", j + 1, (double) s / n);
        }

        while (true) {                                                 // infinite loop with break
            System.out.print("\nSearch roll number (0 to quit): ");
            int key = sc.nextInt(), idx = -1;
            if (key == 0) break;
            for (int i = 0; i < n; i++)
                if (roll[i] == key) { idx = i; break; }                // linear search
            if (idx == -1) { System.out.println("Not found."); continue; }
            double a = avg(marks[idx]), p = pct(present[idx]);
            if (p >= MIN_ATT) {                                        // nested if
                if (grade(a) != 'F') System.out.println("Eligible for exams.");
                else System.out.println("Attendance fine, but failed.");
            } else System.out.println("Detained: attendance shortage.");
        }
    }
}
