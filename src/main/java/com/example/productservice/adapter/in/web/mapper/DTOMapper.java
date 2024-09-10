package com.example.productservice.adapter.in.web.mapper;

import com.example.productservice.adapter.in.web.dto.ProductDTO;
import com.example.productservice.core.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DTOMapper {
    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

    Product mapToProduct (ProductDTO productDTO);
    ProductDTO mapToProductDTO (Product product);
}
