import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
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
        List<ImageDto> imageDtoList = new ArrayList<>();
        for (Image image : imageService.getGlobalTop()) {
            imageDtoList.add(new ImageDto(
                image,
                amazonS3Service.getImageAsBase64String(image.getS3ObjectKey())
            ));
        }
        req.setAttribute("imagesList", imageDtoList);
        req.getRequestDispatcher("globalTop.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}