import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.time.LocalDateTime;

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
        imageAddingFormDto.setName(req.getParameter("imageName"));
        imageAddingFormDto.setKey(amazonS3Service.addToS3(req.getPart("imageFile").getInputStream()));
        imageAddingFormDto.setTimeOfAdding(LocalDateTime.now());
        imageService.addToDB(imageAddingFormDto);
        if (resp.getStatus() == 200) {
            req.setAttribute("message", "Image successful uploaded!");
        } else {
            req.setAttribute("message", "There is error during uploading! Please, try again!");
        }
        req.getRequestDispatcher("uploadStatus.jsp").forward(req, resp);
    }
}
