import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("displayImages")
public class DisplayImagesServlet extends HttpServlet {

    private final ImageService imageService;
    // TODO: create AmazonS3Service inside ImagesUploaderApplication and inject in current class through constructor (same as ImageService)
    private final AmazonS3Service amazonS3Service = new AmazonS3Service();

    public DisplayImagesServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: --- code in current method like a code in DisplayImagesServlet. You can extract code below to some utility class with name ImageUtil
        // TODO: and create a method f.e. List<ImageDto> toImageDtosConverter(List<Image> images) {...}

        List<Image> imagesList = imageService.getGlobalTop();
        List<ImageDto> imageDtoList = new ArrayList<>();
        if (!imagesList.isEmpty()){ // TODO: dont forget about spaces, dont forget about java conventions
            // TODO: Rewrite this loop to streams (I mean stream API)
            for (Image image : imagesList) {
                imageDtoList.add(new ImageDto(
                    image,
                    amazonS3Service.getImageAsBase64String(image.getS3ObjectKey())
                ));
            }
        // TODO: --- after changes fix DisplayImagesServlet please
        } else {
            req.setAttribute("notFoundMessage", "An error occurred during getting images =(");
            req.setAttribute("notFoundStyle", "style = \"display : none\"");
        }
        req.setAttribute("imageDtosList", imageDtoList); // TODO: rename from imageDtosList to imageDtos
        req.getRequestDispatcher("globalTop.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}