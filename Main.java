import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/app_db";
        String user = "root";
        String password = "passwordplaceholder";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
                DriverManager.getConnection(url, user, password);

            int choice;

            do {
                System.out.println("\n1. Add Student Marks");
                System.out.println("2. Update Student Marks");
                System.out.println("3. Display Average Above 75");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();

                        System.out.print("Enter name: ");
                        String name = sc.next();

                        System.out.print("Enter marks in 3 subjects: ");
                        int m1 = sc.nextInt();
                        int m2 = sc.nextInt();
                        int m3 = sc.nextInt();

                        int total = m1 + m2 + m3;
                        double avg = total / 3.0;

                        String sql =
                            "INSERT INTO student_marks " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement ps =
                            con.prepareStatement(sql);

                        ps.setInt(1, id);
                        ps.setString(2, name);
                        ps.setInt(3, m1);
                        ps.setInt(4, m2);
                        ps.setInt(5, m3);
                        ps.setInt(6, total);
                        ps.setDouble(7, avg);

                        ps.executeUpdate();
                        ps.close();

                        System.out.println("Student added");
                        break;
                    }

                    case 2: {
                        System.out.print("Enter student ID: ");
                        int id = sc.nextInt();

                        System.out.print("Enter new marks in 3 subjects: ");
                        int m1 = sc.nextInt();
                        int m2 = sc.nextInt();
                        int m3 = sc.nextInt();

                        int total = m1 + m2 + m3;
                        double avg = total / 3.0;

                        String sql =
                            "UPDATE student_marks SET " +
                            "subject1=?, subject2=?, subject3=?, " +
                            "total=?, average=? WHERE id=?";

                        PreparedStatement ps =
                            con.prepareStatement(sql);

                        ps.setInt(1, m1);
                        ps.setInt(2, m2);
                        ps.setInt(3, m3);
                        ps.setInt(4, total);
                        ps.setDouble(5, avg);
                        ps.setInt(6, id);

                        int rows = ps.executeUpdate();
                        ps.close();

                        if (rows > 0)
                            System.out.println("Marks updated");
                        else
                            System.out.println("Student not found");

                        break;
                    }

                    case 3: {
                        String sql =
                            "SELECT * FROM student_marks " +
                            "WHERE average > 75";

                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql);

                        while (rs.next()) {
                            System.out.println(
                                rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getInt("total") + " " +
                                rs.getDouble("average")
                            );
                        }

                        rs.close();
                        st.close();
                        break;
                    }

                    case 4:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice");
                }

            } while (choice != 4);

            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        sc.close();
    }
}