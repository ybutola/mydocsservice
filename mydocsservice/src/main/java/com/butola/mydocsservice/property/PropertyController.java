package com.butola.mydocsservice.property;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @GetMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPdf() throws Exception {
        // Load the PDF file
        Path pdfPath = Paths.get("src/main/resources/images/aadhaar.pdf");
        byte[] pdfBytes = Files.readAllBytes(pdfPath);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.pdf");

        // Add HATEOAS links
        /*Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PropertyController.class).getPdf()).withSelfRel();
        Link downloadLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PropertyController.class).getPdf()).withRel("download");
        List<Link> links = Arrays.asList(selfLink, downloadLink);

        EntityModel<Resource> model = EntityModel.of(resource);
        model.add(links);
*/

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
