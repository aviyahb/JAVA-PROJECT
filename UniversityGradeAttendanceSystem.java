import java.io.*;
import java.util.*;

public class UniversityGradeAttendanceSystem {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n1. Add Student  2. Display  3. Search  4. Save & Exit");
            
            choice = readInt("choice");

            switch (choice) {
                case 1: addStudent(); break;
                case 2: displayStudents(); break;
                case 3: searchStudent(); break;
                case 4: saveData(); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }

    // Keeps asking until the user enters a valid whole number.
    static int readInt(String field) {
        while (true) {
            System.out.print("Enter " + field + ": ");
            String text = sc.nextLine().trim();
            if (text.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole number.");
            }
        }
    }

    static double readDouble(String field) {
        while (true) {
            System.out.print("Enter " + field + ": ");
            String text = sc.nextLine().trim();
            if (text.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }
            try {
                return Double.parseDouble(text);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    // Modules 1 and 2: input, output, conditions and loops
    static void addStudent() {
        int roll = readInt("roll number");

        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            System.out.print("Enter student name: ");
            name = sc.nextLine().trim();
        }

        int[] marks = new int[3];
        for (int i = 0; i < marks.length; i++) {
            while (true) {
                marks[i] = readInt("marks for subject " + (i + 1) + " (0-100)");
                if (marks[i] >= 0 && marks[i] <= 100) break;
                System.out.println("Marks must be between 0 and 100.");
            }
        }

        double attendance;
        while (true) {
            attendance = readDouble("attendance percentage (0-100)");
            if (attendance >= 0 && attendance <= 100) break;
            System.out.println("Attendance must be between 0 and 100.");
        }

        students.add(new Student(roll, name, marks, attendance));
        System.out.println("Student added successfully.");
    }

    static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students have been added yet.");
            return;
        }
        for (Student s : students) System.out.println(s);
    }

    static void searchStudent() {
        int roll = readInt("roll number to search");
        for (Student s : students) {
            if (s.roll == roll) {
                System.out.println(s);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Module 5: file writing and exception handling
    static void saveData() {
        try (PrintWriter out = new PrintWriter("students.txt")) {
            for (Student s : students) {
                out.println(s.roll + "," + s.name + "," + s.marks[0] + ","
                        + s.marks[1] + "," + s.marks[2] + "," + s.attendance);
            }
            System.out.println("Saved to students.txt");
        } catch (IOException e) {
            System.out.println("Could not save file.");
        }
    }

    // Module 4: class, object, constructor and method overriding
    static class Student {
        int roll;
        String name;
        int[] marks;
        double attendance;

        Student(int roll, String name, int[] marks, double attendance) {
            this.roll = roll;
            this.name = name;
            this.marks = marks;
            this.attendance = attendance;
        }

        // Module 3: method and array traversal
        double average() {
            int total = 0;
            for (int mark : marks) total += mark;
            return (double) total / marks.length;
        }

        char grade() {
            double avg = average();
            if (avg >= 90) return 'A';
            if (avg >= 75) return 'B';
            if (avg >= 60) return 'C';
            if (avg >= 40) return 'D';
            return 'F';
        }

        // Module 6: stream and lambda
        String marksText() {
            return Arrays.stream(marks)
                    .mapToObj(String::valueOf)
                    .reduce((a, b) -> a + ", " + b).orElse("");
        }

        @Override
        public String toString() {
            return "Roll: " + roll + " | Name: " + name
                    + " | Marks: " + marksText()
                    + " | Average: " + String.format("%.2f", average())
                    + " | Grade: " + grade()
                    + " | Attendance: " + attendance + "%";
        }
    }
}
