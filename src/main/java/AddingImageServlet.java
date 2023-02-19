import configuration.amazon.AmazonS3Service;
import image.Image;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("imageUpload")
@MultipartConfig
public class AddingImageServlet extends HttpServlet {

    private static final int STATUS_CODE_OK = 200;
    private static final String STATUS_TEXT_ATTRIBUTE = "message";
    private static final String IMAGE_FILE_ATTRIBUTE = "imageFile";

    private final ImageService imageService;
    private final AmazonS3Service amazonS3Service;

    public AddingImageServlet(ImageService imageService, AmazonS3Service amazonS3Service) {
        this.imageService = imageService;
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    protected void doGet(
        jakarta.servlet.http.HttpServletRequest req,
        jakarta.servlet.http.HttpServletResponse resp
    ) throws jakarta.servlet.ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(
        jakarta.servlet.http.HttpServletRequest req,
        jakarta.servlet.http.HttpServletResponse resp
    ) throws jakarta.servlet.ServletException, IOException {

        imageService.saveImage(new Image(
            req.getParameter("imageName"),
            LocalDateTime.now(),
            amazonS3Service.upload(req.getPart(IMAGE_FILE_ATTRIBUTE).getInputStream()),
            req.getPart(IMAGE_FILE_ATTRIBUTE).getInputStream().readAllBytes().length
        ));

        if (resp.getStatus() == STATUS_CODE_OK) {
            req.setAttribute(STATUS_TEXT_ATTRIBUTE, "Image successful uploaded");
        } else {
            req.setAttribute(STATUS_TEXT_ATTRIBUTE, "There is error during uploading. Please, try again!");
        }
        req.getRequestDispatcher("uploadStatus.jsp").forward(req, resp);
    }
}
