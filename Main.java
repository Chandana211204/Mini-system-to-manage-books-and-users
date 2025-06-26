import java.util.*;

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issueBook() { isIssued = true; }
    public void returnBook() { isIssued = false; }

    @Override
    public String toString() {
        return bookId + " - " + title + " by " + author + (isIssued ? " [Issued]" : " [Available]");
    }
}

class User {
    private int userId;
    private String name;
    private ArrayList<Book> issuedBooks;

    public User(int userId, String name) {
        this.userId = userId;
        this.name = name;
        this.issuedBooks = new ArrayList<>();
    }

    public int getUserId() { return userId; }
    public String getName() { return name; }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
    }

    public void showIssuedBooks() {
        System.out.println("Books issued to " + name + ":");
        if (issuedBooks.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Book b : issuedBooks) {
                System.out.println("  - " + b.getTitle());
            }
        }
    }

    @Override
    public String toString() {
        return userId + " - " + name;
    }
}

class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private Random random;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        random = new Random();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addUser(String name) {
        int id;
        do {
            id = 100 + random.nextInt(900); // Generates ID from 100 to 999
        } while (findUserById(id) != null);

        User newUser = new User(id, name);
        users.add(newUser);
        System.out.println("User added successfully. User ID: " + id);
    }

    public void showAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        System.out.println("Registered Users:");
        for (User u : users) {
            System.out.println("  - " + u);
        }
    }

    public Book findBookById(int id) {
        for (Book b : books) {
            if (b.getBookId() == id) return b;
        }
        return null;
    }

    public User findUserById(int id) {
        for (User u : users) {
            if (u.getUserId() == id) return u;
        }
        return null;
    }

    public void issueBook(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null || user == null) {
            System.out.println("Book or User not found.");
            return;
        }

        if (book.isIssued()) {
            System.out.println("Book is already issued.");
        } else {
            book.issueBook();
            user.issueBook(book);
            System.out.println("Book issued successfully.");
        }
    }

    public void returnBook(int bookId, int userId) {
        Book book = findBookById(bookId);
        User user = findUserById(userId);

        if (book == null || user == null) {
            System.out.println("Book or User not found.");
            return;
        }

        if (book.isIssued()) {
            book.returnBook();
            user.returnBook(book);
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book is not issued.");
        }
    }

    public void showAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("Books in Library:");
        for (Book b : books) {
            System.out.println("  - " + b);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        // Initial sample data
        library.addBook(new Book(1, "Java Basics", "James Gosling"));
        library.addBook(new Book(2, "Python Crash Course", "Eric Matthes"));
        library.addUser("Alice");
        library.addUser("Bob");

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Show All Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Show User's Issued Books");
            System.out.println("5. Add User");
            System.out.println("6. Show All Users");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    library.showAllBooks();
                    break;
                case 2:
                    System.out.print("Enter Book ID: ");
                    int bId = scanner.nextInt();
                    System.out.print("Enter User ID: ");
                    int uId = scanner.nextInt();
                    library.issueBook(bId, uId);
                    break;
                case 3:
                    System.out.print("Enter Book ID: ");
                    int rbId = scanner.nextInt();
                    System.out.print("Enter User ID: ");
                    int ruId = scanner.nextInt();
                    library.returnBook(rbId, ruId);
                    break;
                case 4:
                    System.out.print("Enter User ID: ");
                    int userId = scanner.nextInt();
                    User user = library.findUserById(userId);
                    if (user != null) {
                        user.showIssuedBooks();
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter New User Name: ");
                    String name = scanner.nextLine();
                    library.addUser(name);
                    break;
                case 6:
                    library.showAllUsers();
                    break;
                case 0:
                    System.out.println("Exiting... Bye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
