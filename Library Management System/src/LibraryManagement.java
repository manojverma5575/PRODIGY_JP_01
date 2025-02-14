import java.sql.*;
import java.util.Scanner;

public class LibraryManagement {
    private static final String URL = "jdbc:mysql://localhost:3306/Library";
    private static final String USER = "root";  // Change if needed
    private static final String PASSWORD = "1234";  // Change if needed

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addBook(String title, String author, String isbn) {
        String query = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.executeUpdate();
            System.out.println("✅ Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewBooks() {
        String query = "SELECT * FROM books";
        System.out.println("+-----+----------------------+----------------+--------------+---------+");
        System.out.println("| ID  | Title                | Author         | ISBN         | Issued  |");
        System.out.println("+-----+----------------------+----------------+--------------+---------+");

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                System.out.printf("| %-3d | %-20s | %-14s | %-12s | %-7s |\n",
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getBoolean("isIssued") ? "Yes" : "No");

                System.out.println("+-----+----------------------+----------------+--------------+---------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBook(int id, String column, String newValue) {
        String query = "UPDATE books SET " + column + " = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newValue);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Book updated successfully!");
            else System.out.println("⚠️ Book not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void issueBook(int id) {
        String query = "UPDATE books SET isIssued = TRUE WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Book issued successfully!");
            else System.out.println("⚠️ Book not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void returnBook(int id) {
        String query = "UPDATE books SET isIssued = FALSE WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Book returned successfully!");
            else System.out.println("⚠️ Book not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteBook(int id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("✅ Book deleted successfully!");
            else System.out.println("⚠️ Book not found!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n📚 Library Management System");
            System.out.println("1️⃣ Add Book");
            System.out.println("2️⃣ View Books");
            System.out.println("3️⃣ Update Book");
            System.out.println("4️⃣ Issue Book");
            System.out.println("5️⃣ Return Book");
            System.out.println("6️⃣ Delete Book");
            System.out.println("7️⃣ Exit");
            System.out.print("🔹 Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("📖 Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("✍️ Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("📚 Enter ISBN: ");
                    String isbn = sc.nextLine();
                    addBook(title, author, isbn);
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    System.out.print("🔄 Enter Book ID to Update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();
                    System.out.println("📌 Select field to update: ");
                    System.out.println("1️⃣ Title");
                    System.out.println("2️⃣ Author");
                    System.out.println("3️⃣ ISBN");
                    System.out.print("🔹 Your choice: ");
                    int fieldChoice = sc.nextInt();
                    sc.nextLine();
                    String column = "";
                    if (fieldChoice == 1) column = "title";
                    else if (fieldChoice == 2) column = "author";
                    else if (fieldChoice == 3) column = "isbn";
                    else {
                        System.out.println("⚠️ Invalid choice! Try again.");
                        break;
                    }
                    System.out.print("✍️ Enter new value: ");
                    String newValue = sc.nextLine();
                    updateBook(updateId, column, newValue);
                    break;
                case 4:
                    System.out.print("📕 Enter Book ID to Issue: ");
                    int issueId = sc.nextInt();
                    issueBook(issueId);
                    break;
                case 5:
                    System.out.print("📗 Enter Book ID to Return: ");
                    int returnId = sc.nextInt();
                    returnBook(returnId);
                    break;
                case 6:
                    System.out.print("🗑️ Enter Book ID to Delete: ");
                    int deleteId = sc.nextInt();
                    deleteBook(deleteId);
                    break;
                case 7:
                    System.out.println("🚪 Exiting... Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("⚠️ Invalid choice! Try again.");
            }
        }
    }
}
