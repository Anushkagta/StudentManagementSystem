import java.util.*;

// Base class (Parent)
class User {
    String name, email;
    int age;

    User(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
}

// Admin class inherits from User
class Admin extends User {
    Admin(String name, int age, String email) {
        super(name, age, email);
    }

    void addCourse(ArrayList<Course> courses, Scanner sc) {
        System.out.print("Enter course name: ");
        String cname = sc.nextLine();
        courses.add(new Course(cname));
        System.out.println("Course added!");
    }

    void addSubjectToCourse(ArrayList<Course> courses, Scanner sc) {
        for (int i = 0; i < courses.size(); i++)
            System.out.println((i + 1) + ". " + courses.get(i).name);
        System.out.print("Choose course number: ");
        int cnum = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter subject name: ");
        String subject = sc.nextLine();
        courses.get(cnum - 1).addSubject(subject);
        System.out.println("Subject added!");
    }

    void viewStudents(ArrayList<Student> students) {
        for (Student s : students) {
            System.out.println(s.name + " | " + s.email + " | " + s.course);
        }
    }

    void viewResults(ArrayList<Student> students) {
        for (Student s : students) {
            System.out.println(s.name + ": " + (s.score == -1 ? "No exam taken" : s.score + "/5"));
        }
    }
}

// Student class inherits from User
class Student extends User {
    String course;
    ArrayList<String> chosenSubjects = new ArrayList<>();
    int score = -1;

    Student(String name, int age, String email) {
        super(name, age, email);
    }

    void takeExam(Scanner sc) {
        String[] questions = {
                "Q1: Java is a ...?\n1. OOP\n2. Procedural\n3. Script\n4. None",
                "Q2: Which is used to inherit?\n1. final\n2. import\n3. extends\n4. static",
                "Q3: JVM stands for?\n1. Java Virtual Machine\n2. Java Visual Model\n3. None",
                "Q4: 'int' is a?\n1. Class\n2. Object\n3. Primitive\n4. None",
                "Q5: Which is not a loop?\n1. for\n2. while\n3. loopit\n4. do-while"
        };
        int[] answers = {1, 3, 1, 3, 3};

        int correct = 0;
        for (int i = 0; i < 5; i++) {
            System.out.println(questions[i]);
            int ans = sc.nextInt();
            if (ans == answers[i]) correct++;
        }

        score = correct;
        System.out.println("You scored: " + correct + "/5");
        if (correct >= 3) System.out.println("Result: PASS");
        else System.out.println("Result: FAIL");
    }
}

// Course class remains the same
class Course {
    String name;
    ArrayList<String> subjects = new ArrayList<>();

    Course(String name) {
        this.name = name;
    }

    void addSubject(String subject) {
        subjects.add(subject);
    }
}

// Main class
public class StudentManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        Admin admin = new Admin("Admin", 30, "admin@school.com");

        while (true) {
            System.out.println("\n1. Admin\n2. Student\n3. Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                while (true) {
                    System.out.println("\nAdmin Menu:");
                    System.out.println("1. Add Course");
                    System.out.println("2. Add Subject to Course");
                    System.out.println("3. View All Students");
                    System.out.println("4. View Student Results");
                    System.out.println("5. Back");

                    int adminChoice = sc.nextInt();
                    sc.nextLine();

                    switch (adminChoice) {
                        case 1: admin.addCourse(courses, sc); break;
                        case 2: admin.addSubjectToCourse(courses, sc); break;
                        case 3: admin.viewStudents(students); break;
                        case 4: admin.viewResults(students); break;
                        case 5: break;
                        default: System.out.println("Invalid choice.");
                    }
                    if (adminChoice == 5) break;
                }
            } else if (choice == 2) {
                System.out.print("Enter your name: ");
                String name = sc.nextLine();
                System.out.print("Enter age: ");
                int age = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter email: ");
                String email = sc.nextLine();

                Student st = new Student(name, age, email);

                // Choose Course
                if (courses.size() == 0) {
                    System.out.println("No courses available yet.");
                    continue;
                }

                System.out.println("Available Courses:");
                for (int i = 0; i < courses.size(); i++)
                    System.out.println((i + 1) + ". " + courses.get(i).name);

                System.out.print("Choose course number: ");
                int courseNum = sc.nextInt();
                sc.nextLine();
                st.course = courses.get(courseNum - 1).name;

                // Choose Subjects
                Course selectedCourse = courses.get(courseNum - 1);
                System.out.println("Subjects in " + selectedCourse.name + ":");
                for (String s : selectedCourse.subjects)
                    System.out.println("- " + s);
                System.out.print("Enter subjects (comma separated): ");
                String[] subs = sc.nextLine().split(",");
                for (String s : subs)
                    st.chosenSubjects.add(s.trim());

                // Take Exam
                System.out.println("Start Exam:");
                st.takeExam(sc);

                // Add to student list
                students.add(st);
            } else {
                System.out.println("Goodbye!");
                break;
            }
        }

        sc.close();
    }
}

