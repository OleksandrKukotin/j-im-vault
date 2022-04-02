import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 5 * 5)
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
        Part imagePart = req.getPart("imageFile");
        String filePath = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
        imageAddingFormDto.setImageFile(new File(filePath));
        imageService.addToDB(imageAddingFormDto);
        resp.sendRedirect("imageUpload.jsp");
    }
}
