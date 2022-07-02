import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("imageUpload")
@MultipartConfig
public class AddingImageServlet extends HttpServlet {

    private final ImageService imageService;
    private final AmazonS3Service amazonS3Service = new AmazonS3Service();

    public AddingImageServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws jakarta.servlet.ServletException, IOException {
        final ImageAddingFormDto imageAddingFormDto = new ImageAddingFormDto();
        imageAddingFormDto.setImageName(req.getParameter("imageName"));
        imageAddingFormDto.setImageIS(req.getPart("imageFile").getInputStream());
        String key = amazonS3Service.addToS3(imageAddingFormDto.getImageIS());
        imageService.addToDB(imageAddingFormDto, key);
        resp.sendRedirect("index.jsp");
    }
}
