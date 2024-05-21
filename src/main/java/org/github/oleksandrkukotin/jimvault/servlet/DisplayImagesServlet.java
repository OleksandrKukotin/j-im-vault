package org.github.oleksandrkukotin.jimvault.servlet;

import org.github.oleksandrkukotin.jimvault.configuration.localstack.S3Service;
import org.github.oleksandrkukotin.jimvault.image.Image;
import org.github.oleksandrkukotin.jimvault.image.ImageDto;
import org.github.oleksandrkukotin.jimvault.image.ImageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.github.oleksandrkukotin.jimvault.utils.ImageUtils;

import java.io.IOException;
import java.util.List;

@WebServlet("displayImages")
public class DisplayImagesServlet extends HttpServlet {

    static final String NOT_FOUND_STYLE_ATTRIBUTE = "notFoundStyle";
    static final String NOT_FOUND_MESSAGE_ATTRIBUTE = "notFoundMessage";
    private final ImageService imageService;
    private final S3Service s3Service;

    public DisplayImagesServlet(ImageService imageService, S3Service s3Service) {
        this.imageService = imageService;
        this.s3Service = s3Service;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Image> images = imageService.findAllImages();
        if (!images.isEmpty()) {
            List<ImageDto> imageDtos = ImageUtils.imagesToImageDtosMapper(images, s3Service::getAsBase64);
            req.setAttribute("imageDtos", imageDtos);
        } else {
            req.setAttribute(NOT_FOUND_MESSAGE_ATTRIBUTE, "An error occurred during getting images =(");
            req.setAttribute(NOT_FOUND_STYLE_ATTRIBUTE, "style = \"display : none\"");
        }
        req.getRequestDispatcher("showAllPage.jsp").forward(req, resp);
    }
}