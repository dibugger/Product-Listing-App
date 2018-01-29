import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class GetByUserServlet extends HttpServlet {
    private static final String DB_NAME = "ebay_hw";
    private static final String TABLE_NAME = "products";
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String USERNAME = "ebayuser";
    private static final String PASSWORD = "12345678";
    private static final String HOSTNAME = "ebayhwdbinstance.c22dwmyrdcid.us-east-1.rds.amazonaws.com";
    private static final String PORT = "3306";
    private static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DB_NAME + "?user=" + USERNAME + "&password=" + PASSWORD;

    private static Connection connection;

    public GetByUserServlet() {
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
        String username = request.getParameter("username");
        String query = "SELECT * from " + TABLE_NAME + " WHERE username=BINARY('" + username + "') ORDER BY date_last_updated DESC;";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            JSONArray jsonArray = new JSONArray();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("name", name);
                jsonObject.put("price", price);
                jsonObject.put("description", description);
                jsonObject.put("username", username);
                jsonArray.put(jsonObject);
            }
            PrintWriter writer = response.getWriter();
            writer.write(jsonArray.toString());
            writer.flush();
            writer.close();
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
