import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class DeleteServlet extends HttpServlet {
    // localhost:9000/delete?username=&name=
    private static final String DB_NAME = "ebay_hw";
    private static final String TABLE_NAME = "products";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "ebayuser";
    private static final String PASSWORD = "12345678";
    private static final String HOSTNAME = "ebayhwdbinstance.c22dwmyrdcid.us-east-1.rds.amazonaws.com";
    private static final String PORT = "3306";
    private static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DB_NAME + "?user=" + USERNAME + "&password=" + PASSWORD;

    private static Connection connection;

    public DeleteServlet() {
        try {
            initDBConncetion();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // query the database by username and product name
        String username = request.getParameter("username");
        String name = request.getParameter("name");

        Statement statement = null;
        try {
            statement = connection.createStatement();
            String deleteCmd = "DELETE FROM " + TABLE_NAME + " WHERE username='" + username + "' and name='" + name + "';";
            statement.execute(deleteCmd);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void initDBConncetion() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
