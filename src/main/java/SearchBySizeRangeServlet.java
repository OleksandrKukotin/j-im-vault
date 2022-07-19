import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("searchBySizeRange")
public class SearchBySizeRangeServlet extends HttpServlet {

    private final ImageService imageService;
    private final AmazonS3Service amazonS3Service = new AmazonS3Service();

    public SearchBySizeRangeServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("searchByImageSizeRange.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ImageDto> imageDtoList = imageService.getTopBySizeRange(Integer.parseInt(req.getParameter("min")),
            Integer.parseInt(req.getParameter("max")));
        for (ImageDto dto : imageDtoList) {
            String base64Image = Base64.getEncoder().encodeToString(amazonS3Service.getImageAsBytes(dto.getS3ObjectKey()));
            dto.setBase64Image(base64Image);
        }
        req.setAttribute("imagesList", imageDtoList);
        req.getRequestDispatcher("globalTop.jsp").forward(req, resp);
    }
}
