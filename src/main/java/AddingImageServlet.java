import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("imageUpload")
@MultipartConfig
public class AddingImageServlet extends HttpServlet {

    private final ImageService imageService;

    public AddingImageServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ImageAddingFormDto imageAddingFormDto = new ImageAddingFormDto();
        imageAddingFormDto.setImageName(req.getParameter("imageName"));
        imageAddingFormDto.setImageIS(req.getPart("imageFile").getInputStream());
        imageService.addToDB(imageAddingFormDto);
        resp.sendRedirect("index.jsp");
    }
}
