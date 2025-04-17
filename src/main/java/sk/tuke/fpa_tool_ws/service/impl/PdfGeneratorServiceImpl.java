package sk.tuke.fpa_tool_ws.service.impl;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import sk.tuke.fpa_tool_ws.dto.CalculationDto;
import sk.tuke.fpa_tool_ws.dto.CalculationGroupDto;
import sk.tuke.fpa_tool_ws.service.PdfGeneratorService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private final TemplateEngine templateEngine;
    private static final String LOGO_IMAGE_PATH = "templates/images/logo.png";


    public PdfGeneratorServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    @Override
    public byte[] generatePdfReport(List<CalculationDto> calculations) throws IOException {
        Context context = new Context();
        context.setVariable("calculations", calculations);
        return generatePdf(context);


    }

    @Override
    public byte[] generatePdfReport(List<CalculationDto> calculations, CalculationGroupDto group) throws IOException {
        Context context = new Context();
        context.setVariable("calculations", calculations);
        context.setVariable("group", group);
        return generatePdf(context);
    }

    private byte[] generatePdf(Context context) throws IOException {
        String logoBase64 = encodeImageToBase64();

        if (logoBase64 != null) {
            context.setVariable("logoImageBase64", logoBase64);
        } else {
            System.err.println("Warning: Logo image not found or could not be encoded.");
        }

        String htmlContent = templateEngine.process("pdf/report-template", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);
        renderer.finishPDF();

        outputStream.close();
        return outputStream.toByteArray();
    }

    private String encodeImageToBase64() {
        try {
            ClassPathResource resource = new ClassPathResource(PdfGeneratorServiceImpl.LOGO_IMAGE_PATH);
            if (!resource.exists()) {
                System.err.println("Image resource not found at path: " + PdfGeneratorServiceImpl.LOGO_IMAGE_PATH);
                return null;
            }
            InputStream inputStream = resource.getInputStream();
            byte[] imageBytes = StreamUtils.copyToByteArray(inputStream);
            inputStream.close();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            System.err.println("Error reading or encoding image: " + PdfGeneratorServiceImpl.LOGO_IMAGE_PATH + " - " + e.getMessage());
            return null;
        }
    }
}
