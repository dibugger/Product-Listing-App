import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.SetHeaderHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.ServletException;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;

public class MainSite {

    private static final String PATH = "/";

    public MainSite() throws Exception {

    }

    public static void main(String[] args) throws Exception {
        try {
            DeploymentInfo servletBuilder = deployment()
                    .setClassLoader(MainSite.class.getClassLoader())
                    .setDeploymentName("handler.war")
                    .setContextPath(PATH)
                    .addServlets(
                            servlet("SearchServlet", SearchServlet.class).addMapping("/search"),
                            servlet("UploadServlet", UploadServlet.class).addMapping("/upload"),
                            servlet("UpdateServlet", UpdateServlet.class).addMapping("/update"),
                            servlet("DeleteServlet", DeleteServlet.class).addMapping("/delete"),
                            servlet("GetByUserServlet", GetByUserServlet.class).addMapping("/get-by-user"),
                            servlet("GetAllServlet", GetAllServlet.class).addMapping("/get-all"),
                            servlet("GetByPriceServlet", GetByPriceServlet.class).addMapping("/get-by-price")
                    );
            DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
            manager.deploy();
            HttpHandler servletHandler = manager.start();
            PathHandler pathHandler = Handlers.path(Handlers.redirect(PATH))
                    .addPrefixPath(PATH, servletHandler);
            Undertow server = Undertow.builder()
                    .addHttpListener(9000, "0.0.0.0")
                    .setHandler(pathHandler)
                    .build();
            server.start();
        } catch (ServletException e) {
            throw new RuntimeException();
        }
    }
}
