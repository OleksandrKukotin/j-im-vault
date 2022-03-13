public class TextService {

    private final TextRepository textRepository;

    public TextService(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    public boolean addToDB(TextAddingFormDto textAddingFormDto) {
        textRepository.save(createText(textAddingFormDto));
        return true;
    }

    private Text createText(TextAddingFormDto textAddingFormDto) {
        final Text text = new Text();
        text.setTextAuthor(textAddingFormDto.getTextAuthor());
        text.setTextBody(textAddingFormDto.getTextBody());
        return text;
    }
}
