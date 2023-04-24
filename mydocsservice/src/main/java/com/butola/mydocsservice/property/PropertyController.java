package com.butola.mydocsservice.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/property")
public class PropertyController {
    @Autowired
    PropertyService propertyService;

    @GetMapping(path = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> getPdfFile() throws Exception {
        String blobName = "us.minnesota.edenprairie.yogi.tax.pdf";
        byte[] pdfBytes = propertyService.getPdfFile(blobName);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + blobName);
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            propertyService.uploadFile(file);
            return new ResponseEntity<>("File uploaded successfully.", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error uploading file: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
