import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class UpdateServlet extends HttpServlet {
    // localhost:9000/update?name=name&price=price&description=description&username=username
    private static final String DB_NAME = "ebay_hw";
    private static final String TABLE_NAME = "products";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "ebayuser";
    private static final String PASSWORD = "12345678";
    private static final String HOSTNAME = "ebayhwdbinstance.c22dwmyrdcid.us-east-1.rds.amazonaws.com";
    private static final String PORT = "3306";
    private static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DB_NAME + "?user=" + USERNAME + "&password=" + PASSWORD;

    private static Connection connection;

    public UpdateServlet() {
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
        // these fields are optional, can be null if not updated
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        String username = request.getParameter("username");
        Statement statement = null;
        try {
            statement = connection.createStatement();

            // query by username and product name
            String query = "SELECT * from " + TABLE_NAME + " WHERE name='" + name + "' AND username='" + username + "';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            double price = rs.getDouble("price");
            if (priceStr != null) {
                price = Double.valueOf(priceStr);
            }

            if (description == null) {
                description = rs.getString("description");
            }

            String updateQuery = "UPDATE " + TABLE_NAME + " SET price=" + price + ", description='" + description + "' WHERE name='" + name + "' AND username='" + username + "';";
            statement.execute(updateQuery);
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
