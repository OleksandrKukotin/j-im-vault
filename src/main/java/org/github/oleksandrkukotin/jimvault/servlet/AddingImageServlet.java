package org.github.oleksandrkukotin.jimvault.servlet;

import org.github.oleksandrkukotin.jimvault.service.S3Service;
import org.github.oleksandrkukotin.jimvault.image.Image;
import org.github.oleksandrkukotin.jimvault.service.ImageService;
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

    private final S3Service s3Service;
    private final ImageService imageService;

    public AddingImageServlet(ImageService imageService, S3Service s3Service) {
        this.imageService = imageService;
        this.s3Service = s3Service;
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
        // TODO: Validate that the image file part exists before attempting to upload it. If it doesn't exist, show an appropriate error message to the user.
        // TODO: Move the logic for saving the image to a separate method to make it easier to test and reuse.

        imageService.saveImage(new Image(
            req.getParameter("imageName"),
            LocalDateTime.now(),
            s3Service.upload(req.getPart(IMAGE_FILE_ATTRIBUTE).getInputStream()),
            req.getPart(IMAGE_FILE_ATTRIBUTE).getInputStream().readAllBytes().length
        ));

        // TODO: Add logging to track errors and successful uploads.
        if (resp.getStatus() == STATUS_CODE_OK) {
            req.setAttribute(STATUS_TEXT_ATTRIBUTE, "Image successful uploaded");
        } else {
            req.setAttribute(STATUS_TEXT_ATTRIBUTE, "There is error during uploading. Please, try again!");
        }
        req.getRequestDispatcher("uploadStatus.jsp").forward(req, resp);
    }
}
