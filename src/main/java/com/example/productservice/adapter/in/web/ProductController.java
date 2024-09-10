package com.example.productservice.adapter.in.web;

import com.example.productservice.adapter.in.web.dto.ProductDTO;
import com.example.productservice.adapter.in.web.mapper.DTOMapper;
import com.example.productservice.core.model.Product;
import com.example.productservice.core.port.input.IProductInputPort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/products/{customerId}")
public class ProductController {

    private final IProductInputPort productInputPort;

    @PostMapping
    public ResponseEntity<?> createProduct(@PathVariable String customerId,
                                           @RequestPart @Valid ProductDTO productDTO,
                                           @RequestPart MultipartFile image) throws IOException {
        Product savedProduct = productInputPort.saveProduct(customerId, getProduct(productDTO), image);
        return new ResponseEntity<>(getProductDTO(savedProduct), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable String customerId) {
        List<ProductDTO> productsDTOList = productInputPort.getAllProducts(customerId).stream().map(DTOMapper.INSTANCE::mapToProductDTO).toList();
        return new ResponseEntity<>(productsDTOList, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String customerId,
                                                 @PathVariable String productId) {
        Product product = productInputPort.getProductById(customerId, productId);

        return ObjectUtils.isEmpty(product) ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(getProductDTO(product), HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String customerId,
                                           @PathVariable String productId,
                                           @RequestPart @Valid ProductDTO productDTO,
                                           @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {
            Product updatedProduct = productInputPort.updateProduct(customerId, productId, DTOMapper.INSTANCE.mapToProduct(productDTO), imageFile);
            return ResponseEntity.ok(getProductDTO(updatedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String customerId,
                                              @PathVariable String productId) {
        productInputPort.deleteProduct(customerId, productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private static ProductDTO getProductDTO(Product savedProduct) {
        return DTOMapper.INSTANCE.mapToProductDTO(savedProduct);
    }

    private static Product getProduct(ProductDTO productDTO) {
        return DTOMapper.INSTANCE.mapToProduct(productDTO);
    }
}