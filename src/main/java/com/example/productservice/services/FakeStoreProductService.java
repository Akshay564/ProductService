package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class FakeStoreProductService implements ProductService {

    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeStoreProductDto.class);
        return getProductFromFakeStoreProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductsDto =  restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        return getProductsFromFakeStoreProducts(fakeStoreProductsDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProduct = mapToFakeStoreProductDto(product);

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProduct, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto =  restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor).getBody();

        return getProductFromFakeStoreProduct(fakeStoreProductDto);
    }

    private FakeStoreProductDto mapToFakeStoreProductDto(Product product) {
        if (product == null) {
            return null;
        }
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory() != null ? product.getCategory().getTitle() : null);
        return dto;
    }

    private Product getProductFromFakeStoreProduct(FakeStoreProductDto fakeStoreProductDto) {
        if (fakeStoreProductDto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());

        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);

        return  product;
    }

    private List<Product> getProductsFromFakeStoreProducts(FakeStoreProductDto[] fakeStoreProductsDto) {
        if (fakeStoreProductsDto == null || fakeStoreProductsDto.length == 0) {
            return List.of();
        }

        return Stream.of(fakeStoreProductsDto)
                .map(this::getProductFromFakeStoreProduct)
                .toList();
    }
}
