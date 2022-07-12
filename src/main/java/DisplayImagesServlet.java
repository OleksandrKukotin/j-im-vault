import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("displayImages")
public class DisplayImagesServlet extends HttpServlet {

    private final ImageService imageService;
    private final AmazonS3Service amazonS3Service = new AmazonS3Service();

    public DisplayImagesServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ImageDisplayDto> imageDisplayDtoList = imageService.getGlobalTop();
        for (ImageDisplayDto dto : imageDisplayDtoList) {
            String base64Image = Base64.getEncoder().encodeToString(amazonS3Service.getImageAsBytes(dto.getKey()));
            dto.setBase64Image(base64Image);
        }
        req.setAttribute("imagesList", imageDisplayDtoList);
        req.getRequestDispatcher("globalTop.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}