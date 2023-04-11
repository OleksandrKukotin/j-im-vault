import configuration.amazon.AmazonS3Service;
import image.Image;
import image.ImageDto;
import image.ImageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ImageUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("displayImages")
public class DisplayImagesServlet extends HttpServlet {

    static final String NOT_FOUND_STYLE_ATTRIBUTE = "notFoundStyle";
    static final String NOT_FOUND_MESSAGE_ATTRIBUTE = "notFoundMessage";
    private final ImageService imageService;
    private final AmazonS3Service amazonS3Service;

    public DisplayImagesServlet(ImageService imageService, AmazonS3Service amazonS3Service) {
        this.imageService = imageService;
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Image> images = imageService.findAllImages();
        // TODO: Consider adding pagination or lazy loading for cases when there are a lot of images to display, to improve performance and reduce the load on the server.
        // TODO: Consider adding sorting options for the displayed images, such as by name or date, to make it easier for users to find specific images.
        // TODO: Improve error handling by catching and handling more specific exceptions, rather than just catching and displaying a generic error message.
        // TODO: Consider adding support for filtering images by name or other attributes, to allow users to find specific images more easily.
        if (!images.isEmpty()) {
            List<ImageDto> imageDtos = ImageUtils.imagesToImageDtosMapper(images, amazonS3Service::getAsBase64);
            req.setAttribute("imageDtos", imageDtos);
        } else {
            req.setAttribute(NOT_FOUND_MESSAGE_ATTRIBUTE, "An error occurred during getting images =(");
            req.setAttribute(NOT_FOUND_STYLE_ATTRIBUTE, "style = \"display : none\"");
        }
        req.getRequestDispatcher("globalTop.jsp").forward(req, resp);
    }
}