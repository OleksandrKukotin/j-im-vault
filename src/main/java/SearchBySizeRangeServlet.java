import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("searchBySizeRange")
public class SearchBySizeRangeServlet extends HttpServlet {

    private static final String MESSAGE_ATTRIBUTE = "notFoundMessage";
    private static final String NOT_FOUND_STYLE = "notFoundStyle";
    private static final String JSP_LOOP_TARGET_COLLECTION_ATTRIBUTE = "imageDtos";
    static final String OUTPUT_JSP = "globalTop.jsp";

    private final ImageService imageService;
    private final AmazonS3Service amazonS3Service;

    public SearchBySizeRangeServlet(ImageService imageService, AmazonS3Service amazonS3Service) {
        this.imageService = imageService;
        this.amazonS3Service = amazonS3Service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("searchByImageSizeRange.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Image> images = imageService.findImagesInSizeRange(
            Integer.parseInt(req.getParameter("from")),
            Integer.parseInt(req.getParameter("to"))
        );
        if (!images.isEmpty()) {
            List<ImageDto> imageDtos = ImageUtils.imagesToImageDtosConverter(images, amazonS3Service);
            req.setAttribute(JSP_LOOP_TARGET_COLLECTION_ATTRIBUTE, imageDtos);
        } else {
            req.setAttribute(MESSAGE_ATTRIBUTE, "Images were not found in this range.");
            req.setAttribute(NOT_FOUND_STYLE, "style = \"display : none\"");
        }
        req.getRequestDispatcher(OUTPUT_JSP).forward(req, resp);
    }
}
