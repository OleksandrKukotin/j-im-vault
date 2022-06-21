import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("imagesPreview")
public class DisplayImagesServlet extends HttpServlet {

    private final ImageService imageService;
    private final AmazonS3Service amazonS3Service = new AmazonS3Service();

    public DisplayImagesServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultSet rs = imageService.getAll();
        List<Image> imagesList = new ArrayList<>();
        try {
            while (rs.next()) {
                Image image = new Image();
                image.setImageName(rs.getString("imgname"));
                image.setImageIS(amazonS3Service.getInputStreamFromS3(rs.getString("key")));
                imagesList.add(image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(imagesList.get(1).getImageName());
        req.setAttribute("imagesList", imagesList);
        req.getRequestDispatcher("imagesPreview.jsp").forward(req, resp);
    }
}