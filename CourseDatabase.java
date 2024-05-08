import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int schedule;
    private int registeredStudents;

    public Course(String code, String title, String description, int capacity, int schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = 0;
    }

    public void registerStudent() {
        registeredStudents++;
    }

    public void unregisterStudent() {
        registeredStudents--;
    }

    public boolean isFull() {
        return registeredStudents >= capacity;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSchedule() {
        return schedule;
    }

    public int getRegisteredStudents() {
        return registeredStudents;
    }
}

class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
        course.registerStudent();
    }

    public void unregisterCourse(Course course) {
        registeredCourses.remove(course);
        course.unregisterStudent();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }
}

public class CourseDatabase {
    private Map<String, Course> courses;
    private Map<String, Student> students;
    private Scanner scanner;

    public CourseDatabase() {
        this.courses = new HashMap<>();
        this.students = new HashMap<>();
        this.scanner = new Scanner(System.in);

        // Add sample courses
        courses.put("CS101", new Course("CS101", "Introduction to Computer Science",
                "An introduction to computer science concepts and programming.", 30, 1));
        courses.put("MATH101", new Course("MATH101", "Calculus I",
                "A first course in calculus, covering differentiation and integration.", 25, 2));

        // Add sample students
        students.put("123456", new Student("123456", "John Doe"));
        students.put("234567", new Student("234567", "Sai"));
        students.put("345678", new Student("345678", "John"));
        students.put("456789", new Student("456789", "Doe"));
students.put("124578", new Student("124578", "Doe"));
students.put("235689", new Student("235689", "Doe"));
students.put("794613", new Student("794613", "Doe"));
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nCourse Database Menu:");
            System.out.println("1. Course Listing");
            System.out.println("2. Student Registration");
            System.out.println("3. Student Deregistration");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    courseListing();
                    break;
                case 2:
                    studentRegistration();
                    break;
                case 3:
                    studentDeregistration();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the Course Database. Goodbye!");
        scanner.close();
    }

    private void courseListing() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses.values()) {
            System.out.printf("%s - %s (%d/%d students)\n", course.getCode(), course.getTitle(),
                    course.getRegisteredStudents(), course.getCapacity());
        }
    }

    private void studentRegistration() {
        System.out.print("Enter student ID: ");
        String id = scanner.next();

        if (students.containsKey(id)) {
            Student student = students.get(id);
            System.out.println("Available Courses:");
            for (Course course : courses.values()) {
                if (!course.isFull()) {
                    System.out.printf("%s - %s (%d/%d students)\n", course.getCode(), course.getTitle(),
                            course.getRegisteredStudents(), course.getCapacity());
                }
            }

            System.out.print("Enter course code to register: ");
            String code = scanner.next();

            if (courses.containsKey(code)) {
                Course course = courses.get(code);
                if (!course.isFull()) {
                    student.registerCourse(course);
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Course is full. Registration failed.");
                }
            } else {
                System.out.println("Invalid course code.");
            }
        } else {
            System.out.println("Invalid student ID.");
        }
    }

    private void studentDeregistration() {
        System.out.print("Enter student ID: ");
        String id = scanner.next();

        if (students.containsKey(id)) {
            Student student = students.get(id);
            System.out.println("Registered Courses:");
            for (Course course : student.getRegisteredCourses()) {
                System.out.printf("%s - %s (%d/%d students)\n", course.getCode(), course.getTitle(),
                        course.getRegisteredStudents(), course.getCapacity());
            }

            System.out.print("Enter course code to deregister: ");
            String code = scanner.next();

            if (courses.containsKey(code)) {
                Course course = courses.get(code);
                if (student.getRegisteredCourses().contains(course)) {
                    student.unregisterCourse(course);
                    System.out.println("Deregistration successful!");
                } else {
                    System.out.println("Student is not registered for this course.");
                }
            } else {
                System.out.println("Invalid course code.");
            }
        } else {
            System.out.println("Invalid student ID.");
        }
    }

    public static void main(String[] args) {
        CourseDatabase courseDatabase = new CourseDatabase();
        courseDatabase.start();
    }
}