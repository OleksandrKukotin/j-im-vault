import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddingTextServlet extends HttpServlet {

    private TextService textService;

    public AddingTextServlet(TextService textService) {
        this.textService = textService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final TextAddingFormDto textAddingFormDto = new TextAddingFormDto();
        textAddingFormDto.setTextAuthor(req.getParameter("textAuthor"));
        textAddingFormDto.setTextBody(req.getParameter("textBody"));
        textService.addToDB(textAddingFormDto);
        resp.sendRedirect("index.jsp");
    }
}
