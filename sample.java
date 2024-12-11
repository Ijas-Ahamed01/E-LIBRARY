import java.util.Scanner;
import java.io.*;
import java.util.*;

class Book {
    String bn;
    String an;
    int bid;
    int sts;
    int isAvailable;

    public Book() {
        this.bn = "";
        this.an = "";
        this.bid = 0;
        this.sts = 1;
        this.isAvailable = 1;
    }
}

class Student {
    String sn;
    String pw;
    int sid;
    int borrowedBookId;

    public Student() {
        this.sn = "";
        this.pw = "";
        this.sid = 0;
        this.borrowedBookId = -1;
    }
}

public class sample {
    static Book[] b = new Book[10];
    static Student[] s = new Student[10];
    static int id = 101, st = 1201;

    static {
        for (int i = 0; i < 10; i++) {
            b[i] = new Book();
            s[i] = new Student();
        }
    }

    public static void addBooks() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Book Name: ");
        b[id - 101].bn = scanner.nextLine();
        System.out.print("Enter Author Name: ");
        b[id - 101].an = scanner.nextLine();
        b[id - 101].bid = id;
        b[id - 101].sts = 1;
        System.out.println("Book ID is: " + id);
        id++;
        System.out.println("\nBook Added Successfully..!");
    }

    public static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Student Name: ");
        s[st - 1201].sn = scanner.nextLine();
        System.out.print("\nEnter Student Password: ");
        s[st - 1201].pw = scanner.nextLine();
        System.out.println("Student Added Successfully..!");
        System.out.println("Student ID is: " + st);
        s[st - 1201].sid = st;
        st++;
    }

    public static void viewBooks() {
        System.out.println("\nBook Name\tAuthor Name\tBook ID");
        System.out.println("______________________________________");
        for (int i = 0; i < id - 101; i++) {
            if (b[i].sts == 1) {
                System.out.println(b[i].bn + "\t\t" + b[i].an + "\t\t" + b[i].bid);
            }
        }
    }

    public static void viewStudents() {
        System.out.println("\nStudent Name\tStudent ID");
        System.out.println("___________________________");
        for (int i = 0; i < st - 1201; i++) {
            System.out.println(s[i].sn + "\t\t" + s[i].sid);
        }
    }

    public static void LRBooks() {
        Scanner scanner = new Scanner(System.in);
        int ch, tid;
        String tn;

        do {
            System.out.print("\n\n1. lend books\n2. return Books\n3. exit\nEnter your Option: ");
            ch = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (ch == 3) {
                System.out.println("\t\t\texited Successfully");
                break;
            } else if (ch == 1) {
                System.out.print("\nEnter Book Name: ");
                tn = scanner.nextLine();
                System.out.print("Enter Book ID: ");
                tid = scanner.nextInt();
                if (tid >= st) {
                    System.out.println("\nEnter Valid ID..!");
                } else {
                    if (b[tid - 101].bn.equals(tn) && b[tid - 101].sts == 1) {
                        b[tid - 101].sts = 0;
                        System.out.println("\nBook Lended Successfully..!");
                    } else {
                        System.out.println("\nEnter Valid Book Name..!");
                    }
                }
            } else if (ch == 2) {
                System.out.print("\nEnter Book Name: ");
                tn = scanner.nextLine();
                System.out.print("Enter Book ID: ");
                tid = scanner.nextInt();
                if (tid >= st) {
                    System.out.println("\nEnter Valid ID..!");
                } else {
                    if (b[tid - 101].bn.equals(tn) && b[tid - 101].sts == 0) {
                        b[tid - 101].sts = 1;
                        System.out.println("\nBook Returned Successfully..!");
                    } else {
                        System.out.println("\nEnter Valid Book Name or valid ID..!");
                    }
                }
            } else {
                System.out.println("\nEnter Valid Option..!");
            }
        } while (true);
    }

    public static void searchbook(Book[] b, String searchKey, int searchByTitle) {
        boolean found = false;
        System.out.println("Books Found:");
        System.out.println("ID\tTitle\tAuthor");
        System.out.println("________________________");

        for (int i = 0; i < 10; i++) {
            if ((searchByTitle == 1 && b[i].bn.equals(searchKey)) ||
                (searchByTitle == 0 && b[i].an.equals(searchKey))) {
                found = true;
                System.out.println(b[i].bid + "\t" + b[i].bn + "\t" + b[i].an);
            }
        }

        if (!found) {
            System.out.println("Book not found.");
        }
    }

    public static void issueBookForAdmin(Book[] b, int bookCount, Student[] s, int studentCount) {
        Scanner scanner = new Scanner(System.in);
        int sid, bid;

        System.out.println("Available Books:");
        System.out.println("\nBook Name\tAuthor Name\tBook ID");
        System.out.println("______________________________________");
        for (int i = 0; i < bookCount; i++) {
            if (b[i].sts == 1) {
                System.out.println(b[i].bn + "\t\t" + b[i].an + "\t\t" + b[i].bid);
            }
        }

        System.out.print("Enter Student ID: ");
        sid = scanner.nextInt();
        System.out.print("Enter Book ID: ");
        bid = scanner.nextInt();

        int studentIndex = -1, bookIndex = -1;
        for (int i = 0; i < studentCount; i++) {
            if (s[i].sid == sid) {
                studentIndex = i;
                break;
            }
        }
        for (int i = 0; i < bookCount; i++) {
            if (b[i].bid == bid) {
                bookIndex = i;
                break;
            }
        }

        if (studentIndex != -1 && bookIndex != -1) {
            if (b[bookIndex].sts == 1) {
                s[studentIndex].borrowedBookId = bid;
                b[bookIndex].sts = 0; // Set the book as not available
                System.out.println("Book '" + b[bookIndex].bn + "' issued to student '" + s[studentIndex].sn + "'.");
            } else {
                System.out.println("Book '" + b[bookIndex].bn + "' is not available.");
            }
        } else {
            System.out.println("Student or book not found.");
        }
    }

    public static void saveReports(Book[] b, int bookCount, Student[] s, int studentCount) {
        try (FileWriter file = new FileWriter("library_report.txt")) {
            // Write book report
            file.write("Books Report:\n");
            file.write("Book Name\tAuthor Name\tBook ID\n");
            file.write("_______________________________\n");

            for (int i = 0; i < bookCount; i++) {
                file.write(b[i].bn + "\t\t" + b[i].an + "\t\t" + b[i].bid + "\n");
            }

            // Write student report
            file.write("\nStudents Report:\n");
            file.write("Student Name\tStudent ID\n");
            file.write("________________________\n");

            for (int i = 0; i < studentCount; i++) {
                file.write(s[i].sn + "\t\t" + s[i].sid + "\n");
            }

            System.out.println("Reports saved successfully to library_report.txt");
        } catch (IOException e) {
            System.out.println("Error opening file!");
        }
    }

    public static void admin() {
        Scanner scanner = new Scanner(System.in);
        int ch, code;
        System.out.print("\nEnter id: ");
        code = scanner.nextInt();
        if (code == 6969) {
            do {
                System.out.print("\n1. Add Books\n2. Add Students\n3. View Books\n4. View Students\n5. issueBook\n6. saveReports\n7. EXIT\nEnter Your Option:");
                ch = scanner.nextInt();
                switch (ch) {
                    case 1:
                        addBooks();
                        break;
                    case 2:
                        addStudent();
                        break;
                    case 3:
                        viewBooks();
                        break;
                    case 4:
                        viewStudents();
                        break;
                    case 5:
                        issueBookForAdmin(b, id - 101, s, st - 1201);
                        break;
                    case 6:
                        saveReports(b, id - 101, s, st - 1201);
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("\nEnter Valid Option..!");
                }
            } while (true);
        } else {
            System.out.println("\nEnter VALID code");
        }
    }

    public static void student() {
        Scanner scanner = new Scanner(System.in);
        int ch, tid;
        String searchKey;
        int searchByTitle;

        do {
            System.out.print("\n\n1. View Books\n2. Lend & return Books\n3. search Books\n4. Logout\nEnter your Option:");
            ch = scanner.nextInt();
            if (ch == 4) {
                System.out.println("\t\t\tLogged Out Successfully");
                break;
            } else if (ch == 1) {
                viewBooks();
            } else if (ch == 2) {
                LRBooks();
            } else if (ch == 3) {
                System.out.print("Search by Title (1) or author (0): ");
                searchByTitle = scanner.nextInt();
                if (searchByTitle == 1 || searchByTitle == 0) {
                    System.out.print("Enter the search key: ");
                    searchKey = scanner.next();
                    searchbook(b, searchKey, searchByTitle);
                } else {
                    System.out.println("Enter 0 or 1 to go further process\n");
                }
            }
        } while (true);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int ch, tid;
        String pwd;

        System.out.println("***********************************************");
        System.out.println("-----------welcome to Ijas library------------");
        System.out.println("***********************************************");

        do {
            System.out.print("\n\n1. Admin\n2. Students\n3. Exit\nEnter Your Option:");
            ch = scanner.nextInt();
            if (ch == 3) {
                System.out.println("\t\t\tThanks For Visiting..!");
                break;
            } else if (ch == 1) {
                admin();
            } else if (ch == 2) {
                System.out.print("Enter Student ID:");
                tid = scanner.nextInt();
                System.out.print("Enter Student Password:");
                pwd = scanner.next();
                System.out.println("Logged in as: " + s[tid - 1201].sn);
                if (tid > st) {
                    System.out.println("Enter Valid Student ID..!");
                } else {
                    if (s[tid - 1201].pw.equals(pwd)) {
                        student();
                    } else {
                        System.out.println("\nEnter Valid Password\n");
                    }
                }
            } else {
                System.out.println("Enter Valid Option..!");
            }
        } while (true);
    }
}


       

