package com.example.productservice.adapter.in.web;

import com.example.productservice.adapter.in.web.dto.ProductDTO;
import com.example.productservice.adapter.in.web.mapper.DTOMapper;
import com.example.productservice.core.model.Product;
import com.example.productservice.core.port.input.IProductInputPort;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final IProductInputPort productInputPort;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestPart ProductDTO productDTO,
                                           @RequestPart MultipartFile image) throws IOException {
            Product savedProduct = productInputPort.saveProduct(DTOMapper.INSTANCE.mapToProduct(productDTO), image);
            return new ResponseEntity<>(DTOMapper.INSTANCE.mapToProductDTO(savedProduct), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productsDTOList = productInputPort.getAllProducts().stream().map(DTOMapper.INSTANCE::mapToProductDTO).toList();
        return new ResponseEntity<>(productsDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String id) {
        Product product = productInputPort.getProductById(id);

        return ObjectUtils.isEmpty(product) ?
                                            new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                                            new ResponseEntity<>(DTOMapper.INSTANCE.mapToProductDTO(product), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @RequestPart ProductDTO productDTO,
            @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        try {
            Product updatedProduct = productInputPort.updateProduct(id, DTOMapper.INSTANCE.mapToProduct(productDTO), imageFile);
            return ResponseEntity.ok(DTOMapper.INSTANCE.mapToProductDTO(updatedProduct));
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productInputPort.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}