import java.io.*;
import java.util.*;

class Student {
    private int rollNo;
    private String name;
    private String email;
    private String course;
    private double marks;

    public Student(int rollNo, String name, String email, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
    public double getMarks() { return marks; }

    @Override
    public String toString() {
        return "Roll No: " + rollNo + "\nName: " + name + "\nEmail: " + email + 
               "\nCourse: " + course + "\nMarks: " + marks + "\n";
    }

    public String toFileFormat() {
        return rollNo + "," + name + "," + email + "," + course + "," + marks;
    }
}

class FileUtil {
    public static ArrayList<Student> readStudents(String filename) {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    students.add(new Student(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        parts[3],
                        Double.parseDouble(parts[4])
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Starting with empty records.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return students;
    }

    public static void writeStudents(String filename, ArrayList<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                bw.write(s.toFileFormat());
                bw.newLine();
            }
            System.out.println("Records saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

class StudentManager {
    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    public void loadStudents(String filename) {
        students = FileUtil.readStudents(filename);
        if (!students.isEmpty()) {
            System.out.println("Loaded students from file:");
            displayAll();
        }
    }

    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully!");
    }

    public void displayAll() {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void searchByName(String name) {
        boolean found = false;
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No student found with name: " + name);
        }
    }

    public void deleteByName(String name) {
        Iterator<Student> it = students.iterator();
        boolean removed = false;
        while (it.hasNext()) {
            if (it.next().getName().equalsIgnoreCase(name)) {
                it.remove();
                removed = true;
            }
        }
        System.out.println(removed ? "Student deleted successfully!" : "Student not found.");
    }

    public void sortByMarks() {
        students.sort(Comparator.comparingDouble(Student::getMarks).reversed());
        System.out.println("\nSorted Student List by Marks:");
        displayAll();
    }

    public void saveStudents(String filename) {
        FileUtil.writeStudents(filename, students);
    }
}

public class StudentRecordSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();
        String filename = "students.txt";

        manager.loadStudents(filename);

        while (true) {
            System.out.println("NAME: ABHINAV ADARSH");
            System.out.println("ROLL No.:2401010298");
            System.out.println("\n===== Capstone Student Menu =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search by Name");
            System.out.println("4. Delete by Name");
            System.out.println("5. Sort by Marks");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll No: ");
                    int rollNo = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    manager.addStudent(new Student(rollNo, name, email, course, marks));
                    break;
                case 2:
                    manager.displayAll();
                    break;
                case 3:
                    System.out.print("Enter Name to search: ");
                    manager.searchByName(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Enter Name to delete: ");
                    manager.deleteByName(sc.nextLine());
                    break;
                case 5:
                    manager.sortByMarks();
                    break;
                case 6:
                    manager.saveStudents(filename);
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
