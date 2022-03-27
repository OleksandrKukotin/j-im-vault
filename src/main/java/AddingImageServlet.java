import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class AddingImageServlet extends HttpServlet {

    private final ImageService imageService;

    public AddingImageServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("imageUpload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final ImageAddingFormDto imageAddingFormDto = new ImageAddingFormDto();
        imageAddingFormDto.setImageName(req.getParameter("imageName"));
        imageAddingFormDto.setImageFile(new File(req.getParameter("imageFile")));
        imageService.addToDB(imageAddingFormDto);
        resp.sendRedirect("imageUpload.jsp");
    }
    //TODO: Add parsing multipart data from form
}
